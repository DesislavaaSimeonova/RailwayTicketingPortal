package com.example.RailwayTicketingPortal.repository;

import com.example.RailwayTicketingPortal.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByStartDestinationAndEndDestinationAndDepartureTime(String startDestination, String endDestination, LocalTime departureTime);

    List<Ticket> findAllByStartDestinationAndEndDestination(String startDestination, String endDestination);

    List<Ticket> findAllByStartDestinationAndDepartureTime(String startDestination, LocalTime departureTime);

    List<Ticket> findAllByEndDestinationAndDepartureTime(String endDestination, LocalTime departureTime);
}
