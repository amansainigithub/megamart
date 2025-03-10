package com.coder.springjwt.repository.paymentRepository;

import com.coder.springjwt.models.customerPanelModels.payments.razorPay.PaymentsTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentsTransactions, Long> {

    PaymentsTransactions findByOrderId(String orderId);

}
