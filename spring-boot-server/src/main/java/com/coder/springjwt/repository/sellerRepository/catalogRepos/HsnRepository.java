package com.coder.springjwt.repository.sellerRepository.catalogRepos;

import com.coder.springjwt.models.sellerModels.catalog.hsn.HsnCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HsnRepository extends JpaRepository<HsnCodes,Long> {

}
