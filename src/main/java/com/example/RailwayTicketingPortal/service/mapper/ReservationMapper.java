package com.example.RailwayTicketingPortal.service.mapper;

import com.example.RailwayTicketingPortal.domain.Reservation;
import com.example.RailwayTicketingPortal.service.dto.ReservationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO toDTO(Reservation reservation);

    Reservation toEntity(ReservationDTO reservationDTO);
}
