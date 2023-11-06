package com.example.RailwayTicketingPortal.web.rest;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.service.TicketService;
import com.example.RailwayTicketingPortal.service.custom.CalculationService;
import com.example.RailwayTicketingPortal.service.custom.TicketQueryService;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TicketController {

    private final TicketService ticketService;

    private final TicketQueryService ticketQueryService;

    private final CalculationService calculationService;

    @PostMapping("/ticket")
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) {
        TicketDTO createdTicket = ticketService.create(ticketDTO);
        return ResponseEntity.ok().body(createdTicket);
    }

    @GetMapping("/tickets/price")
    public ResponseEntity<BigDecimal> calculateTicket(@RequestBody List<TicketDTO> tickets) throws Exception {
        BigDecimal price = calculationService.calculatePrice(tickets);
        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/ticket/{ticketId}")
    public Ticket getTicketById(@RequestParam Long ticketId) {
        return ticketService.getById(ticketId);
    }

    @GetMapping("/ticket/search")
    public List<Ticket> getTicketById(@RequestParam(required = false) String startDestination,
                                      @RequestParam(required = false) String endDestination,
                                      @RequestParam(required = false) String departureTime) {
        LocalTime convertedTime = ticketService.parseStringToLocalTime(departureTime);
        return ticketQueryService.getByCriteria(startDestination, endDestination, convertedTime);
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

    @PutMapping("/book/tickets/{userId}")
    public List<TicketDTO>  bookTicket(@RequestBody List<TicketDTO> tickets,
                                       @PathVariable Long userId) throws Exception{
        return ticketService.bookTickets(tickets,userId);
    }

}
