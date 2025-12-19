package com.user.UserService.services;

import com.user.UserService.entity.Hotel;
import com.user.UserService.entity.Rating;
import com.user.UserService.entity.User;
import com.user.UserService.exceptions.ResourceNotFoundException;
import com.user.UserService.external.service.HotelService;
import com.user.UserService.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImplUserService implements InUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HotelService hotelService;

    //private Logger logger = LoggerFactory.getLogger(ImplUserService.class);


    @Override
    public User save(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server !!"));

        //Fetch rating of above user from rating service
        //http://localhost:8083/ratings/users/01a21cb7-bcd6-4f0b-9a7e-6b96163c869d
        Rating[] ratingsOfUser = restTemplate.getForObject("http://HOTELRATINGMICROSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        log.info("{} ", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {

           //Api call to hotel service to get hotel
            //localhost:8082/hotels/e81e8401-a544-4194-bc67-d3cef7c14a64
            //ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);


            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            //log.info("Response status code : {} ", forEntity.getStatusCode());

            //Set the hotel to rating
            rating.setHotel(hotel);

            // return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }
}
