package com.zod.airbnbApp.service;


import com.zod.airbnbApp.dto.RoomDto;
import com.zod.airbnbApp.entity.Hotel;
import com.zod.airbnbApp.entity.Room;
import com.zod.airbnbApp.exception.ResourceNotFoundException;
import com.zod.airbnbApp.repository.HotelRepository;
import com.zod.airbnbApp.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final HotelRepository hotelRepository;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating new room in hotel with id: {}" ,hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room = roomRepository.save(room);

        // TODO: CREATE INVENTORY FOR ROOM AS HOTEL IS ACTIVE
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting rooms in hotel with id: {}" ,hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting rooms  with id: {}" ,roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + roomId));
        return modelMapper.map(room , RoomDto.class);
    }

    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting room  with id: {}" ,roomId);
        boolean exists = roomRepository.existsById(roomId);
        if (!exists){
            throw new ResourceNotFoundException("Hotel not found with id: " + roomId);
        }
        roomRepository.deleteById(roomId);
    }
}
