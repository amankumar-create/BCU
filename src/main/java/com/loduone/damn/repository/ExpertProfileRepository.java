package com.loduone.damn.repository;

import com.loduone.damn.model.ExpertProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertProfileRepository extends MongoRepository<ExpertProfile, String> {
    List<ExpertProfile> findBySpecializationsContaining(String specialization); // Search by expertise.
    List<ExpertProfile> findByLocation(String location); // Search by location.
    List<ExpertProfile> findByRatingGreaterThanEqual(double rating); // Filter by ratings.
}
