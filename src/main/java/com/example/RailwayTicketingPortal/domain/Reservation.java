package com.example.RailwayTicketingPortal.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
@Entity
@Builder
public class Reservation {
    @Id
    @GeneratedValue(generator = "reservation_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reservation_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column
    private Long userId;

    @Column
    private Long ticketId;

    @Column
    private String status;
}
