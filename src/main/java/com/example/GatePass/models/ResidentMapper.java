package com.example.GatePass.models;

public class ResidentMapper {

    public static ResidentEntity toResidentEntity(ResidentDTO residentDTO){
        if(residentDTO == null) return null;
        return new ResidentEntity(residentDTO.getId(),residentDTO.getName(),residentDTO.getPhoneNumber(),residentDTO.getUnitNumber(),residentDTO.getEmail(),residentDTO.getRole(),residentDTO.getVisitors());
    }
    public static ResidentDTO toResidentDTO(ResidentEntity residentEntity){
        if(residentEntity==null) return null;
        return new ResidentDTO(residentEntity.getId(),residentEntity.getName(),residentEntity.getPhoneNumber(),residentEntity.getUnitNumber(),residentEntity.getEmail(),residentEntity.getRole(),residentEntity.getVisitors());
    }
}
