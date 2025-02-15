package com.coder.springjwt.exception.services.sellerServices.sellerProductCategoryService.imple;

import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.babyDto.BabyCategoryDto;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.bornDtos.BornCategoryDto;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.childDtos.ChildCategoryDto;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.parentDtos.ParentCategoryDto;
import com.coder.springjwt.exception.services.sellerServices.sellerProductCategoryService.SellerProductCategoryService;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ChildCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.ParentCategoryModel;
import com.coder.springjwt.repository.sellerRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ChildCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.ParentCategoryRepo;
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

    @Override
    public ResponseEntity<?> getBornById(Long bornId) {
        try {
            Optional<BornCategoryModel> bornData = bornCategoryRepo.findById(Long.valueOf(bornId));

            if(bornData.isPresent()) {

                Map<Object , Object> node = new HashMap<>();

                    BornCategoryModel bornNode = bornData.get();
                    BornCategoryDto bornCategoryDto = new BornCategoryDto();
                    bornCategoryDto.setCategoryName(bornNode.getCategoryName());
                    bornCategoryDto.setId(bornNode.getId());
                    bornCategoryDto.setCategoryFile(bornNode.getCategoryFile());
                    bornCategoryDto.setBabyCategoryId(String.valueOf(bornNode.getBabyCategoryModel().getId()));

                    node.put("node",bornNode);
                    node.put("categoryBreadCrumb" , bornNode.getCategoryName() + "/"
                            + bornNode.getBabyCategoryModel().getCategoryName() + "/"
                            +bornNode.getBabyCategoryModel().getChildCategoryModel().getCategoryName() + "/"
                            +bornNode.getBabyCategoryModel().getChildCategoryModel().getParentCategory().getCategoryName());

                log.info("Born Category Data Fetched Success");
                return ResponseGenerator.generateSuccessResponse(node,SellerMessageResponse.SUCCESS);
            }else{
                log.info("Born Category Data Fetched Failed");
                return ResponseGenerator.generateBadRequestResponse(bornData,SellerMessageResponse.FAILED);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("Something went Wrong ::=> "  + SellerProductCategoryService.class.getName());
            return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.DATA_NOT_FOUND);
        }
    }
}
