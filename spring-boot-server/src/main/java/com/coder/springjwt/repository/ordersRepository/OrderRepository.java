package com.coder.springjwt.repository.ordersRepository;


import com.coder.springjwt.models.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrders,Long> {

    CustomerOrders findByOrderId(String orderId);
}
