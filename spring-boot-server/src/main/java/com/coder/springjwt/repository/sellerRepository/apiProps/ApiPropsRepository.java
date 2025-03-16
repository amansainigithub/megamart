package com.coder.springjwt.repository.sellerRepository.apiProps;

import com.coder.springjwt.models.sellerModels.props.Api_Props;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiPropsRepository extends JpaRepository<Api_Props , Long> {

    Api_Props findByProvider(String provider);
}
