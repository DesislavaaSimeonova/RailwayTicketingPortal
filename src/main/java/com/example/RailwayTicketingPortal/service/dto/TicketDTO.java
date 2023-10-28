package com.example.RailwayTicketingPortal.service.dto;

import lombok.*;

import java.time.LocalTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Long id;

    private String type; //bookTicket

    private String startDestination;

    private String endDestination;

    private LocalTime departureTime;

    private Long userId;
}
