package com.coder.springjwt.services.publicService.reviewsService.imple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.emuns.DeliveryStatus;
import com.coder.springjwt.exception.customerPanelException.ReviewAlreadySubmitException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.productReviews.ProductReviewFiles;
import com.coder.springjwt.models.customerPanelModels.productReviews.ProductReviews;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository.OrderItemsRepository;
import com.coder.springjwt.repository.customerPanelRepositories.reviewsRepository.ReviewsRepository;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.services.publicService.reviewsService.ReviewsService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewsServiceImple implements ReviewsService {


    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private BucketService bucketService;

    @Override
    public ResponseEntity<?> unReviewDeliveredProduct(Integer page, Integer size) {
        log.info("<-- unratedDeliveredProduct Flying -->");
        try {

            //User Check
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new RuntimeException(CustMessageResponse.USERNAME_NOT_FOUND));

            PageRequest pageRequest = PageRequest.of(page, size);

            Page<CustomerOrderItems> unReviewProducts = this.orderItemsRepository.
                                                        findByUserIdAndDeliveryStatusAndIsRatingOrderByIdDesc
                                                        (String.valueOf(user.getId()),DeliveryStatus.DELIVERED.toString(),
                                                        Boolean.FALSE , pageRequest);

//            List<Object[]> allWithoutRatingsByUserId = this.orderItemsRepository
//                                            .findAllWithoutRatingsWithDetails("16");
//
//            for (Object[] row : allWithoutRatingsByUserId) {
//                CustomerOrderItems orderItem = (CustomerOrderItems) row[0];
//                Ratings rating = (Ratings) row[1]; // This will be null
//            }
            return ResponseGenerator.generateSuccessResponse(unReviewProducts , CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> submitProductReview(long id , String rating, String reviewText, MultipartFile file) {
        log.info("======>   submitProductReview   <========");
        try {
            Optional<Long> optId = Optional.ofNullable(id);
            Optional<String> optRating = Optional.ofNullable(rating);
            Optional<String> optReviewText = Optional.ofNullable(reviewText);
            Optional<MultipartFile> optFile = Optional.ofNullable(file);

            if(optFile.isEmpty())
            {
                log.warn("Review File is Empty");
            } else if (optFile.isPresent()) {
                log.warn("Review File is Present");
            }

            if(optId.isPresent() && optRating.isPresent() && Optional.ofNullable(id).isPresent() && optReviewText.isPresent()){

                //User Check
                String currentUser = UserHelper.getOnlyCurrentUser();
                User user = this.userRepository.findByUsername(currentUser)
                                                .orElseThrow(() -> new RuntimeException(CustMessageResponse.USERNAME_NOT_FOUND));

                CustomerOrderItems checkedOrderItems = this.orderItemsRepository
                                                                    .findByIdAndUserIdAndDeliveryStatusAndIsRating(id,
                                                                    String.valueOf(user.getId()),
                                                                    DeliveryStatus.DELIVERED.toString(),
                                                                    Boolean.FALSE);
                Optional<CustomerOrderItems> optionalCheckedOrderItems = Optional.ofNullable(checkedOrderItems);

                if(optionalCheckedOrderItems.isEmpty())
                {
                    throw new ReviewAlreadySubmitException(CustMessageResponse.REVIEW_ALREADY_SUBMIT);
                }

                SellerProduct sellerProduct = this.sellerProductRepository.findById(Long.parseLong(checkedOrderItems.getProductId()))
                        .orElseThrow(() -> new RuntimeException("SELLER PRODUCT NOT FOUND"));


                if(optionalCheckedOrderItems.isPresent())
                {
                    ProductReviews productReviews = new ProductReviews();
                    productReviews.setRating(optRating.get());
                    productReviews.setReview(optReviewText.get());
                    productReviews.setOrderItemId(String.valueOf(checkedOrderItems.getId()));
                    productReviews.setUserId(checkedOrderItems.getUserId());
                    productReviews.setProductId(checkedOrderItems.getProductId());
                    productReviews.setProductFileUrl(checkedOrderItems.getFileUrl());

                    //Set Product Name
                    productReviews.setProductName(checkedOrderItems.getProductName());

                    //Set Product Size
                    productReviews.setProductSize(checkedOrderItems.getProductSize());

                    //Set Product Price
                    productReviews.setProductPrice(checkedOrderItems.getProductPrice());

                    //Set Seller Product
                    productReviews.setSellerProduct(sellerProduct);

                    //Set User
                     productReviews.setUser(user);

                    //Set Review File URL
                    if(optFile.isPresent()) {
                        BucketModel bucketModel = this.bucketService.uploadFile(file);

                        ProductReviewFiles productReviewFiles = new ProductReviewFiles();
                        productReviewFiles.setReviewFileUrl(bucketModel.getBucketUrl());
                        productReviewFiles.setProductReviews(productReviews);

                        //Set Product Reviews Files To LIST
                        productReviews.setProductReviewFiles(List.of(productReviewFiles));

                    }

                    //save RUser Review
                    this.reviewsRepository.save(productReviews);
                    log.info("Product Review Saved Success:: ");

                    //Update CustomerOrderItem Table Column isRating = 'TRUE'
                    checkedOrderItems.setRating(Boolean.TRUE);
                    this.orderItemsRepository.save(checkedOrderItems);
                    log.info("product order Item rating flag update Success");

                    return ResponseGenerator.generateSuccessResponse(CustMessageResponse.DATA_SAVED_SUCCESS,
                            CustMessageResponse.SUCCESS);
                }else{
                    return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.DATA_NOT_FOUND);
                }
            }
            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.SUCCESS , CustMessageResponse.SUCCESS);
        }
        catch (ReviewAlreadySubmitException e)
        {
            e.getMessage();
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(Collections.singletonMap("msg",e.getMessage()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> getUserReviews(Integer page, Integer size) {
        log.info("==== getUserReviews Flying ====");
        try {
            //Get User
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new RuntimeException(CustMessageResponse.USERNAME_NOT_FOUND));

            PageRequest pageRequest = PageRequest.of(page, size , Sort.by(Sort.Direction.DESC , "id"));
            Page<ProductReviews> reviewsData = this.reviewsRepository.
                                                findByUserIdOrderByIdDesc(String.valueOf(user.getId()),
                                                                                                pageRequest);

            return ResponseGenerator.generateSuccessResponse(reviewsData,CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> getEditReview(long reviewId) {
        log.info("==== getEditReview Flying ====");
        try {
            //Get User
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new RuntimeException(CustMessageResponse.USERNAME_NOT_FOUND));

            ProductReviews reviewData = this.reviewsRepository.findByIdAndUserId(reviewId, String.valueOf(user.getId()))
                                        .orElseThrow(()->new RuntimeException(CustMessageResponse.DATA_NOT_FOUND));

            return ResponseGenerator.generateSuccessResponse(reviewData,CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> updateReviews(long id, String rating, String reviewText, MultipartFile file) {
        log.info("==== updateReviews Flying ====");
        try {
            //Get User
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser)
                    .orElseThrow(() -> new RuntimeException(CustMessageResponse.USERNAME_NOT_FOUND));

            ProductReviews productReviews = this.reviewsRepository
                                            .findByIdAndUserId(id, String.valueOf(user.getId()))
                                            .orElseThrow(()-> new  RuntimeException("Review Id not Found Here !!!"));

            if(Optional.ofNullable(file).isPresent())
            {
                productReviews.setReview(reviewText);
                productReviews.setRating(rating);

                //Upload File To AWS and GET URL
                BucketModel bucketModel = bucketService.uploadFile(file);
                if(productReviews.getProductReviewFiles().isEmpty()){
                    ProductReviewFiles productReviewFiles = new ProductReviewFiles();
                    productReviewFiles.setReviewFileUrl(bucketModel.getBucketUrl());
                    productReviewFiles.setProductReviews(productReviews);

                    List<ProductReviewFiles> reviewFiles = new ArrayList<>();
                    reviewFiles.add(productReviewFiles);
                    productReviews.setProductReviewFiles(reviewFiles);
                }else{
                    List<ProductReviewFiles> productReviewFilesStream = productReviews.getProductReviewFiles().stream()
                            .map(e -> {
                                e.setReviewFileUrl(bucketModel.getBucketUrl());
                                return e;
                            }).collect(Collectors.toList());
                    productReviews.setProductReviewFiles(productReviewFilesStream);
                }
                this.reviewsRepository.save(productReviews);
                log.info("Product Review Update Success with File");
            }else{
                productReviews.setReview(reviewText);
                productReviews.setRating(rating);

                this.reviewsRepository.save(productReviews);
                log.info("Product Review Update Success!!!");
            }
            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.DATA_UPDATE_SUCCESS,CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }


}
