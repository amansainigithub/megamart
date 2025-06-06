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
            "AND coi.deliveryStatus IN ('PENDING' , 'SHIPPED' , 'OUT_OF_DELIVERY' , 'CANCELLED')" +
            "ORDER BY coi.creationDate DESC")
    Page<CustomerOrderItems> findOrderItemsExceptDelivered(@Param("customerId") Long customerId , Pageable  pageable);

    @Query("SELECT coi FROM CustomerOrderItems coi "+
            "WHERE coi.userId = :customerId " +
            "AND coi.deliveryStatus IN ('DELIVERED','RETURN','EXCHANGE')" +
            "ORDER BY coi.creationDate DESC")
    Page<CustomerOrderItems> findOrderItemsDelivered(@Param("customerId") Long customerId , Pageable  pageable);

    @Query("SELECT coi FROM CustomerOrderItems coi "+
            "WHERE coi.userId = :userId " +
            "AND coi.id = :id")
    CustomerOrderItems findOrderItemsById(@Param("userId") Long userId, @Param("id") Long id);


    Page<CustomerOrderItems> findAllByDeliveryStatus(String deliveryStatus , Pageable pageable);

    Page<CustomerOrderItems> findAllByDeliveryStatusAndRefundStatus(String deliveryStatus , String refundStatus, Pageable pageable);


//    @Query("SELECT coi, r FROM CustomerOrderItems coi " +
//            "LEFT JOIN Ratings r ON coi.id = CAST(r.orderItemsId as long) " +
//            "WHERE coi.userId = :userId AND r.orderItemsId IS NULL")
//    List<Object[]> findAllWithoutRatingsWithDetails(@Param("userId") String userId);


    Page<CustomerOrderItems> findByUserIdAndDeliveryStatusAndIsRatingOrderByIdDesc(  String userId,
                                                                                    String deliveryStatus,
                                                                                    boolean isRating,Pageable pageable);

    CustomerOrderItems findByIdAndUserIdAndDeliveryStatusAndIsRating(Long id,
                                                                     String userId,
                                                                     String deliveryStatus,
                                                                     boolean isRating);


    // Method 1: Derived Query Method
    long countByDeliveryStatusAndUserId(String deliveryStatus, String userId);

    long countByUserId(String userId);


    @Query("SELECT coi FROM CustomerOrderItems coi "+
            "WHERE coi.id = :id " +
            "AND coi.userId = :userId " +
            "AND coi.customOrderNumber = :customOrderNumber " +
            "AND coi.deliveryStatus = :deliveryStatus " +
            "AND coi.razorpayOrderId = :razorpayOrderId ")
    CustomerOrderItems findByCancelOrder(@Param("id") String id,
                                         @Param("userId") String userId ,
                                         @Param("customOrderNumber") String customOrderNumber,
                                         @Param("deliveryStatus") String deliveryStatus,
                                         @Param("razorpayOrderId") String razorpayOrderId);


    CustomerOrderItems findByIdAndUserIdAndDeliveryStatus( Long id,String userId,String deliveryStatus);


    List<CustomerOrderItems> findAllByDeliveryStatus(String deliveryStatus);

}
