package com.coder.springjwt.services.sellerServices.sellerProductCategoryService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.bornDtos.BornCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.adminDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.models.adminModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.adminModels.categories.BornCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.adminModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.adminRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.ChildCategoryRepo;
import com.coder.springjwt.repository.adminRepository.categories.ParentCategoryRepo;
import com.coder.springjwt.services.sellerServices.sellerProductCategoryService.SellerProductCategoryService;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SellerProductCategoryServiceImple implements SellerProductCategoryService {


    @Autowired
    private ParentCategoryRepo parentCategoryRepo;

    @Autowired
    private ChildCategoryRepo childCategoryRepo;

    @Autowired
    private BabyCategoryRepo babyCategoryRepo;

    @Autowired
    private BornCategoryRepo bornCategoryRepo;

    Logger logger =  LoggerFactory.getLogger(SellerProductCategoryService.class);

    @Override
    public ResponseEntity<?> getParentCategory() {
        try {
            List<ParentCategoryModel> parentData = this.parentCategoryRepo.findAll();

            List<Object> parentNode = parentData.stream().map(parent -> {
                ParentCategoryDto parentCategoryDto = new ParentCategoryDto();
                parentCategoryDto.setCategoryName(parent.getCategoryName());
                parentCategoryDto.setId(parent.getId());
                return parentCategoryDto;
            }).collect(Collectors.toList());


            log.info("Parent Category Data Fetched Success");
            return ResponseGenerator.generateSuccessResponse(parentNode,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("Something went Wrong ::=> "  + SellerProductCategoryService.class.getName());
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getChildCategoryListById(Long parentId) {
        try {
            List<ChildCategoryModel> childData=  childCategoryRepo.getChildCategoriesListByParentCategoryId(parentId);

            List<Object> childNode = childData.stream().map(child -> {
                ChildCategoryDto childCategoryDto = new ChildCategoryDto();
                childCategoryDto.setCategoryName(child.getCategoryName());
                childCategoryDto.setId(child.getId());
                childCategoryDto.setParentCategoryId(String.valueOf(child.getParentCategory().getId()));
                return childCategoryDto;
            }).collect(Collectors.toList());


            log.info("Child Category Data Fetched Success");
            return ResponseGenerator.generateSuccessResponse(childNode,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("Something went Wrong ::=> "  + SellerProductCategoryService.class.getName());
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getBabyCategoryListChildById(Long childId) {
        try {
            List<BabyCategoryModel> babyData=  babyCategoryRepo.getBabyCategoryListByChildCategoryId(childId);

            List<Object> babyNode = babyData.stream().map(baby -> {
                BabyCategoryDto babyCategoryDto = new BabyCategoryDto();
                babyCategoryDto.setCategoryName(baby.getCategoryName());
                babyCategoryDto.setId(baby.getId());
                babyCategoryDto.setChildCategoryId (String.valueOf(baby.getChildCategoryModel().getId()));
                return babyCategoryDto;
            }).collect(Collectors.toList());


            log.info("Baby Category Data Fetched Success");
            return ResponseGenerator.generateSuccessResponse(babyNode,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("Something went Wrong ::=> "  + SellerProductCategoryService.class.getName());
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getBornCategoryListByBabyId(Long babyId) {
        try {
            List<BornCategoryModel> bornData=  bornCategoryRepo.getBornCategoryListByBabyCategoryId(babyId);

            List<Object> bornNode = bornData.stream().map(born -> {
                BornCategoryDto bornCategoryDto = new BornCategoryDto();
                bornCategoryDto.setCategoryName(born.getCategoryName());
                bornCategoryDto.setId(born.getId());
                bornCategoryDto.setBabyCategoryId(String.valueOf(born.getBabyCategoryModel().getId()));
                return bornCategoryDto;
            }).collect(Collectors.toList());


            log.info("Born Category Data Fetched Success");
            return ResponseGenerator.generateSuccessResponse(bornNode,SellerMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("Something went Wrong ::=> "  + SellerProductCategoryService.class.getName());
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }
}
