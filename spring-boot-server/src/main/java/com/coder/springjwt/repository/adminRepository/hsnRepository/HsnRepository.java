package com.coder.springjwt.repository.adminRepository.hsnRepository;

import com.coder.springjwt.models.adminModels.hsn.HsnCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HsnRepository extends JpaRepository<HsnCodes,Long> {

}
