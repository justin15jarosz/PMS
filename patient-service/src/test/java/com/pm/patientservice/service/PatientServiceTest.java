package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private BillingServiceGrpcClient billingServiceGrpcClient;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(UUID.randomUUID());
        patient.setName("John Doe");
        patient.setAddress("123 Main St");
        patient.setEmail("johndoe@test.com");
        patient.setDateOfBirth(LocalDate.now());
    }

    @Test
    void getPatients() {
        when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));

        assertEquals(1, patientService.getPatients().size());
    }

    @Test
    void createPatient() {
        when(patientRepository.existsByEmail(any(String.class))).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setAddress("123 Main St");
        requestDTO.setEmail("johndoe@test.com");
        requestDTO.setDateOfBirth("2000-01-01");
        requestDTO.setRegisteredDate("2023-01-01");

        PatientResponseDTO responseDTO = patientService.createPatient(requestDTO);

        assertNotNull(responseDTO);
    }

    @Test
    void createPatient_emailExists() {
        when(patientRepository.existsByEmail(any(String.class))).thenReturn(true);

        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setEmail("johndoe@test.com");

        assertThrows(EmailAlreadyExistsException.class, () -> patientService.createPatient(requestDTO));
    }

    @Test
    void updatePatient() {
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.of(patient));
        when(patientRepository.existsByEmailAndIdNot(any(String.class), any(UUID.class))).thenReturn(false);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setAddress("123 Main St");
        requestDTO.setEmail("johndoe@test.com");
        requestDTO.setDateOfBirth("2000-01-01");

        PatientResponseDTO responseDTO = patientService.updatePatient(UUID.randomUUID(), requestDTO);

        assertNotNull(responseDTO);
    }

    @Test
    void updatePatient_notFound() {
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        PatientRequestDTO requestDTO = new PatientRequestDTO();

        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(UUID.randomUUID(), requestDTO));
    }

    @Test
    void updatePatient_emailExists() {
        when(patientRepository.findById(any(UUID.class))).thenReturn(Optional.of(patient));
        when(patientRepository.existsByEmailAndIdNot(any(String.class), any(UUID.class))).thenReturn(true);

        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setEmail("johndoe@test.com");

        assertThrows(EmailAlreadyExistsException.class, () -> patientService.updatePatient(UUID.randomUUID(), requestDTO));
    }

    @Test
    void deletePatient() {
        assertDoesNotThrow(() -> patientService.deletePatient(UUID.randomUUID()));
    }
}
