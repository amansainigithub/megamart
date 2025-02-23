package com.coder.springjwt.services.publicService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.customerDtos.BabyCategoryDto;
import com.coder.springjwt.dtos.customerDtos.BornCategoryDto;
import com.coder.springjwt.dtos.customerDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.customerDtos.ParentCategoryDto;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.models.sellerModels.homeSliders.HomeSliderModel;
import com.coder.springjwt.repository.homeSliderRepo.HomeSliderRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.publicService.PublicService;
import com.coder.springjwt.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PublicServiceImple implements PublicService {
    @Autowired
    private ParentCategoryRepo parentCategoryRepo;

    @Autowired
    private HomeSliderRepo homeSliderRepo;

    @Autowired
    private BabyCategoryRepo babyCategoryRepo;

    @Override
    public ResponseEntity<?> getProductCategoryService() {

        Map<Object,Object> mapNode = new HashMap<>();

        try {
            List<ParentCategoryModel> parentCategories = this.parentCategoryRepo.findAll();


//            List<ParentCategoryDto> collect = parentCategories.stream().map(parent ->
//                    new ParentCategoryDto(parent.getId(), parent.getCategoryName(),
//
//                            parent.getChildCategoryModelList().stream().map(child->
//                                            new ChildCategoryDto(child.getId(),child.getCategoryName(),
//                                            child.getBabyCategoryModel().stream().map(baby->
//                                                    new BabyCategoryDto(baby.getId(),baby.getCategoryName(),
//                                                            baby.getBornCategoryModel().stream()
//                                                                    .map(born->
//                                                                            new BornCategoryDto(born.getId(),born.getCategoryName()))
//                                                                    .collect(Collectors.toList())
//                                                    ))
//                                                    .collect(Collectors.toList())
//                                            ))
//                                    .collect(Collectors.toList())
//
//                    )).collect(Collectors.toList());



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


            //Get Hole Slider Data
            List<HomeSliderModel> homeSliderData = this.homeSliderRepo.findAll();

            //get Baby Category
            Pageable pageable = PageRequest.of(0, 17);
            List<BabyCategoryModel> babyList = this.babyCategoryRepo.findAll(pageable).getContent();
            List<BabyCategoryModel> babyDataFilter = babyList.stream().map(
                    b -> new BabyCategoryModel(b.getId(), b.getCategoryName(),b.getCategoryFile()))
                    .collect(Collectors.toList());

            mapNode.put("listOfCategories",listOfCategories);
            mapNode.put("homeSliderData",homeSliderData);
            mapNode.put("babyDataFilter",babyDataFilter);
            return ResponseGenerator.generateSuccessResponse(mapNode, SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }
}
