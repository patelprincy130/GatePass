package com.example.GatePass.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResidentDTO {
    private String id;
    private String name;
    private Long phoneNumber;
    private Integer unitNumber;
    private String email;
    private String role;
    private List<VisitorEntity> visitors;
}
