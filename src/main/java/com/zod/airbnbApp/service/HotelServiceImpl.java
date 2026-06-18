package com.zod.airbnbApp.service;

import com.zod.airbnbApp.dto.HotelDto;
import com.zod.airbnbApp.entity.Hotel;
import com.zod.airbnbApp.exception.ResourceNotFoundException;
import com.zod.airbnbApp.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel with name: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Created new hotel with ID: {}", hotelDto.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long hotelId) {
        log.info("Fetch hotel with id: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotel(Long hotelId, HotelDto hotelDto) {
        log.info("Updating hotel with id: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(hotelId); // Ensure the ID remains the same

        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public void deleteHotel(Long hotelId) {
        log.info("Deleting hotel with id: {}", hotelId);
        boolean exists = hotelRepository.existsById(hotelId);
        if (!exists) {
            throw new ResourceNotFoundException("Hotel not found with id: " + hotelId);
        }
         hotelRepository.deleteById(hotelId);
    }

    @Override
    public void activateHotel(Long hotelId) {
        log.info("Activating hotel with id: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        hotel.setActive(true);
        hotelRepository.save(hotel);
    }
}
