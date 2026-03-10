package com.example.GatePass.controllers;

import com.example.GatePass.models.ResidentDTO;
import com.example.GatePass.models.ResidentEntity;
import com.example.GatePass.services.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    ResidentService residentService;

    @GetMapping
    public ResponseEntity<List<ResidentDTO>> getResidents(){
        return residentService.getAll();
    }

    @PostMapping
    public ResponseEntity<ResidentDTO> addResident(@RequestBody ResidentDTO residentDTO){
        return residentService.addResident(residentDTO);
    }

    @GetMapping("unit/{unit}")
    public ResponseEntity<ResidentDTO> getResidentByUnit(@PathVariable int unit){
        return residentService.getResidentByUnit(unit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResidentDTO> updateResident(@RequestBody ResidentDTO residentDTO, @PathVariable String id){
        return residentService.updateResident(residentDTO,id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteResident(@PathVariable String id){
        return residentService.deleteResident(id);
    }


}
