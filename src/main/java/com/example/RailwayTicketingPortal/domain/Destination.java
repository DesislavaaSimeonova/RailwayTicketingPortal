package com.example.RailwayTicketingPortal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "destination")
@Entity
@Builder
public class Destination {

    @Id
    @GeneratedValue(generator = "destination_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "destination_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @ManyToMany
    private Set<Ticket> tickets; //target
}
