package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.custom.CalculationService;
import com.example.RailwayTicketingPortal.service.custom.ValidationService;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import com.example.RailwayTicketingPortal.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CalculationServiceTest {

    private static final String VIENNA = "Vienna";
    private static final String MUNICH = "Munich";
    private static final String UNDER_16_YEARS = "under 16 years";
    private static final String STANDARD = "Standard";
    private static final LocalTime DEPARTURE_TIME = LocalTime.parse("08:20:00");
    private static final Long USER_ID = 1L;

    @InjectMocks
    private CalculationService calculationService;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void init(){
        this.calculationService = new CalculationService(this.userRepository, this.validationService,this.userMapper);
    }

    private TicketDTO createTicketPrice(){
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setType(STANDARD);
        ticketDTO.setStartDestination(VIENNA);
        ticketDTO.setEndDestination(MUNICH);
        ticketDTO.setDepartureTime(DEPARTURE_TIME);
        ticketDTO.setUserId(USER_ID);
        return ticketDTO;
    }

    @Test
    void whenCalculatePriceBasedOnDistance_FullPriceInRushHour_shouldSucceed() throws Exception {
        // given
        TicketDTO ticketFullPrice = createTicketPrice();
        BigDecimal expectedFullPrice = BigDecimal.valueOf(75.00);

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String formattedPrice = decimalFormat.format(expectedFullPrice);

        // when
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(new UserDTO());
        BigDecimal calculatedPrice = calculationService.calculatePrice(List.of(ticketFullPrice));

        Mockito.verify(validationService).validateUser(USER_ID);
        Mockito.verify(userRepository).findById(USER_ID);
        Mockito.verify(userMapper).toDTO(Mockito.any(User.class));

        // then
        assertEquals(new BigDecimal(formattedPrice), calculatedPrice);

    }

    @Test
    void whenCalculatePriceBasedOnDistance_PriceInNonRushHour_shouldSucceed() throws Exception {
        //given
        TicketDTO ticketDTO = createTicketPrice();
        ticketDTO.setDepartureTime(LocalTime.of(10,10));
        BigDecimal expectedPriceWithDiscount = BigDecimal.valueOf(71.25);

        //when
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(new UserDTO());
        BigDecimal calculatedPrice = calculationService.calculatePrice(List.of(ticketDTO));

        Mockito.verify(validationService).validateUser(USER_ID);
        Mockito.verify(userRepository).findById(USER_ID);
        Mockito.verify(userMapper).toDTO(Mockito.any(User.class));

        //then
        assertEquals(expectedPriceWithDiscount, calculatedPrice);
    }

    @Test
    void whenCalculatePriceBasedOnDistance_PriceInNonRushHourAndWithUnder16_shouldSucceed() throws Exception {
        //given
        TicketDTO ticketDTO = createTicketPrice();
        ticketDTO.setDepartureTime(LocalTime.of(10,10));
        BigDecimal expectedPriceWithDiscount = BigDecimal.valueOf(64.12);

        UserDTO userDTO = new UserDTO();
        userDTO.setWithChildUnder16(Boolean.TRUE);

        //when
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(userDTO);
        BigDecimal calculatedPrice = calculationService.calculatePrice(List.of(ticketDTO));

        Mockito.verify(validationService).validateUser(USER_ID);
        Mockito.verify(userRepository).findById(USER_ID);
        Mockito.verify(userMapper).toDTO(Mockito.any(User.class));

        //then
        assertEquals(expectedPriceWithDiscount, calculatedPrice);
    }

    @Test
    void whenCalculatePriceBasedOnDistance_PriceInNonRushHourAndUserOver60s_shouldSucceed() throws Exception {
        //given
        TicketDTO ticketDTO = createTicketPrice();
        ticketDTO.setDepartureTime(LocalTime.of(10,10));
        BigDecimal expectedPriceWithDiscount = BigDecimal.valueOf(47.02);

        UserDTO userDTO = new UserDTO();
        userDTO.setWithOver60sRailwayCard(Boolean.TRUE);

        //when
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(userDTO);
        BigDecimal calculatedPrice = calculationService.calculatePrice(List.of(ticketDTO));

        Mockito.verify(validationService).validateUser(USER_ID);
        Mockito.verify(userRepository).findById(USER_ID);
        Mockito.verify(userMapper).toDTO(Mockito.any(User.class));

        //then
        assertEquals(expectedPriceWithDiscount, calculatedPrice);
    }

    @Test
    void whenCalculatePriceBasedOnDistance_PriceInNonRushHourAndUserOnlyWithFamilyCard_shouldSucceed() throws Exception {
        //given
        TicketDTO ticketDTO = createTicketPrice();
        ticketDTO.setDepartureTime(LocalTime.of(10,10));
        BigDecimal expectedPriceWithDiscount = BigDecimal.valueOf(71.25);

        UserDTO userDTO = new UserDTO();
        userDTO.setWithFamilyCard(Boolean.TRUE);

        //when
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(userDTO);
        BigDecimal calculatedPrice = calculationService.calculatePrice(List.of(ticketDTO));

        Mockito.verify(validationService).validateUser(USER_ID);
        Mockito.verify(userRepository).findById(USER_ID);
        Mockito.verify(userMapper).toDTO(Mockito.any(User.class));

        //then
        assertEquals(expectedPriceWithDiscount, calculatedPrice);
    }
    @Test
    void whenCalculatePriceBasedOnDistance_PriceInNonRushHourAndUserUnder16AndWithFamilyCard_shouldSucceed() throws Exception {
        //given
        TicketDTO ticketDTOChild = createTicketPrice();
        ticketDTOChild.setDepartureTime(LocalTime.of(10,10));

        TicketDTO ticketDTOParent = createTicketPrice();
        ticketDTOParent.setDepartureTime(LocalTime.of(10,10));

        BigDecimal expectedPriceWithDiscount = BigDecimal.valueOf(71.24);

        UserDTO userDTO = new UserDTO();
        userDTO.setWithFamilyCard(Boolean.TRUE);
        userDTO.setWithChildUnder16(Boolean.TRUE);

        //when
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(new User()));
        Mockito.when(userMapper.toDTO(Mockito.any(User.class))).thenReturn(userDTO);
        BigDecimal calculatedPrice = calculationService.calculatePrice(List.of(ticketDTOChild,ticketDTOParent));

        Mockito.verify(validationService, Mockito.times(2)).validateUser(USER_ID);
        Mockito.verify(userRepository, Mockito.times(2)).findById(USER_ID);
        Mockito.verify(userMapper, Mockito.times(2)).toDTO(Mockito.any(User.class));

        //then
        assertEquals(expectedPriceWithDiscount, calculatedPrice);
    }

}
