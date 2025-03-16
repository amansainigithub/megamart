package com.coder.springjwt.repository.sellerRepository.hotDealsRepos;

import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsEngineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealsEngineRepo extends JpaRepository<HotDealsEngineModel,Long> {
}
