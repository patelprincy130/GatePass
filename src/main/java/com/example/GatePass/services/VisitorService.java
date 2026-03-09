package com.example.GatePass.services;

import com.example.GatePass.models.VisitorDTO;
import com.example.GatePass.models.VisitorEntity;
import com.example.GatePass.models.VisitorMapper;
import com.example.GatePass.repository.VisitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepo visitorRepo;

    public ResponseEntity<VisitorDTO> getVisitor(int unit) {
        VisitorEntity visitor = visitorRepo.findByUnitNumber(unit);
        if(visitor!=null){
            return new ResponseEntity<>(VisitorMapper.toDTO(visitor),HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<VisitorDTO>> getAll() {
        List<VisitorEntity> allVisitors=visitorRepo.findAll();
        List<VisitorDTO> all = allVisitors.stream().map(e->VisitorMapper.toDTO(e)).collect(Collectors.toList());
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    public String addVisitor(VisitorEntity visitor) {
        visitorRepo.save(visitor);
        return "Visitor added successfully!";
    }

    public ResponseEntity<VisitorDTO> updateVisitor(VisitorEntity visitor) {
        String id=visitor.getId();
        Optional<VisitorEntity> visitorFromDBOp=visitorRepo.findById(id);
        if(visitorFromDBOp.isPresent()){
            VisitorEntity visitorFromDB=visitorFromDBOp.get();
            visitorFromDB.setUnitNumber(visitor.getUnitNumber()!=null?visitor.getUnitNumber(): visitorFromDB.getUnitNumber());
            visitorFromDB.setName(visitor.getName()!=null?visitor.getName(): visitorFromDB.getName());
            visitorFromDB.setPhoneNumber(visitor.getPhoneNumber()!=null?visitor.getPhoneNumber(): visitorFromDB.getPhoneNumber());
            visitorFromDB.setExitTime(visitor.getExitTime()!=null?visitor.getExitTime(): visitorFromDB.getExitTime());
            visitorFromDB.setPurpose(visitor.getPurpose()!=null?visitor.getPurpose(): visitorFromDB.getPurpose());
            visitorRepo.save(visitorFromDB);
            return new ResponseEntity<>(VisitorMapper.toDTO(visitorFromDB),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(VisitorMapper.toDTO(null),HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> deleteVisitor(String id) {
        if(visitorRepo.existsById(id)){
            visitorRepo.deleteById(id);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Visitor not found!",HttpStatus.NOT_FOUND);
    }
}
