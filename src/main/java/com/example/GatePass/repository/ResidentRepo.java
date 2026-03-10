package com.example.GatePass.repository;

import com.example.GatePass.models.ResidentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepo extends MongoRepository<ResidentEntity,String> {
    ResidentEntity findByUnitNumber(int unit);
}
