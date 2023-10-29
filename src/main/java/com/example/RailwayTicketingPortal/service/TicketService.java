package com.example.RailwayTicketingPortal.service;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.repository.TicketRepository;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.custom.ValidationService;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import com.example.RailwayTicketingPortal.service.mapper.TicketMapper;

import com.example.RailwayTicketingPortal.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;


import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class TicketService {

    private final TicketMapper ticketMapper;

    private final TicketRepository ticketRepository;

    private final ValidationService validationService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

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
    public LocalTime parseStringToLocalTime(String departureTime){
        return LocalTime.parse(departureTime);
    }

    public List<TicketDTO> bookTickets(List<TicketDTO> tickets, Long userId) throws Exception{
        validationService.validateUser(userId);

        Optional<User> optionalUser = userRepository.findById(userId);
        UserDTO userDTO = new UserDTO();
        if(optionalUser.isPresent()){
            userDTO = userMapper.toDTO(optionalUser.get());
        }
        for (TicketDTO ticketDTO: tickets) {
            validationService.validateTicket(ticketDTO);

            ticketDTO.setUserId(userId);
            userDTO.getTickets().add(ticketMapper.toEntity(ticketDTO));
            userRepository.save(userMapper.toEntity(userDTO));
            ticketRepository.save(ticketMapper.toEntity(ticketDTO));
        }
        return tickets;
    }
}
