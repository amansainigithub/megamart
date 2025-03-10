package com.coder.springjwt.repository.cartItemsRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CustomerOrderItems,Long> {
}
