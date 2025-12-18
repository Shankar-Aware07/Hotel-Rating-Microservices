package com.hotelrating.HotelRatingMicroservice.services;

import com.hotelrating.HotelRatingMicroservice.entities.Rating;
import com.hotelrating.HotelRatingMicroservice.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ImplRatingService implements InRatingService{

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public Rating create(Rating rating) {
        String id = UUID.randomUUID().toString();
        rating.setRatingId(id);
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getAllByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getAllByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
