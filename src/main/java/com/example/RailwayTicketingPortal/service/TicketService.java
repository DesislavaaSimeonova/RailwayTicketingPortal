package com.example.RailwayTicketingPortal.service;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.repository.TicketRepository;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import com.example.RailwayTicketingPortal.service.mapper.TicketMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private TicketRepository ticketRepository;

    public TicketDTO create(TicketDTO ticketDTO) {

        if(ticketDTO == null){
            throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
        }
        Ticket ticket = ticketMapper.toEntity(ticketDTO);
        ticket = ticketRepository.save(ticket);
        if(ticket.getId() != null){
            return ticketMapper.toDTO(ticket);
        }
        throw new HttpServerErrorException(HttpStatus.EXPECTATION_FAILED);
    }

    public TicketDTO update(TicketDTO ticketDTO, Long id) {

        if(ticketDTO != null && id != null){
            Ticket currentticket =getById(id);

            Ticket ticket = ticketMapper.toEntity(ticketDTO);
            ticket.setId(currentticket.getId());

            Ticket updatedticket = ticketRepository.save(ticket);
            return ticketMapper.toDTO(updatedticket);
        }
        throw new HttpServerErrorException(HttpStatus.NOT_MODIFIED);
    }

    public Ticket getById(Long id) {
        if(id != null){
            Optional<Ticket> ticket = ticketRepository.findById(id);
            return ticket.orElseThrow(() ->  new HttpServerErrorException(HttpStatus.NO_CONTENT));
        }
        throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
    }

    public List<Ticket> getByCriteria(String startDestination, String endDestination, LocalTime departureTime) {
       if(startDestination != null && endDestination != null && departureTime !=null){
           return ticketRepository.findAllByStartDestinationAndEndDestinationAndDepartureTime(startDestination,endDestination,departureTime);
       }
       else if(startDestination != null && endDestination != null){
           return ticketRepository.findAllByStartDestinationAndEndDestination(startDestination,endDestination);
       }
       else if(startDestination != null && departureTime != null){
           return ticketRepository.findAllByStartDestinationAndDepartureTime(startDestination,departureTime);
       }
       else if(endDestination !=null && departureTime != null){
           return  ticketRepository.findAllByEndDestinationAndDepartureTime(endDestination,departureTime);
       }
       return new ArrayList<>();
    }

    public List<Ticket> getAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets;
    }

    public void delete(Long id) {
        if(id != null && ticketRepository.existsById(id)){
            ticketRepository.deleteById(id);
        }
        else throw new HttpServerErrorException(HttpStatus.EXPECTATION_FAILED);
    }
}
