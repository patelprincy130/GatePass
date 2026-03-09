package com.example.GatePass.models;

public class VisitorMapper {

    // Entity -> DTO
    public static VisitorDTO toDTO(VisitorEntity entity) {
        if (entity == null) return null;
        return new VisitorDTO(
                entity.getId(),
                entity.getName(),
                entity.getPhoneNumber(),
                entity.getUnitNumber(),
                entity.getPurpose(),
                entity.getEntryTime(),
                entity.getExitTime()
        );
    }

    // DTO -> Entity
    public static VisitorEntity toEntity(VisitorDTO dto) {
        if (dto == null) return null;
        return new VisitorEntity(
                dto.getId(),
                dto.getName(),
                dto.getPhoneNumber(),
                dto.getUnitNumber(),
                dto.getPurpose(),
                dto.getEntryTime(),
                dto.getExitTime()
        );
    }
}
