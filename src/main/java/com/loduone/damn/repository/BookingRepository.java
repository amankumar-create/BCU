package com.loduone.damn.repository;

import com.loduone.damn.enums.BookingStatus;
import com.loduone.damn.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByExpertId(String expertId );
    List<Booking> findByClientId(String clientId );
}