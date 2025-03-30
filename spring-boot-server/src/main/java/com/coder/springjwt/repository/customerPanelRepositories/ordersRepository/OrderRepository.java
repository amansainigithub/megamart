package com.coder.springjwt.repository.customerPanelRepositories.ordersRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
import com.razorpay.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                    "AND co.payment_status = 'PAID'"+
                    "AND coi.delivery_status = :pending",
                    nativeQuery = true)
    Long countOrdersByCustomerIdNative(@Param("customerId") Long customerId, @Param("pending") String pending);

    @Query(value = "SELECT COUNT(co.id) FROM customer_Order co " +
                    "JOIN customer_order_items coi ON co.id = coi.order_id " +
                    "WHERE co.customer_id = :customerId AND co.payment_status = 'PAID'",
                    nativeQuery = true)
    Long countTotalOrdersByCustomerIdNative(@Param("customerId") Long customerId);


    @Query("SELECT coi FROM CustomerOrders co JOIN co.customerOrderItems coi WHERE co.user.id = :customerId AND co.paymentStatus = :paid")
    List<CustomerOrderItems> findOrderItemsByCustomerId(@Param("customerId") Long customerId, @Param("paid") String paid);

    @Query("SELECT co FROM CustomerOrders co JOIN co.customerOrderItems coi " +
            "WHERE co.user.id = :customerId " +
            "AND co.paymentStatus IN ('PAID', 'UNPAID') " +  // Allow both PAID and UNPAID
            "ORDER BY co.creationDate DESC")
    List<CustomerOrders> findOrderItemsByCustomerIdCustom(@Param("customerId") Long customerId);


    @Query("SELECT co FROM CustomerOrders co JOIN co.customerOrderItems coi " +
            "WHERE co.user.id = :customerId AND co.id = :id  ")
    CustomerOrders getOrderWithUserIdAndOrderId( @Param("customerId") Long customerId,
                                                           @Param("id") long id);


    @Query("SELECT co FROM CustomerOrders co " +
            "JOIN co.customerOrderItems coi " +
            "WHERE coi.deliveryStatus = :pending and coi.customerOrders.id = co.id")
    Page<CustomerOrders> getPendingOrderItems(@Param("pending") String pending, Pageable pageable);

    @Query("SELECT co FROM CustomerOrders co " +
            "JOIN co.customerOrderItems coi " +
            "WHERE coi.deliveryStatus = :shipped")
    Page<CustomerOrders> getShippedOrders(@Param("shipped") String shipped, Pageable pageable);

    @Query("SELECT co FROM CustomerOrders co " +
            "JOIN co.customerOrderItems coi " +
            "WHERE coi.deliveryStatus = :outOfDelivery")
    Page<CustomerOrders> getOutOfDeliveryOrders(@Param("outOfDelivery") String outOfDelivery, Pageable pageable);

    @Query("SELECT co FROM CustomerOrders co " +
            "JOIN co.customerOrderItems coi " +
            "WHERE coi.deliveryStatus = :delivered")
    Page<CustomerOrders> getDeliveryOrders(@Param("delivered") String delivered, Pageable pageable);








}
