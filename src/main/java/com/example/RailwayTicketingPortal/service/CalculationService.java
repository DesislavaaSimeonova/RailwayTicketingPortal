package com.example.RailwayTicketingPortal.service;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.domain.User;

public class CalculationService {

    //take train before 9.30am & 4-7:30pm => rushHour = full price
    boolean rushHour;

    //9:30-4am & after 7:30pm => 5% discount

    //'over 60s rail card' => 34% discount
    //with child under 16 years old and 'family card'=> 50% discount on every ticket
    //with child under 16 years old => 10% discount on every ticket


    //ONLY ONE TYPE OF CARD

    private void calculatePrice(User user, Ticket ticket){

    }
}
