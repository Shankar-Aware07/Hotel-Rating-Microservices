package com.hotelrating.HotelRatingMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelRatingMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRatingMicroserviceApplication.class, args);
	}

}
