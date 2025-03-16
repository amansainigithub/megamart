package com.coder.springjwt.models.customerPanelModels.payments.razorPay;

import com.coder.springjwt.models.entities.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaymentsTransactions extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String paymentId;
    private String signature;
    private String amount;
    private String currency;
    private String status;
    private String created_at;;
    private String attempts;
    private String paymentCreatedJson;
    private String paymentCompleteJson;

    private String userId;
    private String userName;
}
