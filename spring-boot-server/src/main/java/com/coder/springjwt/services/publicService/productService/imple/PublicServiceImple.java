package com.coder.springjwt.services.publicService.productService.imple;

import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.BabyCategoryDto;
import com.coder.springjwt.dtos.customerPanelDtos.BornCategoryDto;
import com.coder.springjwt.dtos.customerPanelDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.customerPanelDtos.ParentCategoryDto;
import com.coder.springjwt.emuns.ProductStatus;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.models.sellerModels.homeSliders.HomeSliderModel;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsEngineModel;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    @Override
    public ResponseEntity<?> getProductCategoryService() {
        log.info("<--- getProductListByCategoryId Flying --->");

        Map<Object,Object> mapNode = new HashMap<>();

        try {
            List<ParentCategoryModel> parentCategories = this.parentCategoryRepo.findAll();

            //Get Hole Slider Data
            List<HomeSliderModel> homeSliderData = this.homeSliderRepo.findAll();

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
            List<BabyCategoryModel> babyDataFilter = babyList.stream().map(
                    b -> new BabyCategoryModel(b.getId(), b.getCategoryName(),b.getCategoryFile()))
                    .collect(Collectors.toList());


            //get HotDealEngine Data
            HotDealsEngineModel hotDealEngine = this.hotDealsEngineRepo.findById(1L).get();
            List<HotDealsModel> hotDeals = hotDealEngine.getHotDealsModels();

            //Get Parent Categories only Men
            Pageable mensListPageable = PageRequest.of(0, 22);
            List<BornCategoryModel> mensList = this.bornCategoryRepo.getBornCategoryListByParentCategoryId(1L,mensListPageable);

            Page<SellerProductResponse> productsList = this.getBornCategoryList(1l, "Mens Top Wear", 0, 999);


            mapNode.put("homeSliderData",homeSliderData);
            mapNode.put("listOfCategories",listOfCategories);
            mapNode.put("babyDataFilter",babyDataFilter);
            mapNode.put("hotDealEngine",hotDealEngine);
            mapNode.put("hotDeals",hotDeals);
            mapNode.put("mensList",mensList);
            mapNode.put("productsList",productsList.getContent());

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
    public ResponseEntity<?> getProductListDeal99(long categoryId, String categoryName, Integer page, Integer size) {
        log.info("<--- getProductListDeal99 Flying --->");

        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(categoryId)
                    .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            Pageable pageable = PageRequest.of(page, size);

            Page<SellerProduct> pagedSellerProducts = this.sellerProductRepository
                    .findByBornCategoryIdAndProductStatus(
                            String.valueOf(bornCategoryModel.getId()),
                            ProductStatus.PV_APPROVED.toString(),
                            pageable);

            List<SellerProductResponse> sellerProductResponsesList = pagedSellerProducts.getContent()
                    .stream()
                    .map(sp -> {
                        SellerProductResponse response = modelMapper.map(sp, SellerProductResponse.class);
                        ProductVariants productVariants = sp.getProductRows().get(0);
                        if (Integer.parseInt(productVariants.getProductPrice()) < 100) {
                            response.setColorVariant(productVariants.getColorVariant());
                            response.setProductPrice(productVariants.getProductPrice());
                            response.setProductMrp(productVariants.getProductMrp());
                            response.setCalculatedDiscount(productVariants.getCalculatedDiscount());
                            response.setProductFilesResponses(sp.getProductFiles()
                                    .stream()
                                    .map(productFiles -> modelMapper.map(productFiles, ProductFilesResponse.class))
                                    .collect(Collectors.toList()));
                            return response;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            Page<SellerProductResponse> pagedResponse = new PageImpl<>(sellerProductResponsesList, pageable, pagedSellerProducts.getTotalElements());
            log.info("getProductListDeal99 Fetch Data Success ::");
            return ResponseGenerator.generateSuccessResponse(pagedResponse, SellerMessageResponse.SUCCESS);
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
            Page<SellerProductResponse> similarProduct = this.getBornCategoryList(
                                                            Long.valueOf(sellerProduct.getBornCategoryId()),
                                                            sellerProduct.getBornCategoryName(),
                                                            0,
                                                            99);

            map.put("pw",response);
            map.put("similarProducts",similarProduct);

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
                    .findByBornCategoryId(String.valueOf(bornCategoryModel.getId()) , pageRequest );

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


}

