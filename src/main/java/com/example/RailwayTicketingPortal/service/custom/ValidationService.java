package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.repository.TicketRepository;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ValidationService {

    private static final String USER_NOT_EXIST = "User does not exist";

    private static final String TICKET_NOT_EXIST = "Ticket does not exist";

    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    public void validateUser(Long userId) throws Exception {
        if(userId == null || !userRepository.existsById(userId)) throw new Exception(USER_NOT_EXIST);
    }

    public void validateTicket(TicketDTO ticketDTO) throws Exception {
        if(ticketDTO == null && !ticketRepository.existsById(ticketDTO.getId())) throw new Exception(TICKET_NOT_EXIST);
    }
}
