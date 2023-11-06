package com.example.RailwayTicketingPortal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(generator = "user_sequence_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_sequence_generator", initialValue = 1000, allocationSize = 1)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Long years;

    @OneToMany(mappedBy = "userId")
    List<Ticket> tickets;

    @Column(name = "with_family_card")
    private boolean withFamilyCard;

    @Column(name = "with_over_60s_railway_card")
    private boolean withOver60sRailwayCard;

    @Column(name = "with_child_under_16_years")
    private boolean withChildUnder16Years;

}
