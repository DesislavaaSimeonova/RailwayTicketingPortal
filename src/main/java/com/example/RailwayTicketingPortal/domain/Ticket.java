package com.example.RailwayTicketingPortal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

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
    @ManyToMany
    @JoinTable(
            name = "ticket_destination",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "start_destination)id"))
    private Set<Destination> destinations; //owner

    @Column
    private LocalDateTime departureTime;

    private Long userId;

}
