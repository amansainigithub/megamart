package com.coder.springjwt.repository.customerPanelRepositories.orderItemsRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerOrderItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<CustomerOrderItems,Long> {

    @Query("SELECT coi FROM CustomerOrderItems coi "+
            "WHERE coi.userId = :customerId " +
            "AND coi.deliveryStatus IN ('PENDING' , 'SHIPPED' , 'OUT_OF_DELIVERY')" +
            "ORDER BY coi.creationDate DESC")
    List<CustomerOrderItems> findOrderItemsExceptDelivered(@Param("customerId") Long customerId);

    @Query("SELECT coi FROM CustomerOrderItems coi "+
            "WHERE coi.userId = :customerId " +
            "AND coi.deliveryStatus IN ('DELIVERED')" +
            "ORDER BY coi.creationDate DESC")
    List<CustomerOrderItems> findOrderItemsDelivered(@Param("customerId") Long customerId);

    @Query("SELECT coi FROM CustomerOrderItems coi "+
            "WHERE coi.userId = :userId " +
            "AND coi.id = :id")
    CustomerOrderItems findOrderItemsById(@Param("userId") Long userId, @Param("id") Long id);


    Page<CustomerOrderItems> findAllByDeliveryStatus(String deliveryStatus , Pageable pageable);


    @Query("SELECT coi, r FROM CustomerOrderItems coi " +
            "LEFT JOIN Ratings r ON coi.id = CAST(r.orderItemsId as long) " +
            "WHERE coi.userId = :userId AND r.orderItemsId IS NOT NULL")
    List<Object[]> findAllWithoutRatingsWithDetails(@Param("userId") String userId);







}
