package com.coder.springjwt.exception.services.sellerServices.categories.bornCategoryImple;

import com.coder.springjwt.bucket.bucketModels.BucketModel;
import com.coder.springjwt.bucket.bucketService.BucketService;
import com.coder.springjwt.constants.customerConstants.messageConstants.test.CustMessageResponse;
import com.coder.springjwt.constants.sellerConstants.sellerMessageConstants.SellerMessageResponse;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.bornDtos.BornCategoryDto;
import com.coder.springjwt.dtos.sellerDtos.categoriesDtos.bornDtos.FileMetadata;
import com.coder.springjwt.exception.customerException.CategoryNotFoundException;
import com.coder.springjwt.exception.customerException.DataNotFoundException;
import com.coder.springjwt.models.sellerModels.categories.BabyCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategoryModel;
import com.coder.springjwt.models.sellerModels.categories.BornCategorySampleFilesModel;
import com.coder.springjwt.repository.sellerRepository.categories.BabyCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BornCategoryRepo;
import com.coder.springjwt.repository.sellerRepository.categories.BornSampleFilesRepo;
import com.coder.springjwt.exception.services.sellerServices.categories.BornCategoryService;
import com.coder.springjwt.util.MessageResponse;
import com.coder.springjwt.util.ResponseGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BornCategoryServiceImple implements BornCategoryService {

    @Autowired
    BabyCategoryRepo babyCategoryRepo;

    @Autowired
    BornCategoryRepo bornCategoryRepo;

    @Autowired
    BornSampleFilesRepo bornSampleFilesRepo;

    @Autowired
    BucketService bucketService;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger =  LoggerFactory.getLogger(BornCategoryServiceImple.class);

    @Override
    public ResponseEntity<?> saveBornCategory(BornCategoryDto bornCategoryDto) {
        MessageResponse response =new MessageResponse();
        try {
            BornCategoryModel bornCategoryModel=  modelMapper.map(bornCategoryDto , BornCategoryModel.class);
            logger.info("Mapper Convert Success");

            System.out.println("Born Model =>  " + bornCategoryModel);

            System.out.println("Born DTO =>  " + bornCategoryDto);

            Optional<BabyCategoryModel> babyOptional =
                    this.babyCategoryRepo.findById(Long.parseLong(bornCategoryDto.getBabyCategoryId()));

            logger.info("bornOptional Running 1.....");
            logger.info("IS pResent :: "+String.valueOf(babyOptional.isPresent()));

            if(babyOptional.isPresent()) {
                logger.info("Data Present Success");
                bornCategoryModel.setBabyCategoryModel(babyOptional.get());

                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                bornCategoryModel.setUser(auth.getName());
                //save Category
                this.bornCategoryRepo.save(bornCategoryModel);

                logger.info("Born-Category Saved Success");
                response.setMessage("Born-Category Saved Success");
                response.setStatus(HttpStatus.OK);
                return ResponseGenerator.generateSuccessResponse(response, "Success");
            }
            else{
                logger.error("Baby Category Not Found Via Id : "+ bornCategoryDto.getBabyCategoryId());
                response.setMessage("Baby Category Not Found Via Id : "+ bornCategoryDto.getBabyCategoryId());
                response.setStatus(HttpStatus.BAD_REQUEST);
                return ResponseGenerator.generateBadRequestResponse(response);
            }
        }
        catch (DataIntegrityViolationException ex) {
            // Handle exception here
            logger.error("Duplicate entry error: ");
            response.setMessage("Duplicate entry error: ");
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return ResponseGenerator.generateBadRequestResponse(response);
        }
    }

    @Override
    public ResponseEntity<?> getBornCategoryList() {
        try {
            List<BornCategoryModel> bornList =  this.bornCategoryRepo.findAll();
            List<BornCategoryDto> bornCategoryDtos =   bornList
                    .stream()
                    .map(category -> modelMapper.map(category, BornCategoryDto.class))
                    .collect(Collectors.toList());
            return ResponseGenerator.generateSuccessResponse(bornCategoryDtos,"Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Failed");
        }
    }

    @Override
    public ResponseEntity<?> deleteBornCategoryById(long categoryId) {
        try {
            BornCategoryModel data = this.bornCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new CategoryNotFoundException("Category id not Found"));

            this.bornCategoryRepo.deleteById(data.getId());
            logger.info("Delete Success => Category id :: " + categoryId );
            return ResponseGenerator.generateSuccessResponse("Delete Success" , "Success");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Category Could Not deleted");
            return ResponseGenerator.generateBadRequestResponse
                    ("Category Could not deleted :: " + e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateBornCategory(BornCategoryDto bornCategoryDto) {
        MessageResponse response = new MessageResponse();
        try {

            logger.info(bornCategoryDto.toString());

            //get Born Data
            BornCategoryModel bornData =   this.bornCategoryRepo.findById(bornCategoryDto.getId()).
                    orElseThrow(()->new DataNotFoundException("Data not Found"));

            //getBaby Category By ID
            BabyCategoryModel babyData =   this.babyCategoryRepo.findById(Long.valueOf(bornCategoryDto.getBabyCategoryId())).
                    orElseThrow(()->new DataNotFoundException("Data not Found"));


//            Convert DTO TO Model Class
            BornCategoryModel bornCategoryModel =  modelMapper.map(bornCategoryDto , BornCategoryModel.class);

            //Set baby Data in Modal
            bornCategoryModel.setBabyCategoryModel(babyData);

            this.bornCategoryRepo.save(bornCategoryModel);

            logger.info("Data Update Success");
            return ResponseGenerator.generateSuccessResponse("Success" , "Data update Success");

        }
        catch (Exception e)
        {
            logger.info("Data Update Failed");
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("failed" ," Data Update Failed");
        }
    }

    @Override
    public ResponseEntity<?> getBornCategoryById(long categoryId) {
        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(categoryId).orElseThrow(
                    () -> new RuntimeException("Data not Found ! Error"));
            BornCategoryDto bornCategoryDto = modelMapper.map(bornCategoryModel, BornCategoryDto.class);
            logger.info("Born Category Fetch Success !");
            return ResponseGenerator.generateSuccessResponse(bornCategoryDto , "Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            logger.error("");
            return ResponseGenerator.generateBadRequestResponse(e.getMessage() , "Error");
        }
    }

    @Override
    public ResponseEntity<?> updateBornCategoryFile(MultipartFile file, Long bornCategoryId) {
        try {
            BornCategoryModel bornCategoryModel = this.bornCategoryRepo.findById(bornCategoryId).orElseThrow(()-> new DataNotFoundException("DATA_NOT_FOUND"));

            //Delete old File
            try {
                bucketService.deleteFile(bornCategoryModel.getCategoryFile());
            }catch (Exception e)
            {
                logger.error("File Not deleted Id:: " + bornCategoryId);
            }
            //upload New File
            BucketModel bucketModel = bucketService.uploadFile(file);
            if(bucketModel != null)
            {
                bornCategoryModel.setCategoryFile(bucketModel.getBucketUrl());
                this.bornCategoryRepo.save(bornCategoryModel);
                return ResponseGenerator.generateSuccessResponse("Success","File Update Success");
            }
            else {
                logger.error("Bucket Model is null | please check AWS bucket configuration");
                throw new Exception("Bucket AWS is Empty");
            }
        }
        catch (Exception e)
        {
            logger.info("Exception" , e.getMessage());
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse("Error" ,"File Not Update");
        }
    }

    @Override
    public ResponseEntity<?> getBornCategoryListByPagination(Integer page, Integer size) {
        Page<BornCategoryModel> bornData = this.bornCategoryRepo.findAll(PageRequest.of(page , size, Sort.by("id").descending()));
        System.out.println("BORN DATA " + bornData.toString());
        if(!bornData.isEmpty()) {

            return ResponseGenerator.generateSuccessResponse(bornData, "BORN_SUCCESSFULLY_FETCH");
        }else {
            log.info("Born Data Data Not Found its NULL or BLANK ::::::::: {}", "BornCategory" );
            return ResponseGenerator.generateDataNotFound(CustMessageResponse.DATA_NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> sampleFilesService(Long bornCategoryId, List<MultipartFile> files, List<String> metadataList) {

       try {
           if (files.size() != metadataList.size()) {
               return ResponseEntity.badRequest().body("Mismatched input sizes.");
           }

           List<BornCategorySampleFilesModel> uploadedFiles = new ArrayList<>();
           for (int i = 0; i < files.size(); i++) {
               MultipartFile file = files.get(i);
               String metadataJson = metadataList.get(i);

               // Parse JSON metadata
               FileMetadata fileMetadata = parseMetadata(metadataJson);
               if (fileMetadata != null) {
                   fileMetadata.setFileName(file.getOriginalFilename());
                   // Convert FileMetadata to BornCategorySampleFilesModel
                   BornCategorySampleFilesModel sampleFileModel = modelMapper.map(fileMetadata, BornCategorySampleFilesModel.class);
                   // Add to list
                   uploadedFiles.add(sampleFileModel);

               }
           }

           Optional<BornCategoryModel> bornData = this.bornCategoryRepo.findById(bornCategoryId);
           if(bornData.isPresent())
           {
               BornCategoryModel bornCategoryModel = bornData.get();

               for(BornCategorySampleFilesModel dataInsider : uploadedFiles){
                   dataInsider.setBornCategoryModel(bornCategoryModel);
                   this.bornSampleFilesRepo.save(dataInsider);
               }
               System.out.println("Data Saved Success!!!!");
           }

           return ResponseGenerator.generateSuccessResponse(uploadedFiles, SellerMessageResponse.SUCCESS);
       }
       catch (Exception e){
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(SellerMessageResponse.FAILED);
       }
    }


    private FileMetadata parseMetadata(String json) {
        // Use a JSON parser (e.g., Jackson or Gson) to parse the metadata
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, FileMetadata.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
