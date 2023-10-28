package com.example.RailwayTicketingPortal.web.rest;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.service.TicketService;
import com.example.RailwayTicketingPortal.service.custom.CalculationService;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CalculationService calculationService;

    @PostMapping("/ticket")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO){
        TicketDTO createdTicket = ticketService.create(ticketDTO);

        return ResponseEntity.ok().body(createdTicket);
    }

    @GetMapping("/tickets/price")
    public ResponseEntity<Double> calculateTicket(@RequestBody List<TicketDTO> tickets) throws Exception {
        Double price = calculationService.calculatePrice(tickets);
        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicketById(@RequestParam Long ticketId) {
        return ticketService.getById(ticketId);
    }

    @GetMapping("/ticket/search")
    public List<Ticket> getTicketById(@RequestParam(required = false) String startDestination,
                                      @RequestParam(required = false)String endDestination,
                                      @RequestParam(required = false) LocalTime departureTime) {
        return ticketService.getByCriteria(startDestination,endDestination,departureTime);
    }

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets() {
        return ticketService.getAll();
    }

    @DeleteMapping("/ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@RequestParam Long ticketId) {
        ticketService.delete(ticketId);
        return ResponseEntity.noContent().build();
    }

}
