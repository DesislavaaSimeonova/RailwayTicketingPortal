package com.example.RailwayTicketingPortal.service.dto;

import com.example.RailwayTicketingPortal.domain.Ticket;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Long years;

    private boolean withFamilyCard;

    List<Ticket> tickets;
}
