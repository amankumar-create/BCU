package com.loduone.damn.service.impl;

import com.loduone.damn.dto.ExpertProfileDTO;
import com.loduone.damn.model.ExpertProfile;
import com.loduone.damn.repository.ExpertProfileRepository;
import com.loduone.damn.service.ExpertProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpertProfileServiceImpl implements ExpertProfileService {
    @Autowired
    private ExpertProfileRepository expertProfileRepository;

    @Override
    public List<ExpertProfileDTO> getAllProfiles() {
        List<ExpertProfile> expertProfiles =  expertProfileRepository.findAll();
        return expertProfiles.stream()
                .map(this::biuldExpertProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExpertProfileDTO getProfileById(String id) {
        Optional<ExpertProfile> expertProfile = expertProfileRepository.findById(id);
        return expertProfile.map(this::biuldExpertProfileResponse).orElse(null);
    }

    @Override
    public List<ExpertProfileDTO> searchBySpecialization(String specialization) {
        List<ExpertProfile> expertProfiles = expertProfileRepository.findBySpecializationsContaining(specialization);
        return expertProfiles.stream()
                .map(this::biuldExpertProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpertProfileDTO> searchByLocation(String location) {
        List<ExpertProfile> expertProfiles = expertProfileRepository.findByLocation(location);
        return expertProfiles.stream()
                .map(this::biuldExpertProfileResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExpertProfileDTO addProfile(ExpertProfileDTO expertProfileDTO) {
        ExpertProfile expertProfile = buildExpertProfile(expertProfileDTO);
        return biuldExpertProfileResponse(expertProfileRepository.save(expertProfile));
    }

    @Override
    public ExpertProfileDTO updateProfile(String id, ExpertProfileDTO expertProfileDTO) {
        ExpertProfile newProfile = buildExpertProfile(expertProfileDTO);
        newProfile.setId(id);
        ExpertProfile updatedProfile = expertProfileRepository.save(newProfile);
        return biuldExpertProfileResponse(updatedProfile);
    }

    @Override
    public void deleteProfile(String id) {
        expertProfileRepository.deleteById(id);
    }

    public ExpertProfileDTO biuldExpertProfileResponse(ExpertProfile profile){
        return ExpertProfileDTO.builder()
                .id(profile.getId())
                .fullName(profile.getFullName())
                .location(profile.getLocation())
                .experience(profile.getExperience())
                .languages(profile.getLanguages())
                .profilePictureUrl(profile.getProfilePictureUrl())
                .servicesOffered(profile.getServicesOffered() != null ?
                        profile.getServicesOffered().stream()
                                .map(service -> {
                                    ExpertProfileDTO.ServiceDTO serviceDTO = new ExpertProfileDTO.ServiceDTO();
                                    serviceDTO.setName(service.getName());
                                    serviceDTO.setFee(service.getFee());
                                    return serviceDTO;
                                })
                                .collect(Collectors.toList()) :
                        Collections.emptyList())
                .consultationFee(profile.getConsultationFee())
                .qualifications(profile.getQualifications())
                .specializations(profile.getSpecializations())
                .rating(profile.getRating())
                .isVerified(profile.getIsVerified())
                .reviews(profile.getReviews().stream()
                        .map(review -> {
                            ExpertProfileDTO.ReviewDTO reviewDTO = new ExpertProfileDTO.ReviewDTO();
                            reviewDTO.setClientName(review.getClientName());
                            reviewDTO.setComment(review.getComment());
                            reviewDTO.setStars(review.getStars());
                            return reviewDTO;
                        })
                        .collect(Collectors.toList()))
                .build();

    }
    public ExpertProfile buildExpertProfile(ExpertProfileDTO expertProfileDTO){
        ExpertProfile expertProfile = new ExpertProfile();
        expertProfile.setFullName(expertProfileDTO.getFullName());
        expertProfile.setProfilePictureUrl(expertProfileDTO.getProfilePictureUrl());
        expertProfile.setExperience(expertProfileDTO.getExperience());
        expertProfile.setQualifications(expertProfileDTO.getQualifications());
        expertProfile.setLanguages(expertProfileDTO.getLanguages());
        expertProfile.setLocation(expertProfileDTO.getLocation());
        expertProfile.setSpecializations(expertProfileDTO.getSpecializations());
        expertProfile.setServicesOffered(expertProfileDTO.getServicesOffered() != null ?
                expertProfileDTO.getServicesOffered().stream()
                        .map(serviceDTO -> {
                            ExpertProfile.Service service = new ExpertProfile.Service();
                            service.setName(serviceDTO.getName());
                            service.setFee(serviceDTO.getFee());
                            return service;
                        })
                        .collect(Collectors.toList()) :
                Collections.emptyList());
        expertProfile.setConsultationFee(expertProfileDTO.getConsultationFee());
        expertProfile.setRating(expertProfileDTO.getRating());
        expertProfile.setReviews(expertProfileDTO.getReviews().stream()
                .map(reviewDTO -> {
                    ExpertProfile.Review review = new ExpertProfile.Review();
                    review.setClientName(reviewDTO.getClientName());
                    review.setComment(reviewDTO.getComment());
                    review.setStars(reviewDTO.getStars());
                    return review;
                })
                .collect(Collectors.toList()));
        expertProfile.setIsVerified(expertProfileDTO.isVerified());
        return expertProfile;
    }
}
