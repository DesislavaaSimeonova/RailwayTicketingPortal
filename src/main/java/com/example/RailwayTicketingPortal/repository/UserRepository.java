package com.example.RailwayTicketingPortal.repository;

import com.example.RailwayTicketingPortal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
