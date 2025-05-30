package com.coder.springjwt.repository;

import com.coder.springjwt.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByUsernameAndCustomerRegisterComplete(String username, String rcFlag);
	Boolean existsByUsername(String username);
	Boolean existsByCustomerEmail(String email);
	Page<User> findByProjectRole(String projectRole , Pageable pageable);

	Optional<User> findByEmailVerificationToken(String token);
}
