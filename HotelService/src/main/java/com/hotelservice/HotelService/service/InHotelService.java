package com.hotelservice.HotelService.service;

import com.hotelservice.HotelService.entity.Hotel;

import java.util.List;

public interface InHotelService {

    //Create
    Hotel create(Hotel hotel);

    //Get All
    List<Hotel> getAll();

    //Get single
    Hotel get(String id);
}
