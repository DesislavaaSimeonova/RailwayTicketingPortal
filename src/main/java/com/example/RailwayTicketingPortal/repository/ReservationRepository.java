package com.example.RailwayTicketingPortal.repository;

import com.example.RailwayTicketingPortal.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByUserId(Long userId);

    boolean existsByUserIdAndTicketId(Long userId, Long ticketId);
}
