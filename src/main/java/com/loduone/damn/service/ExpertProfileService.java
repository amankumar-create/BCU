package com.loduone.damn.service;

import com.loduone.damn.dto.ExpertProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpertProfileService {
    List<ExpertProfileDTO> getAllProfiles();
    ExpertProfileDTO getProfileById(String id);
    List<ExpertProfileDTO> searchBySpecialization(String specialization);
    List<ExpertProfileDTO> searchByLocation(String location);
    ExpertProfileDTO addProfile(ExpertProfileDTO caProfile);
    ExpertProfileDTO updateProfile(String id, ExpertProfileDTO caProfile);
    void deleteProfile(String id);
}
