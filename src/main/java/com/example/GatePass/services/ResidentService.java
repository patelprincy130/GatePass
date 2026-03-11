package com.example.GatePass.services;

import com.example.GatePass.models.*;
import com.example.GatePass.repository.ResidentRepo;
import com.example.GatePass.repository.VisitorRepo;
import com.example.GatePass.wrapperClasses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidentService {

    @Autowired
    ResidentRepo residentRepo;

    @Autowired
    VisitorRepo visitorRepo;

    public ResponseEntity<List<ResidentDTO>> getAll() {
        List<ResidentEntity> entity=residentRepo.findAll();
        List<ResidentDTO> residentDTOS=entity.stream().map(e->ResidentMapper.toResidentDTO(e)).collect(Collectors.toList());
        return new ResponseEntity<>(residentDTOS, HttpStatus.OK);
    }

    public ResponseEntity<ResidentDTO> addResident(ResidentDTO residentDTO) {
        ResidentEntity entity=residentRepo.save(ResidentMapper.toResidentEntity(residentDTO));
        return new ResponseEntity<>(ResidentMapper.toResidentDTO(entity),HttpStatus.CREATED);
    }

    public ResponseEntity<ResidentDTO> getResidentByUnit(int unit) {
        //List<ResidentEntity> entities=residentRepo.findAll().stream().filter(e->e.getUnitNumber().equals(unit)).collect(Collectors.toList());
        ResidentEntity entity = residentRepo.findByUnitNumber(unit);
        return new ResponseEntity<>(ResidentMapper.toResidentDTO(entity),HttpStatus.OK);
    }

    public ResponseEntity<ResidentDTO> updateResident(ResidentDTO residentDTO,String id) {
        Optional<ResidentEntity> entity=residentRepo.findById(id);
        if(entity.isPresent()){
            ResidentEntity residentEntity=entity.get();
            residentEntity.setName(residentDTO.getName());
            residentEntity.setPhoneNumber(residentDTO.getPhoneNumber());
            residentEntity.setUnitNumber(residentDTO.getUnitNumber());
            residentEntity.setEmail(residentDTO.getEmail());
            residentEntity.setVisitors(residentDTO.getVisitors());
            residentRepo.save(residentEntity);
            return new ResponseEntity<>(ResidentMapper.toResidentDTO(residentEntity),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteResident(String id) {
        Optional<ResidentEntity> entity=residentRepo.findById(id);
        if(entity.isPresent()){
            residentRepo.deleteById(id);
            return new ResponseEntity<>("Deleted resident",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("Resident ID Invalid",HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<String> addVisitor(int unit, VisitorDTO visitor) {
        ResidentEntity residentEntity=residentRepo.findByUnitNumber(unit);
        if(residentEntity==null){
            return new ResponseEntity<>("Resident not found",HttpStatus.NOT_FOUND);
        }
        VisitorEntity visitorEntity=visitorRepo.save(VisitorMapper.toEntity(visitor));
        residentEntity.getVisitors().add(visitorEntity);
        residentRepo.save(residentEntity);
        return new ResponseEntity<>("Visitor added successfully under unit :"+unit, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<ApiResponse<VisitorDTO>> updateVisitor(int unit, VisitorDTO visitor) {
        ResidentEntity residentEntity=residentRepo.findByUnitNumber(unit);
        if(residentEntity==null){
            ApiResponse apiResponse=new ApiResponse<>("Resident not found",null);
            return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
        }
        List<VisitorEntity> visitorEntities=residentEntity.getVisitors();
        VisitorEntity visitorEntity=visitorEntities.stream().filter(e->e.getId().equals(visitor.getId())).findFirst().orElse(null);
        if(visitorEntity!=null){
            visitorEntity.setPurpose(visitor.getPurpose());
            visitorEntity.setName(visitor.getName());
            visitorEntity.setPhoneNumber(visitor.getPhoneNumber());
            visitorEntity.setUnitNumber(visitor.getUnitNumber());
            visitorEntity.setExitTime(visitor.getExitTime());
            visitorEntity.setEntryTime(visitor.getEntryTime());
            visitorRepo.save(visitorEntity);
//            residentEntity.getVisitors().add(visitorEntity);  -->
            residentRepo.save(residentEntity);
            ApiResponse apiResponse=new ApiResponse<>("Visitor updated for resident of unit: "+unit,VisitorMapper.toDTO(visitorEntity));
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        ApiResponse apiResponse=new ApiResponse<>("Visitor not found under resident of unit: "+unit,null);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

    @Transactional
    public ResponseEntity<ApiResponse<VisitorDTO>> deleteVisitor(int unit,String id) {
        ResidentEntity residentEntity=residentRepo.findByUnitNumber(unit);
        if(residentEntity==null){
            return new ResponseEntity<>(new ApiResponse("Resident not found for unit: "+unit,null),HttpStatus.NOT_FOUND);
        }
        List<VisitorEntity> visitorEntities=residentEntity.getVisitors();
        VisitorEntity visitorEntity=visitorEntities.stream().filter(e->e.getId().equals(id)).findFirst().orElse(null);
        if(visitorEntity!=null){
            visitorRepo.deleteById(visitorEntity.getId());
            residentEntity.getVisitors().removeIf(e->e.getId().equals(id));
            residentRepo.save(residentEntity);
            ApiResponse apiResponse=new ApiResponse("Visitor deleted for unit: "+unit,VisitorMapper.toDTO(visitorEntity));
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        ApiResponse apiResponse=new ApiResponse("Visitor not found for unit: "+unit,null);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
