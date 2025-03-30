package com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<CustomerOrderItems,Long> {
}
