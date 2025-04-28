package com.coder.springjwt.repository.customerPanelRepositories.returnRepository;

import com.coder.springjwt.models.customerPanelModels.CustomerReturnOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnRepository extends JpaRepository<CustomerReturnOrders, Long> {
}
