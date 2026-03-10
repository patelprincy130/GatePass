package com.example.GatePass.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "residentEntity")
public class ResidentEntity {
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private Long phoneNumber;
    @NonNull
    private Integer unitNumber;
    @NonNull
    private String email;
    @NonNull
    private String role;
    @DBRef
    private List<VisitorEntity> visitors;
}
