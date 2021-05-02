package com.java.momentum.dailypoints.repository;

import com.java.momentum.dailypoints.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long>{

}
