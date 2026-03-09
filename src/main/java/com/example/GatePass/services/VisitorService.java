package com.example.GatePass.services;

import com.example.GatePass.models.VisitorEntity;
import com.example.GatePass.repository.VisitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepo visitorRepo;

    public ResponseEntity<VisitorEntity> getVisitor(int unit) {
        VisitorEntity visitor = visitorRepo.findByUnitNumber(unit);
        if(visitor!=null){
            return new ResponseEntity<>(visitor,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<VisitorEntity>> getAll() {
        List<VisitorEntity> allVisitors=visitorRepo.findAll();
        return new ResponseEntity<>(allVisitors, HttpStatus.OK);
    }

    public String addVisitor(VisitorEntity visitor) {
        visitorRepo.save(visitor);
        return "Visitor added successfully!";
    }

    public ResponseEntity<String> updateVisitor(VisitorEntity visitor) {
        String id=visitor.getId();
        VisitorEntity visitorFromDB=visitorRepo.findById(id).get();
        if(visitorFromDB!=null){
            visitorFromDB.setUnitNumber(visitor.getUnitNumber()!=null?visitor.getUnitNumber():visitorFromDB.getUnitNumber());
            visitorFromDB.setName(visitor.getName()!=null?visitor.getName():visitorFromDB.getName());
            visitorFromDB.setPhoneNumber(visitor.getPhoneNumber()!=null?visitor.getPhoneNumber():visitorFromDB.getPhoneNumber());
            visitorFromDB.setExitTime(visitor.getExitTime()!=null?visitor.getExitTime():visitorFromDB.getExitTime());
            visitorFromDB.setPurpose(visitor.getPurpose()!=null?visitor.getPurpose():visitorFromDB.getPurpose());
            visitorRepo.save(visitorFromDB);
            return new ResponseEntity<>("Updated successfully",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Cant find visitor",HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteVisitor(String id) {
        if(visitorRepo.existsById(id)){
            visitorRepo.deleteById(id);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Visitor not found!",HttpStatus.NOT_FOUND);
    }
}
