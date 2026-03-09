package com.example.GatePass.repository;

import com.example.GatePass.models.VisitorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepo extends MongoRepository<VisitorEntity,String> {

    VisitorEntity findByUnitNumber(int unit);
}