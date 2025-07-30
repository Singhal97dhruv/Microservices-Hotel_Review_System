package com.lcwd.hotel.Hotel.Service.services;

import com.lcwd.hotel.Hotel.Service.entities.Hotel;

import java.util.List;

public interface HotelService {

    public Hotel create(Hotel hotel);

    public List<Hotel> getAll();

    public Hotel get(String id);

}
