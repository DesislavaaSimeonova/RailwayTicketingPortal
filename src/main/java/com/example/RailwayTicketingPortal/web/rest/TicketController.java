package com.example.RailwayTicketingPortal.web.rest;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.service.TicketService;
import com.example.RailwayTicketingPortal.service.custom.CalculationService;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;

    private final CalculationService calculationService;

    public TicketController(TicketService ticketService, CalculationService calculationService) {
        this.ticketService = ticketService;
        this.calculationService = calculationService;
    }

    @PostMapping("/ticket")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) throws Exception {
        TicketDTO createdTicket = ticketService.create(ticketDTO);

        return ResponseEntity.ok().body(createdTicket);
    }

    @GetMapping("/tickets")
    public ResponseEntity<Double> calculateTicket(@RequestBody List<TicketDTO> tickets) throws Exception {
        Double price = calculationService.calculatePrice(tickets);
        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicketById(@RequestParam Long ticketId) {
        return ticketService.getById(ticketId);
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
