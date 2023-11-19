package com.example.RailwayTicketingPortal.web.rest;

import com.example.RailwayTicketingPortal.domain.Reservation;
import com.example.RailwayTicketingPortal.service.ReservationService;
import com.example.RailwayTicketingPortal.service.dto.ReservationDTO;
import com.example.RailwayTicketingPortal.service.mapper.ReservationMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ReservationController {
    private final ReservationMapper reservationMapper;
    private final ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws Exception{
        ReservationDTO createdReservation = reservationService.create(reservationDTO);
        return ResponseEntity.ok().body(createdReservation);
    }

    @PutMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO,
                                                            @PathVariable Long reservationId) {
        ReservationDTO updatedReservation = reservationService.update(reservationDTO,reservationId);
        return ResponseEntity.ok().body(updatedReservation);
    }

    @GetMapping("/reservation/{reservationId}")
    public ReservationDTO getReservationById(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.getById(reservationId);
        return reservationMapper.toDTO(reservation);
    }

    @GetMapping("/reservations/{userId}")
    public List<Reservation> getAllReservationsByUserId(@PathVariable Long userId) throws Exception {
        return reservationService.getAllForAUser(userId);
    }

    @PutMapping("/cancel/reservation")
    public ResponseEntity<ReservationDTO> cancelReservation(@RequestBody ReservationDTO reservationDTO) throws Exception{
        ReservationDTO cancelledReservation = reservationService.cancel(reservationDTO);
        return ResponseEntity.ok().body(cancelledReservation);
    }
}
