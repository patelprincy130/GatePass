package com.example.GatePass.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "visitorDB")
public class VisitorEntity {

    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private Long phoneNumber;
    @NonNull
    private Integer unitNumber;
    @NonNull
    private String purpose;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

}
