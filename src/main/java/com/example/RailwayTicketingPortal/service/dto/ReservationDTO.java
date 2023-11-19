package com.example.RailwayTicketingPortal.service.dto;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long userId;
    private Long ticketId;
    private String status;
}
