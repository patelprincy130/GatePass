package com.example.GatePass.controllers;

import com.example.GatePass.models.VisitorEntity;
import com.example.GatePass.services.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitors")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping("/test")
    public String test(){
        return "working ok";
    }

    @GetMapping
    public ResponseEntity<List<VisitorEntity>> getAll(){
        return visitorService.getAll();
    }

    @GetMapping("/unit/{unit}")
    public ResponseEntity<VisitorEntity> getVisitor(@PathVariable int unit){
        return visitorService.getVisitor(unit);
    }

    @PostMapping
    public String addVisitor(@RequestBody VisitorEntity visitor){
        return visitorService.addVisitor(visitor);
    }

    @PutMapping
    public ResponseEntity<String> updateVisitor(@RequestBody VisitorEntity visitor){
        return visitorService.updateVisitor(visitor);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteVisitor(@PathVariable String id){
        return visitorService.deleteVisitor(id);
    }
}
