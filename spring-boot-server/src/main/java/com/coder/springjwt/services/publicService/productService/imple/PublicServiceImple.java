package com.coder.springjwt.services.publicService.productService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.*;
import com.coder.springjwt.dtos.customerPanelDtos.filterDto.ProductFilterDto;
import com.coder.springjwt.emuns.ProductStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.customerPanelModels.productReviews.ProductReviews;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.models.sellerModels.homeSliders.HomeSliderModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.customerPanelRepositories.reviewsRepository.ReviewsRepository;
import com.coder.springjwt.repository.sellerRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.homeSliderRepo.HomeSliderRepo;
import com.coder.springjwt.repository.sellerRepository.hotDealsRepos.HotDealsEngineRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.response.sellerProductResponse.ProductDetailsResponse;
import com.coder.springjwt.response.sellerProductResponse.ProductFilesResponse;
import com.coder.springjwt.response.sellerProductResponse.SellerProductResponse;
import com.coder.springjwt.response.sellerProductResponse.SellerProductVarientResponse;
import com.coder.springjwt.services.publicService.productService.PublicService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PublicServiceImple implements PublicService {
    @Autowired
    private ParentCategoryRepo parentCategoryRepo;

    @Autowired
    private HomeSliderRepo homeSliderRepo;

    @Autowired
    private BabyCategoryRepo babyCategoryRepo;

    @Autowired
    private BornCategoryRepo bornCategoryRepo;

    @Autowired
    private HotDealsEngineRepo hotDealsEngineRepo;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReviewsRepository reviewsRepository;


    @Override
    public ResponseEntity<?> getProductCategoryService() {
        log.info("<--- getProductListByCategoryId Flying --->");

        Map<Object,Object> mapNode = new HashMap<>();

        try {
            //Get Home Slider Data
            List<HomeSliderModel> homeSliderData = this.homeSliderRepo.findAll();

            List<ParentCategoryModel> parentCategories = this.parentCategoryRepo.findAll();

            List<ParentCategoryDto> listOfCategories = new ArrayList<>();
            for(ParentCategoryModel pcm : parentCategories)
            {
                    ParentCategoryDto parentCategoryDto =  new ParentCategoryDto();
                    parentCategoryDto.setId(pcm.getId());
                    parentCategoryDto.setParentCategoryName(pcm.getCategoryName());

                    List<BabyCategoryDto> babyCategoryDtoList = new ArrayList<>();
                    for(ChildCategoryModel ccm : pcm.getChildCategoryModelList())
                    {
                        ChildCategoryDto childCategoryDto = new ChildCategoryDto();
                        childCategoryDto.setId(ccm.getId());
                        childCategoryDto.setChildCategoryName(ccm.getCategoryName());

                        for(BabyCategoryModel bbm : ccm.getBabyCategoryModel())
                        {
                            BabyCategoryDto babyCategoryDto = new BabyCategoryDto();
                            babyCategoryDto.setId(bbm.getId());
                            babyCategoryDto.setBabyCategoryName(bbm.getCategoryName());

                            List<BornCategoryDto> bornCategoryDtoList = new ArrayList<>();
                            for(BornCategoryModel bornC : bbm.getBornCategoryModel())
                            {
                                BornCategoryDto bornCategoryDto = new BornCategoryDto();
                                bornCategoryDto.setId(bornC.getId());
                                bornCategoryDto.setBornCategoryName(bornC.getCategoryName());
                                bornCategoryDtoList.add(bornCategoryDto);
                            }
                            babyCategoryDto.setBornCategoryDtos(bornCategoryDtoList);
                            babyCategoryDtoList.add(babyCategoryDto);
                        }
                    }
                    parentCategoryDto.setBabyCategoryDtos(babyCategoryDtoList);
                listOfCategories.add(parentCategoryDto);
            }

            //get Baby Category
            Pageable pageable = PageRequest.of(0, 17);
            List<BabyCategoryModel> babyList = this.babyCategoryRepo.findAll(pageable).getContent();
            List<BabyCategoryPopularDto> babyDataFilter = babyList.stream().map(
                    b -> new BabyCategoryPopularDto(b.getId(), b.getCategoryName(),b.getCategoryFile()))
                    .collect(Collectors.toList());

            //Get Parent Categories only Men
            Pageable mensListPageable = PageRequest.of(0, 22);
            List<BornCategoryModel> mensList = this.bornCategoryRepo.getBornCategoryListByParentCategoryId(1L,mensListPageable);

            Page<SellerProductResponse> productsList = this.getBornCategoryList(1l, "Mens Top Wear", 0, 40);

            mapNode.put("listOfCategories",listOfCategories);
            mapNode.put("homeSliderData",homeSliderData);
            mapNode.put("babyDataFilter",babyDataFilter);
            mapNode.put("productsList",productsList.getContent());
            mapNode.put("mensList",mensList);
            log.info("getProductCategoryService Fetch Data Success");
            return ResponseGenerator.generateSuccessResponse(mapNode, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getBabyCategoryFooter() {
       try {
           List<BabyCategoryModel> babyCategories = babyCategoryRepo.
                                        findAll(Sort.by(Sort.Direction.DESC, "creationDate"));

           List<BabyCategoryFooterDto> footerCategory = babyCategories.stream()
                                       .map(category -> modelMapper.map(category, BabyCategoryFooterDto.class))
                                       .collect(Collectors.toList());

           return ResponseGenerator.generateSuccessResponse(footerCategory, SellerMessageResponse.SUCCESS);
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
       }
    }

    @Override
    public ResponseEntity<?> getProductListByCategoryId(long categoryId, String categoryName, Integer page, Integer size) {
        log.info("<--- getProductListByCategoryId Flying --->");
        try {

            BabyCategoryModel babyCategoryModel = this.babyCategoryRepo.findById(categoryId)
                    .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            PageRequest pageRequest= PageRequest.of(page, size);
            Page<SellerProduct> babyCategoryData = this.sellerProductRepository
                                                   .findByBabyCategoryIdAndProductStatus(String.valueOf(babyCategoryModel.getId()) ,
                                                    ProductStatus.PV_APPROVED.toString(), pageRequest );


            List<SellerProductResponse> productResponses = babyCategoryData.getContent().stream()
                    .map(sellerProduct -> {
                        SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

                        //Calculate Ratings
                        this.calculateRatingAndReviews(response , sellerProduct.getProductReviews());

                        ProductVariants productVariants = sellerProduct.getProductRows().get(0);
                        response.setColorVariant(productVariants.getColorVariant());
                        response.setProductPrice(productVariants.getProductPrice());
                        response.setProductMrp(productVariants.getProductMrp());
                        response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

                        response.setProductFilesResponses(sellerProduct.getProductFiles().stream()
                                .map(productFiles->modelMapper.map(productFiles,ProductFilesResponse.class)).collect(Collectors.toList()));

                        return response;
                    })
                    .collect(Collectors.toList());

            Page<SellerProductResponse> responsePage = new PageImpl<>(productResponses, babyCategoryData.getPageable(), babyCategoryData.getTotalElements());

            log.info("getProductListByBornCategoryId Fetch Data Success :: ");
            return ResponseGenerator.generateSuccessResponse(responsePage, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getProductListByBornCategoryId(long categoryId, String categoryName, Integer page, Integer size) {
        log.info("<--- getProductListByBornCategoryId Flying --->");
        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(categoryId)
                    .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            PageRequest pageRequest= PageRequest.of(page, size);
            Page<SellerProduct> data = this.sellerProductRepository
                                        .findByBornCategoryIdAndProductStatus(String.valueOf(bornCategoryModel.getId()) ,
                                         ProductStatus.PV_APPROVED.toString(), pageRequest );


            List<SellerProductResponse> productResponses = data.getContent().stream()
                    .map(sellerProduct -> {
                        SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

                        ProductVariants productVariants = sellerProduct.getProductRows().get(0);
                        response.setColorVariant(productVariants.getColorVariant());
                        response.setProductPrice(productVariants.getProductPrice());
                        response.setProductMrp(productVariants.getProductMrp());
                        response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

                        response.setProductFilesResponses(sellerProduct.getProductFiles().stream()
                                .map(productFiles->modelMapper.map(productFiles,ProductFilesResponse.class)).collect(Collectors.toList()));

                        return response;
                    })
                    .collect(Collectors.toList());

            Page<SellerProductResponse> responsePage = new PageImpl<>(productResponses, data.getPageable(), data.getTotalElements());

            log.info("getProductListByBornCategoryId Fetch Data Success :: ");
            return ResponseGenerator.generateSuccessResponse(responsePage, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }



    @Override
    public ResponseEntity<?> productWatching(String pI, String pN) {
        log.info("<--- productWatching Flying --->");

        Map<Object,Object> map = new HashMap<>();
        try {
            SellerProduct sellerProduct = this.sellerProductRepository
                                            .findByIdAndProductStatus(Long.parseLong(pI),
                                                    ProductStatus.PV_APPROVED.toString());

            SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

            //Calculate Ratings
            this.calculateRatingAndReviews(response , sellerProduct.getProductReviews());

            ProductVariants productVariants = sellerProduct.getProductRows().get(0);
            response.setColorVariant(productVariants.getColorVariant());
            response.setProductPrice(productVariants.getProductPrice());
            response.setProductMrp(productVariants.getProductMrp());
            response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

            ProductDetailsResponse pd1 = new ProductDetailsResponse();
            pd1.setPdKey("Sleeve Type");
            pd1.setPdValue(sellerProduct.getSleeveType());
            ProductDetailsResponse pd2 = new ProductDetailsResponse();
            pd2.setPdKey("patterns");
            pd2.setPdValue(sellerProduct.getPattern());
            ProductDetailsResponse pd3 = new ProductDetailsResponse();
            pd3.setPdKey("finishing Type");
            pd3.setPdValue(sellerProduct.getFinishingType());
            ProductDetailsResponse pd4 = new ProductDetailsResponse();
            pd4.setPdKey("fitType");
            pd4.setPdValue(sellerProduct.getFitType());
            ProductDetailsResponse pd5 = new ProductDetailsResponse();
            pd5.setPdKey("material Type");
            pd5.setPdValue(sellerProduct.getMaterialType());
            ProductDetailsResponse pd6 = new ProductDetailsResponse();
            pd6.setPdKey("country");
            pd6.setPdValue(sellerProduct.getCountry());
            response.setProductDetailsResponses(List.of(pd1,pd2,pd3,pd4,pd5,pd6));

            response.setSellerProductVarientResponses(sellerProduct.getProductRows().stream()
                    .map(pv->modelMapper.map(pv,SellerProductVarientResponse.class)).collect(Collectors.toList()));

            response.setProductFilesResponses(sellerProduct.getProductFiles().stream()
                    .map(productFiles->modelMapper.map(productFiles,ProductFilesResponse.class)).collect(Collectors.toList()));


            //Similar Products Starting
            Page<SellerProductResponse> similarProduct = this.getSimilarProducts(
                                                            Long.valueOf(sellerProduct.getBornCategoryId()),
                                                            sellerProduct.getBornCategoryName(),
                                                            0,
                                                            40);

            PageRequest pageRequest= PageRequest.of(0, 10);
            Page<ProductReviews> productReviews = this.reviewsRepository.findByProductId(String.valueOf(pI), pageRequest);

            map.put("pw",response);
            map.put("similarProducts",similarProduct);
            //map.put("productReviews",productReviews);

            log.info("productWatching Fetch Data Success:: ");
            return ResponseGenerator.generateSuccessResponse(map, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }




    public Page<SellerProductResponse> getBornCategoryList(long categoryId, String categoryName, Integer page, Integer size) {
        log.info("<--- getBornCategoryList Flying --->");
        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(categoryId)
                    .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            PageRequest pageRequest= PageRequest.of(page, size);
            Page<SellerProduct> data = this.sellerProductRepository
                    .findByBornCategoryIdAndProductStatus(String.valueOf(bornCategoryModel.getId()) ,
                            ProductStatus.PV_APPROVED.toString(),pageRequest );

            List<SellerProductResponse> productResponses = data.getContent().stream()
                    .map(sellerProduct -> {
                        SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

                        //Calculate Ratings
                        this.calculateRatingAndReviews(response , sellerProduct.getProductReviews());

                        ProductVariants productVariants = sellerProduct.getProductRows().get(0);
                        response.setColorVariant(productVariants.getColorVariant());
                        response.setProductPrice(productVariants.getProductPrice());
                        response.setProductMrp(productVariants.getProductMrp());
                        response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

                        response.setProductFilesResponses(sellerProduct.getProductFiles().stream()
                                .map(productFiles->modelMapper.map(productFiles,ProductFilesResponse.class)).collect(Collectors.toList()));

                        return response;
                    })
                    .collect(Collectors.toList());

            Page<SellerProductResponse> responsePage = new PageImpl<>(productResponses, data.getPageable(), data.getTotalElements());

            log.info("getBornCategoryList Fetch Data Success:: ");
            return responsePage;
            //ResponseGenerator.generateSuccessResponse(responsePage, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
            return null;
        }
    }

    public void calculateRatingAndReviews( SellerProductResponse sellerProductResponse , List<ProductReviews> productReviews)
    {
        try {
            //Ratings
            double rating = 0;
            double reviewCount = 0.0;
            for(ProductReviews pv : productReviews)
            {
                if(pv != null)
                {
                    rating+=Double.valueOf(pv.getRating());
                    reviewCount++;
                }
            }
            if(rating != 0 )
            {
                double averageRating = rating / reviewCount;
                sellerProductResponse.setRating(String.valueOf(averageRating));
                sellerProductResponse.setReviewCount(String.valueOf(reviewCount));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.error("Calculated Rating Error !!!");
        }
    }



    @Override
    public ResponseEntity<?> productSearching(String searchKey) {
        log.info("===> productSearching <====");
        try {
            log.info("searchKey :: " + searchKey);
            List<BornCategoryModel> searchingData = this.bornCategoryRepo.findByCategoryNameContainingIgnoreCase(searchKey);

            log.info("Searching Found :: " + searchingData.size());

            List<BornCategoryDto> searchData = searchingData.stream()
                                               .map(bc -> modelMapper.map(bc, BornCategoryDto.class))
                                               .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(searchData, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> productFilter(ProductFilterDto productFilterDto, Integer page, Integer size) {
        log.info("<===  productFilter Flying... ===>");
        log.info("BRAND :: " + productFilterDto.getBrandKeys());
        log.info("GENDER :: " +productFilterDto.getGenders());
        log.info("PRICE:: " + productFilterDto.getPrice());

        //Price Range
        int min = 0;
        int max = 0;
        if(productFilterDto.getPrice() != null)
        {
            int[] range = parsePriceRange(productFilterDto.getPrice());
            min = range[0];
            max = range[1];
        }

        try {
            int brandKeys = productFilterDto.getBrandKeys().size();
            int genders = productFilterDto.getGenders().size();

            //Page Request Size
            PageRequest pageRequest = PageRequest.of(page, size);

            Page<SellerProduct> sellerProductResponse= null;

            //Brand Filter
            if(brandKeys > 0)
            {
                //Brand Keys Present and Gender is Empty
                if(brandKeys > 0 &&  genders == 0 && productFilterDto.getPrice() == null){
                    log.info("Brand Present and Gender is Empty");
                    sellerProductResponse = this.sellerProductRepository.findByBrandField(
                            productFilterDto.getBrandKeys(),
                            pageRequest);
                }

                //Brand Keys Present and Gender is Present
                if(brandKeys > 0 && genders > 0 && productFilterDto.getPrice() == null){
                    log.info("Brand Present and Gender Present");
                    sellerProductResponse = this.sellerProductRepository.findByBrandFieldAndGenders(
                            productFilterDto.getBrandKeys(),productFilterDto.getGenders(),
                            pageRequest);
                }
            }

            //Gender Filter
            if(genders > 0)
            {
                //Brand Empty and Gender is Present
                if(brandKeys == 0 && genders > 0 && productFilterDto.getPrice() == null){
                    log.info("Brand Empty and Gender Present");
                    sellerProductResponse = this.sellerProductRepository.findByGenders(
                            productFilterDto.getGenders(),
                            pageRequest);
                }
            }


            //Price Filter
            if(productFilterDto.getPrice() != null)
            {
                //Price Range Present
                if( brandKeys > 0 && genders == 0 && productFilterDto.getPrice() != null ){
                    log.info("brand Present and gender Empty and PriceRange Present ");
                    sellerProductResponse = this.sellerProductRepository
                            .findByBrandFieldAndPriceRange(productFilterDto.getBrandKeys() ,
                                    min ,max ,pageRequest);
                }
                //Price Range Present
                if( brandKeys > 0 && genders > 0 && productFilterDto.getPrice() != null ){
                    log.info("brand Present and gender Present and PriceRange Present ");
                    sellerProductResponse = this.sellerProductRepository.findByBrandFieldAndGenderAndPriceRange(
                            productFilterDto.getBrandKeys() ,
                            productFilterDto.getGenders() ,
                            min ,max ,pageRequest);
                }

                //Price Range Present
                if( brandKeys == 0 && genders == 0 && productFilterDto.getPrice() != null ){
                    log.info("brand Empty and gender Empty and PriceRange Present ");
                    sellerProductResponse = this.sellerProductRepository.findByPriceRange(min ,max ,pageRequest);
                }
            }

                    Page<SellerProductResponse> responsePage  = sellerProductResponse.map(sellerProduct -> {
                    SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

                    //Calculate Ratings
                    this.calculateRatingAndReviews(response , sellerProduct.getProductReviews());

                    ProductVariants productVariants = sellerProduct.getProductRows().get(0);
                    response.setColorVariant(productVariants.getColorVariant());
                    response.setProductPrice(productVariants.getProductPrice());
                    response.setProductMrp(productVariants.getProductMrp());
                    response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

                    response.setProductFilesResponses(
                            sellerProduct.getProductFiles().stream()
                                    .map(productFiles -> modelMapper.map(productFiles, ProductFilesResponse.class))
                                    .collect(Collectors.toList())
                    );
                    return response;
                });
            return ResponseGenerator.generateSuccessResponse(responsePage, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
//            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getProductByIdCustomer(String pI, String pN) {
        log.info("<--- productWatching Flying --->");

        Map<Object,Object> map = new HashMap<>();
        try {
            SellerProduct sellerProduct = this.sellerProductRepository
                    .findByIdAndProductStatus(Long.parseLong(pI),
                            ProductStatus.PV_APPROVED.toString());

            SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

            ProductVariants productVariants = sellerProduct.getProductRows().get(0);
            response.setColorVariant(productVariants.getColorVariant());
            response.setProductPrice(productVariants.getProductPrice());
            response.setProductMrp(productVariants.getProductMrp());
            response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

            response.setSellerProductVarientResponses(sellerProduct.getProductRows().stream()
                    .map(pv->modelMapper.map(pv,SellerProductVarientResponse.class)).collect(Collectors.toList()));

            response.setProductFilesResponses(sellerProduct.getProductFiles().stream()
                    .map(productFiles->modelMapper.map(productFiles,ProductFilesResponse.class)).collect(Collectors.toList()));

            map.put("pw",response);

            log.info("productWatching Fetch Data Success:: ");
            return ResponseGenerator.generateSuccessResponse(map, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }



    public Page<SellerProductResponse> getSimilarProducts(long categoryId, String categoryName, Integer page, Integer size) {
        log.info("<--- getBornCategoryList Flying --->");
        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(categoryId)
                    .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            PageRequest pageRequest= PageRequest.of(page, size);
            Page<SellerProduct> data = this.sellerProductRepository.findByBornCategoryIdAndProductStatus
                                                (String.valueOf(bornCategoryModel.getId()) ,
                                                ProductStatus.PV_APPROVED.toString(), pageRequest );

            List<SellerProductResponse> productResponses = data.getContent().stream()
                    .map(sellerProduct -> {
                        SellerProductResponse response = modelMapper.map(sellerProduct, SellerProductResponse.class);

                        //Calculate Ratings
                        this.calculateRatingAndReviews(response , sellerProduct.getProductReviews());

                        ProductVariants productVariants = sellerProduct.getProductRows().get(0);
                        response.setColorVariant(productVariants.getColorVariant());
                        response.setProductPrice(productVariants.getProductPrice());
                        response.setProductMrp(productVariants.getProductMrp());
                        response.setCalculatedDiscount(productVariants.getCalculatedDiscount());

                        response.setProductFilesResponses(sellerProduct.getProductFiles().stream()
                                .map(productFiles->modelMapper.map(productFiles,ProductFilesResponse.class)).collect(Collectors.toList()));

                            return response;
                    })
                    .collect(Collectors.toList());

            Page<SellerProductResponse> responsePage = new PageImpl<>(productResponses, data.getPageable(), data.getTotalElements());

            log.info("getBornCategoryList Fetch Data Success:: ");
            return responsePage;
            //ResponseGenerator.generateSuccessResponse(responsePage, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
            return null;
        }
    }

    public static int[] parsePriceRange(String option) {
            int min = 0;
            int max = 0;

            // Matches "Up to ₹100"
            String[] parts = option.split("to");
            min = Integer.parseInt(parts[0]);
            max = Integer.parseInt(parts[1]);
            return new int[]{min, max};
    }


}

