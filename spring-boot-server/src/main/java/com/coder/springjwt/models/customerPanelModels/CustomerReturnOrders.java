package com.coder.springjwt.models.customerPanelModels;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customerReturnOrders")
public class CustomerReturnOrders {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //RETURN REASON
    private String returnReason;

    //BANK DETAILS
    private String accountNumber;
    private String ifscCode;
    private String bankName;

    //DELIVERY DATE TIME PICKUP
    private String deliveryPickUpDateTime;

    //AMOUNT REFUND ID
    private String returnRefundStatus;
    private String returnRefundRequestDateTime;
    private String returnRefundProcessedDateTime;
    private String returnRefundId;

    private String deliveryStatus;
    private long orderItemId;

}
