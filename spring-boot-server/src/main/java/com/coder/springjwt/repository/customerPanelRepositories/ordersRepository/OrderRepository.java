package com.coder.springjwt.repository.customerPanelRepositories.ordersRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import com.coder.springjwt.models.customerPanelModels.CustomerOrders;
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


    @Query("SELECT coi FROM CustomerOrders co JOIN co.customerOrderItems coi WHERE co.user.id = :customerId AND co.paymentStatus = :paid")
    List<CustomerOrderItems> findOrderItemsByCustomerId(@Param("customerId") Long customerId, @Param("paid") String paid);



    //Delivered Status Starting for Seller
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

//Delivered Status Starting for Seller






}
