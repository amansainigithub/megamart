package com.coder.springjwt.repository.customerPanelRepositories.returnExchangeRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerReturnExchangeOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnExchangeRepository extends JpaRepository<CustomerReturnExchangeOrders, Long> {
}
