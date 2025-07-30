package com.lcwd.hotel.Hotel.Service.repositories;

import com.lcwd.hotel.Hotel.Service.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,String> {
}
