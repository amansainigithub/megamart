package com.coder.springjwt.services.publicService.addressService;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.addressDto.AddressDto;
import com.coder.springjwt.exception.customerPanelException.DataNotFoundException;
import com.coder.springjwt.globalExceptionHandler.AddressLimitExceededException;
import com.coder.springjwt.helpers.userHelper.UserHelper;
import com.coder.springjwt.models.User;
import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
import com.coder.springjwt.repository.UserRepository;
import com.coder.springjwt.repository.customerPanelRepositories.addressRepository.AddressRepository;
import com.coder.springjwt.util.ResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImple implements  AddressService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> saveAddress(AddressDto addressDto) {
        log.info("<-- saveAddress Flying --> ");
        try {
            CustomerAddress customerAddress = modelMapper.map(addressDto, CustomerAddress.class);

            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                        new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            //Set Address Limit
            long addressCount = this.addressRepository.countByUserId(user.getId());
            if(addressCount >= 50){
                throw new AddressLimitExceededException(CustMessageResponse.NOT_MORE_THAN_50_ADDRESS);
            } else if (addressCount == 0) {
                customerAddress.setDefaultAddress(Boolean.TRUE);
            }

            //Change another Addresses Remove to Default Set Value to FALSE
            if(customerAddress.isDefaultAddress())
            {
                List<CustomerAddress> addressList = this.addressRepository.findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE);
                addressList.stream().forEach(e->{
                        e.setDefaultAddress(Boolean.FALSE);
                        this.addressRepository.save(e);
                });
            }

            //save Username and Userid
            customerAddress.setUsername(user.getUsername());
            customerAddress.setUserId(user.getId());

            //save User
            customerAddress.setUser(user);

            this.addressRepository.save(customerAddress);

            log.info("saveAddress Finishing Point");
            return ResponseGenerator.generateSuccessResponse(addressDto , CustMessageResponse.DATA_SAVED_SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }



    @Override
    public ResponseEntity<?> getAddress() {
        log.info("<-- getAddress Flying --> ");
      try {
          String currentUser = UserHelper.getOnlyCurrentUser();
          User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                        new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

          List<CustomerAddress> customerAddresses = user.getCustomerAddresses();

          List<AddressDto> addressCollector = customerAddresses.stream().map(
                                                ca -> modelMapper.map(ca, AddressDto.class)).collect(Collectors.toList());
          log.info("<-- getAddress Fetch Success --> ");
          return ResponseGenerator.generateSuccessResponse(addressCollector , CustMessageResponse.SOMETHING_WENT_WRONG);
      }
      catch (Exception e)
      {
          e.printStackTrace();
          return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
      }
    }

    @Override
    public ResponseEntity<?> deleteAddress(long id) {
        log.info("<-- deleteAddress Flying --> ");

        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            //Delete Address
            this.addressRepository.deleteById(id);

            //if Last Address is here so make as a Default Address
            long addressCount = this.addressRepository.countByUserId(user.getId());
            if (addressCount == 1) {
                List<CustomerAddress> customerAddressList = this.addressRepository.findAll();
                customerAddressList.stream().forEach(ca->{
                    ca.setDefaultAddress(Boolean.TRUE);
                    this.addressRepository.save(ca);
                });
            }
            log.info("<-- deleteAddress Success --> ");
            return ResponseGenerator.generateSuccessResponse(id,CustMessageResponse.DELETE_SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG,
                    CustMessageResponse.DELETE_FAILED);
        }
    }

    @Override
    public ResponseEntity<?> setDefaultAddress(long id) {
        log.info("<-- setDefaultAddress Flying --> ");

        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                    new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            List<CustomerAddress> addressList = this.addressRepository.findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE);
            addressList.stream().forEach(e->{
                e.setDefaultAddress(Boolean.FALSE);
                this.addressRepository.save(e);
            });

            CustomerAddress changeDefaultAddress = this.addressRepository.findById(id)
                                                            .orElseThrow(()-> new DataNotFoundException(CustMessageResponse.DATA_NOT_FOUND));

            changeDefaultAddress.setDefaultAddress(Boolean.TRUE);
            this.addressRepository.save(changeDefaultAddress);
            log.info("<-- setDefaultAddress Success --> ");
            return ResponseGenerator.generateSuccessResponse(CustMessageResponse.SUCCESS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<?> getAddressById(long id) {
        log.info("<-- getAddressById Flying --> ");
       try {
           String currentUser = UserHelper.getOnlyCurrentUser();
           User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                   new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

           CustomerAddress customerAddress = this.addressRepository.findByUserIdAndId(user.getId(), id);

           log.info("<-- getAddressById Success --> ");
           return ResponseGenerator.generateSuccessResponse(customerAddress,CustMessageResponse.SUCCESS);
       }
       catch (Exception e)
       {
           e.printStackTrace();
           return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
       }
    }

    @Override
    public ResponseEntity<?> updateAddress(AddressDto addressDto) {
        log.info("<-- updateAddress Flying --> ");
         try {
             String currentUser = UserHelper.getOnlyCurrentUser();
             User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                     new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

             if(addressDto.isDefaultAddress())
             {
                 List<CustomerAddress> addressList = this.addressRepository.findByUserIdAndDefaultAddress(user.getId(), Boolean.TRUE);
                 addressList.stream().forEach(e->{
                     e.setDefaultAddress(Boolean.FALSE);
                     this.addressRepository.save(e);
                 });
             }

             CustomerAddress customerAddress = this.addressRepository.findByUserIdAndId(user.getId(), addressDto.getId());
             customerAddress.setId(addressDto.getId());
             customerAddress.setCountry(addressDto.getCountry());
             customerAddress.setCustomerName(addressDto.getCustomerName());
             customerAddress.setMobileNumber(addressDto.getMobileNumber());
             customerAddress.setArea(addressDto.getArea());
             customerAddress.setPostalCode(addressDto.getPostalCode());
             customerAddress.setAddressLine1(addressDto.getAddressLine1());
             customerAddress.setAddressLine2(addressDto.getAddressLine2());
             customerAddress.setUser(user);
             customerAddress.setUserId(user.getId());
             customerAddress.setUsername(user.getUsername());
             customerAddress.setDefaultAddress(addressDto.isDefaultAddress());

             this.addressRepository.save(customerAddress);

             log.info(" <-- updateAddress Success --> ");
             return ResponseGenerator.generateSuccessResponse(CustMessageResponse.UPDATE_SUCCESS, CustMessageResponse.SUCCESS);
         }
         catch (Exception e){
             e.printStackTrace();
             return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
         }
    }
}
