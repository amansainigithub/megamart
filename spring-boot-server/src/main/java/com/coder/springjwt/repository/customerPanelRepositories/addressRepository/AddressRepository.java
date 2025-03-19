package com.coder.springjwt.repository.customerPanelRepositories.addressRepository;

import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<CustomerAddress , Long> {

    long countByUserId(Long userId);

    List<CustomerAddress> findByUserIdAndDefaultAddress(Long userId , boolean defaultAddress);

    CustomerAddress findByUserIdAndId(Long userId , Long id);

}
