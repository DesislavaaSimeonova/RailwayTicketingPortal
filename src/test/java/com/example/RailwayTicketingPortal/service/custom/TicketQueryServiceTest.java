package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.domain.Ticket;
import com.example.RailwayTicketingPortal.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class TicketQueryServiceTest {

    private static final String VIENNA = "Vienna";
    private static final String MUNICH = "Munich";
    private static final String STANDARD = "Standard";
    private static final LocalTime DEPARTURE_TIME = LocalTime.parse("08:20:00");
    private static final Long USER_ID = 1L;

    @InjectMocks
    private TicketQueryService ticketQueryService;

    @Mock
    private TicketRepository ticketRepository;

    @BeforeEach
    void init(){
        this.ticketQueryService = new TicketQueryService(this.ticketRepository);
    }

    private Ticket createTicket(){
        Ticket ticket = new Ticket();
        ticket.setType(STANDARD);
        ticket.setStartDestination(VIENNA);
        ticket.setEndDestination(MUNICH);
        ticket.setDepartureTime(DEPARTURE_TIME);
        ticket.setUserId(USER_ID);
        return ticket;
    }

    @Test
    void getTicketsByCriteria_StartEndDestinationDepartureTime_shouldSucceed(){
        //given
        String startDestination = VIENNA;
        String endDestination = MUNICH;
        LocalTime departureTime = DEPARTURE_TIME;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository
                .findAllByStartDestinationAndEndDestinationAndDepartureTime(startDestination, endDestination, departureTime))
                .thenReturn(List.of(ticket));


        List<Ticket> expected = ticketQueryService.getByCriteria(startDestination, endDestination, departureTime);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_StartEndDestination_shouldSucceed(){
        //given
        String startDestination = VIENNA;
        String endDestination = MUNICH;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository
                        .findAllByStartDestinationAndEndDestination(startDestination, endDestination))
                .thenReturn(List.of(ticket));


        List<Ticket> expected = ticketQueryService.getByCriteria(startDestination, endDestination,null);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_StartDestinationDepartureTime_shouldSucceed(){
        //given
        String startDestination = VIENNA;
        LocalTime departureTime = DEPARTURE_TIME;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository
                        .findAllByStartDestinationAndDepartureTime(startDestination, departureTime))
                .thenReturn(List.of(ticket));

        List<Ticket> expected = ticketQueryService.getByCriteria(startDestination, null, departureTime);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_EndDestinationDepartureTime_shouldSucceed(){
        //given
        String endDestination = MUNICH;
        LocalTime departureTime = DEPARTURE_TIME;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository
                        .findAllByEndDestinationAndDepartureTime(endDestination, departureTime))
                .thenReturn(List.of(ticket));


        List<Ticket> expected = ticketQueryService.getByCriteria(null, endDestination, departureTime);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_StartDestination_shouldSucceed(){
        //given
        String startDestination = VIENNA;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository.findAllByStartDestination(startDestination)).thenReturn(List.of(ticket));
        List<Ticket> expected = ticketQueryService.getByCriteria(startDestination, null, null);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_EndDestination_shouldSucceed(){
        //given
        String endDestination = MUNICH;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository.findAllByEndDestination(endDestination)).thenReturn(List.of(ticket));

        List<Ticket> expected = ticketQueryService.getByCriteria(null,endDestination,null);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_DepartureTime_shouldSucceed(){
        //given
        LocalTime departureTime = DEPARTURE_TIME;
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository.findAllByDepartureTime(departureTime)).thenReturn(List.of(ticket));
        List<Ticket> expected = ticketQueryService.getByCriteria(null, null, departureTime);

        //then
        Assertions.assertEquals(actual,expected);
    }

    @Test
    void getTicketsByCriteria_NoFiltersApplied_shouldSucceed(){
        //given
        Ticket ticket = createTicket();
        List<Ticket> actual = new ArrayList<>();
        actual.add(ticket);

        //when
        Mockito.when(ticketRepository.findAll()).thenReturn(List.of(ticket));


        List<Ticket> expected = ticketQueryService.getByCriteria(null, null, null);

        //then
        Assertions.assertEquals(actual,expected);
    }

}
