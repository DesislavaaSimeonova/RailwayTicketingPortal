package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TicketQueryService {

    private final TicketRepository ticketRepository;

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
        else if(startDestination != null){
            return ticketRepository.findAllByStartDestination(startDestination);
        }
        else if(endDestination != null){
            return ticketRepository.findAllByEndDestination(endDestination);
        }
        else if(departureTime != null){
            return ticketRepository.findAllByDepartureTime(departureTime);
        }
        return ticketRepository.findAll();
    }
}
