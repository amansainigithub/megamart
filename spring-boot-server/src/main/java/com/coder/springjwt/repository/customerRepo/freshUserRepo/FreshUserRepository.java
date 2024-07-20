package com.coder.springjwt.repository.customerRepo.freshUserRepo;

import com.coder.springjwt.models.customerModels.FreshUser.FreshUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreshUserRepository extends JpaRepository<FreshUser , Long> {

    FreshUser findByUsername(String username);

}
