package com.loduone.damn.controller;
import com.loduone.damn.dto.ExpertProfileDTO;
import com.loduone.damn.service.ExpertProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/expert-profiles")
public class ExpertProfileController {
    @Autowired
    ExpertProfileService expertProfileService;
    
    @GetMapping
    public List<ExpertProfileDTO> getAllProfiles() {
        return expertProfileService.getAllProfiles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpertProfileDTO> getProfileById(@PathVariable String id) {
        return ResponseEntity.ok(expertProfileService.getProfileById(id));
    }

    @GetMapping("/search")
    public List<ExpertProfileDTO> searchProfiles(@RequestParam(required = false) String specialization,
                                          @RequestParam(required = false) String location) {
        if (specialization != null) {
            return expertProfileService.searchBySpecialization(specialization);
        } else if (location != null) {
            return expertProfileService.searchByLocation(location);
        } else {
            return expertProfileService.getAllProfiles();
        }
    }

    @PostMapping
    public ResponseEntity<ExpertProfileDTO> addProfile(@RequestBody ExpertProfileDTO ExpertProfileDTO) {
        return ResponseEntity.ok(expertProfileService.addProfile(ExpertProfileDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpertProfileDTO> updateProfile(@PathVariable String id, @RequestBody ExpertProfileDTO ExpertProfileDTO) {
        return ResponseEntity.ok(expertProfileService.updateProfile(id, ExpertProfileDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable String id) {
        expertProfileService.deleteProfile(id);
    }
}
