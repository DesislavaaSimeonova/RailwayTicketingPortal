package com.example.RailwayTicketingPortal.service.mapper;


import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketDTO toDTO(Ticket ticket);

    Ticket toEntity(TicketDTO ticketDTO);

}
