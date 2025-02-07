package com.loduone.damn.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "expert_profile")
public class ExpertProfile {
    @Id
    private String id; // Auto-generated unique ID.
    private String fullName;
    private String profilePictureUrl;
    private String location;
    private String qualifications;
    private int experience; // In years.
    private String membershipNumber;
    private List<String> specializations; // e.g., Taxation, GST.
    private List<String> languages; // e.g., English, Hindi.
    private double consultationFee; // Fee per session.
    private List<Service> servicesOffered; // Custom service list.
    private Double rating; // Average rating.
    private List<Review> reviews; // Client feedback.
    private List<TimeSlot> availability; // Available time slots.
    private Boolean isVerified; // Profile verification status.

    // Nested Classes for Services and Reviews
    @Data
    public static class Service {
        private String name;
        private double fee;
    }

    @Data
    public static class Review {
        private String clientName;
        private String comment;
        private Integer stars; // 1 to 5.
    }
}
