package com.pm.patientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getPatients() throws Exception {
        when(patientService.getPatients()).thenReturn(Collections.singletonList(PatientResponseDTO.builder().build()));

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void createPatient() throws Exception {
        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setAddress("123 Main St");
        requestDTO.setEmail("johndoe@test.com");
        requestDTO.setDateOfBirth("2000-01-01");
        requestDTO.setRegisteredDate("2023-01-01");

        when(patientService.createPatient(any(PatientRequestDTO.class))).thenReturn(PatientResponseDTO.builder().build());

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePatientById() throws Exception {
        UUID id = UUID.randomUUID();
        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setAddress("123 Main St");
        requestDTO.setEmail("johndoe@test.com");
        requestDTO.setDateOfBirth("2000-01-01");

        when(patientService.updatePatient(any(UUID.class), any(PatientRequestDTO.class))).thenReturn(PatientResponseDTO.builder().build());

        mockMvc.perform(put("/patients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePatientById() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/patients/{id}", id))
                .andExpect(status().isNoContent());
    }
}
