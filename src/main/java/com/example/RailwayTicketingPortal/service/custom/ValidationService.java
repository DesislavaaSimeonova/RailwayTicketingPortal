package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.repository.ReservationRepository;
import com.example.RailwayTicketingPortal.repository.TicketRepository;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.dto.ReservationDTO;
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
    private static final String RESERVATION_NOT_EXIST = "Reservation does not exist";

    private static final String EXISTING_RESERVATION = "This ticket is already booked by the user!";

    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;

    private final ReservationRepository reservationRepository;

    public void validateUser(Long userId) throws Exception {
        if(userId == null || !userRepository.existsById(userId)) throw new Exception(USER_NOT_EXIST);
    }

    public void validateTicket(TicketDTO ticketDTO) throws Exception {
        if(ticketDTO == null && !ticketRepository.existsById(ticketDTO.getId())) throw new Exception(TICKET_NOT_EXIST);
    }
    public void validateTicketId(Long ticketId) throws Exception {
        if(!ticketRepository.existsById(ticketId)) throw new Exception(TICKET_NOT_EXIST);
    }

    public void validateReservation(ReservationDTO reservationDTO) throws Exception {
        if(reservationDTO == null && !reservationRepository.existsById(reservationDTO.getId())) throw new Exception(RESERVATION_NOT_EXIST);
    }

    public void validateReservationParameters(Long userId, Long ticketId) throws Exception{
        validateUser(userId);
        validateTicketId(ticketId);
        validateUniqueReservation(userId,ticketId);
    }

    public void validateUniqueReservation(Long userId, Long ticketId) throws Exception {
        if(reservationRepository.existsByUserIdAndTicketId(userId,ticketId)){
            throw new Exception(EXISTING_RESERVATION);
        }
    }
}
