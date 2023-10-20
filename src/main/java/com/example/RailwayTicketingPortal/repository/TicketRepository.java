package com.example.RailwayTicketingPortal.repository;

import com.example.RailwayTicketingPortal.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
