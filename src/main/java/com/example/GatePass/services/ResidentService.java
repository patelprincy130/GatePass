package com.example.GatePass.services;

import com.example.GatePass.models.ResidentDTO;
import com.example.GatePass.models.ResidentEntity;
import com.example.GatePass.models.ResidentMapper;
import com.example.GatePass.repository.ResidentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResidentService {

    @Autowired
    ResidentRepo residentRepo;

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
            residentRepo.save(ResidentMapper.toResidentEntity(residentDTO));
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
}
