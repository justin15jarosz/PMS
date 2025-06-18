package com.pm.patientservice.model;

import com.pm.patientservice.dto.PatientRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @NotNull
    String name;

    @NotNull
    @Email
    @Column(unique = true)
    String email;

    @NotNull
    String address;

    @NotNull
    LocalDate dateOfBirth;

    @NotNull
    LocalDate registeredDate;

    public static Patient toPatient(PatientRequestDTO patientRequestDTO) {
        return Patient.builder()
                .name(patientRequestDTO.getName())
                .email(patientRequestDTO.getEmail())
                .address(patientRequestDTO.getAddress())
                .dateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()))
                .registeredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()))
                .build();
    }
}
