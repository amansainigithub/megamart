package com.coder.springjwt.repository.customerPanelRepositories.addressRepository;

import com.coder.springjwt.models.customerPanelModels.address.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<CustomerAddress , Long> {

    long countByUserId(Long userId);

}
