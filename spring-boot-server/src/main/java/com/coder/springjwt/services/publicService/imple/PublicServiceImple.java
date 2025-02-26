package com.coder.springjwt.services.publicService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.customerDtos.BabyCategoryDto;
import com.coder.springjwt.dtos.customerDtos.BornCategoryDto;
import com.coder.springjwt.dtos.customerDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.customerDtos.ParentCategoryDto;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.models.sellerModels.homeSliders.HomeSliderModel;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsEngineModel;
import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsModel;
import com.coder.springjwt.models.sellerModels.sellerProductModels.ProductVariants;
import com.coder.springjwt.models.sellerModels.sellerProductModels.SellerProduct;
import com.coder.springjwt.repository.homeSliderRepo.HomeSliderRepo;
import com.coder.springjwt.repository.hotDealsRepos.HotDealsEngineRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.sellerStoreRepository.SellerProductRepository;
import com.coder.springjwt.response.sellerProductResponse.ProductFilesResponse;
import com.coder.springjwt.response.sellerProductResponse.SellerProductResponse;
import com.coder.springjwt.response.sellerProductResponse.SellerProductVarientResponse;
import com.coder.springjwt.services.publicService.PublicService;
import com.coder.springjwt.util.ResponseGenerator;
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

@Service
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
            HotDealsEngineModel hotDealEngine = this.hotDealsEngineRepo.findById(16L).get();
            List<HotDealsModel> hotDeals = hotDealEngine.getHotDealsModels();

            //Get Parent Categories only Men
            Pageable mensListPageable = PageRequest.of(0, 22);
            List<BornCategoryModel> mensList = this.bornCategoryRepo.getBornCategoryListByParentCategoryId(2L,mensListPageable);

            //Get Parent Categories only Women
            Pageable womenListPageable = PageRequest.of(0, 22);
            List<BornCategoryModel> womenList = this.bornCategoryRepo.getBornCategoryListByParentCategoryId(5L,womenListPageable);

            mapNode.put("homeSliderData",homeSliderData);
            mapNode.put("listOfCategories",listOfCategories);
            mapNode.put("babyDataFilter",babyDataFilter);
            mapNode.put("hotDealEngine",hotDealEngine);
            mapNode.put("hotDeals",hotDeals);
            mapNode.put("mensList",mensList);
            mapNode.put("womenList",womenList);
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
        try {
            BabyCategoryModel babyCategoryModel = this.babyCategoryRepo.findById(categoryId)
                    .orElseThrow(()-> new DataNotFoundException(SellerMessageResponse.DATA_NOT_FOUND));

            PageRequest pageRequest= PageRequest.of(page, size);
            Page<SellerProduct> babyCategoryData = this.sellerProductRepository
                                                    .findByBabyCategoryId(String.valueOf(babyCategoryModel.getId()),pageRequest);


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

            return ResponseGenerator.generateSuccessResponse(responsePage, SellerMessageResponse.SUCCESS);
        }catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }



    }

