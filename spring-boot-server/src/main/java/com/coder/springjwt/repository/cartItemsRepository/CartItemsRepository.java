package com.coder.springjwt.repository.cartItemsRepository;

import com.coder.springjwt.dtos.cartItemsDto.CartItemsDto;
import com.coder.springjwt.models.CustomerOrderItems;
import com.coder.springjwt.models.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CustomerOrderItems,Long> {
}
