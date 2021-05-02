package com.java.momentum.dailypoints.repository;

import com.java.momentum.dailypoints.model.MomentumProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MomentumProductRepository extends MongoRepository<MomentumProduct, Long>{

}
