package com.example.GatePass.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitorDTO {
    private String id;
    private String name;
    private Long phoneNumber;
    private Integer unitNumber;
    private String purpose;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
}
