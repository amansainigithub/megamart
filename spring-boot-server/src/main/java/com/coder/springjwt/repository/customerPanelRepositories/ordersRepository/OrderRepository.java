package com.coder.springjwt.repository.customerPanelRepositories.ordersRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrders,Long> {

    CustomerOrders findByOrderId(String orderId);
}
