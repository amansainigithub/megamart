package com.coder.springjwt.repository.customerPanelRepositories.ordersRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrders,Long> {
    CustomerOrders findByOrderId(String orderId);

    @Query(value = "SELECT COUNT(co.id) FROM customer_Order co " +
                    "JOIN customer_order_items coi ON co.id = coi.order_id " +
                    "WHERE co.customer_id = :customerId " +
                    "AND co.status = 'PAID'"+
                    "AND coi.order_status = :pending",
                    nativeQuery = true)
    Long countOrdersByCustomerIdNative(@Param("customerId") Long customerId, @Param("pending") String pending);

    @Query(value = "SELECT COUNT(co.id) FROM customer_Order co " +
                    "JOIN customer_order_items coi ON co.id = coi.order_id " +
                    "WHERE co.customer_id = :customerId AND co.status = 'PAID'",
                    nativeQuery = true)
    Long countTotalOrdersByCustomerIdNative(@Param("customerId") Long customerId);




    //    @Query("SELECT co FROM CustomerOrders co WHERE co.user.id = :customerId")
    //    List<CustomerOrders> findOrdersByCustomerId(@Param("customerId") Long customerId);
    //    @Query(value = "SELECT * FROM customer_Order WHERE customer_id = :customerId", nativeQuery = true)
    //    List<CustomerOrders> findOrdersByCustomerIdNative(@Param("customerId") Long customerId);



}
