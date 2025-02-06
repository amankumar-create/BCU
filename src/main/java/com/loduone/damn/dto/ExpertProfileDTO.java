package com.loduone.damn.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ExpertProfileDTO {
    String id; // Auto-generated unique ID.
    String fullName;
    String profilePictureUrl;
    String location;
    String qualifications;
    int experience; // In years.
    List<String> specializations; // e.g., Taxation, GST.
    List<String> languages; // e.g., English, Hindi.
    double consultationFee; // Fee per session.
    double rating; // Average rating.
    boolean isVerified; // Profile verification status.

    // Nested DTOs for Services and Reviews
    List<ServiceDTO> servicesOffered; // Custom service list.
    List<ReviewDTO> reviews; // Client feedback.

    @Data
    public static class ServiceDTO {
        private String name;
        private double fee;
    }

    @Data
    public static class ReviewDTO {
        private String clientName;
        private String comment;
        private int stars; // 1 to 5.
    }
}
