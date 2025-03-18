package com.coder.springjwt.services.publicService.addressService;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.coder.springjwt.constants.customerPanelConstants.messageConstants.CustMessageResponse;
import com.coder.springjwt.dtos.customerPanelDtos.addressDto.AddressDto;
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
        try {
            String currentUser = UserHelper.getOnlyCurrentUser();
            User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                        new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

            long addressCount = this.addressRepository.countByUserId(user.getId());
            if(addressCount >= 50){
                throw new AddressLimitExceededException("User cannot have more than 10 addresses.");
            }

            CustomerAddress customerAddress = modelMapper.map(addressDto, CustomerAddress.class);

            //save Username and Userid
            customerAddress.setUsername(user.getUsername());
            customerAddress.setUserId(user.getId());

            //save User
            customerAddress.setUser(user);

            this.addressRepository.save(customerAddress);
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
      try {
          String currentUser = UserHelper.getOnlyCurrentUser();
          User user = this.userRepository.findByUsername(currentUser).orElseThrow(() ->
                  new UserNotFoundException(CustMessageResponse.USERNAME_NOT_FOUND));

          List<CustomerAddress> customerAddresses = user.getCustomerAddresses();

          List<AddressDto> addressCollector = customerAddresses.stream().map(
                                                ca -> modelMapper.map(ca, AddressDto.class)).collect(Collectors.toList());


          return ResponseGenerator.generateSuccessResponse(addressCollector , CustMessageResponse.SOMETHING_WENT_WRONG);

      }
      catch (Exception e)
      {
          e.printStackTrace();
          return ResponseGenerator.generateBadRequestResponse(CustMessageResponse.SOMETHING_WENT_WRONG);
      }
    }
}
