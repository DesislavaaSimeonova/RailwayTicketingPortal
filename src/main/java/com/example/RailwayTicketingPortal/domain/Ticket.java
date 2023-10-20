package com.example.RailwayTicketingPortal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket")
@Entity
@Builder
public class Ticket {
    @Id
    @GeneratedValue(generator = "ticket_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ticket_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column
    private String type;

    @Column
    private String startDestination;

    @Column
    private String endDestination;

    @Column
    private LocalDateTime departureTime;

    private Long userId;

}
