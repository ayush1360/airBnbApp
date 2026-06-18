package com.zod.airbnbApp.service;

import com.zod.airbnbApp.dto.HotelDto;

public interface HotelService {

     HotelDto createNewHotel(HotelDto hotelDto);

     HotelDto getHotelById(Long hotelId);

     HotelDto updateHotel(Long hotelId, HotelDto hotelDto);

     void deleteHotel(Long hotelId);

     void activateHotel(Long hotelId);

}
