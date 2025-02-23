package com.coder.springjwt.repository.hotDealsRepos;

import com.coder.springjwt.models.sellerModels.hotDealsEngine.HotDealsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotDealsRepo  extends JpaRepository<HotDealsModel,Long> {
}
