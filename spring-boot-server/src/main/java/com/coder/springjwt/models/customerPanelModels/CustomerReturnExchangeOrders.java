package com.coder.springjwt.models.customerPanelModels;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customerReturnExchangeOrders")
public class CustomerReturnExchangeOrders extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //BANK DETAILS COD
    private String accountNumber;
    private String ifscCode;
    private String bankName;

    private String returnReason;
    private String returnDeliveryPickUpDateTime;
    private String returnDeliveryStatus;
    private String returnRefundStatus;
    private String returnRefundId;


    //Exchange Items Properties
    private String exchangeReason;
    private String exchangeDeliveryPickUpDateTime;
    private String exchangeDeliveryStatus;
    private String exchangeProductSize;

    //COMMON PROPERTIES
    private String productId;
    private long orderItemId;
    private String paymentMode;

}
