package com.user.UserService.controllers;

import com.user.UserService.entity.User;
import com.user.UserService.services.ImplUserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ImplUserService userService;

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }


    int retryCount = 1;
    //Get single user
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
      @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        log.info("Get single user handler : UserController");
        log.info("Retry count {} ",retryCount);
        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }



    //Fallback method
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
//        log.info("Fallback method is executed because service down ", ex.getMessage());
        User user = User.builder()
                .email("dummy00@gmail.com")
                .name("dummy")
                .about("This is dummy user for circuit breaker")
                .userId("38470")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //Get all user
    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
}
