package com.hotelrating.HotelRatingMicroservice.services;
import com.hotelrating.HotelRatingMicroservice.entities.Rating;
import java.util.List;

public interface InRatingService {

    //Create
    Rating create(Rating rating);

    //Get all
    List<Rating> getAll();

    //Get all by User Id
    List<Rating> getAllByUserId(String userId);

    //Get all by Hotel Id
    List<Rating> getAllByHotelId(String hotelId);
}
