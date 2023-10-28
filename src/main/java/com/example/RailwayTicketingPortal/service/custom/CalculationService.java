package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import com.example.RailwayTicketingPortal.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CalculationService {

    private final UserRepository userRepository;

    private final ValidationService validationService;

    private final UserMapper userMapper;

    public static final String VIENNA = "Vienna";
    public static final String FRANKFURT = "Frankfurt";
    public static final String MUNICH = "Munich";
    public static final String UNDER_16_YEARS = "under 16 years";


    //take train before 9.30am & 4-7:30pm => rushHour = full price
    //'over 60s rail card' => 34% discount
    //with child under 16 years old and 'family card'=> 50% discount on every ticket
    //with child under 16 years old => 10% discount on every ticket

    public Double calculatePrice(List<TicketDTO> tickets) throws Exception {
        Double total = 0.0;
        for (TicketDTO ticketDTO : tickets) {
            Double basePrice = calculatePriceBasedOnDistance(ticketDTO);
            basePrice = calculatePriceBasedOnHour(basePrice, ticketDTO);
            basePrice = calculatePriceBasedOnYears(basePrice, ticketDTO);

            total += basePrice;
        }

        return total;
    }

    private Double calculatePriceBasedOnYears(Double basePrice, TicketDTO ticketDTO) throws Exception {
        validationService.validateUser(ticketDTO.getUserId());
        Optional<User> optionalUser = userRepository.findById(ticketDTO.getUserId());
        UserDTO userDTO = new UserDTO();
        if(optionalUser.isPresent()){
            userDTO = userMapper.toDTO(optionalUser.get());
        }

        //with child under 16 years old and 'family card'=> 50% discount on every ticket
        if(UNDER_16_YEARS.equals(ticketDTO.getType()) && userDTO.isWithFamilyCard()){
            basePrice *= 0.5;

        //with child under 16 years old => 10% discount on every ticket
        }else if(UNDER_16_YEARS.equals(ticketDTO.getType())){
            basePrice *= 0.9;
        }

        return basePrice;
    }

    private Double calculatePriceBasedOnHour(Double basePrice, TicketDTO ticketDTO){
        LocalTime departureTime = ticketDTO.getDepartureTime();

        //9:30-4am & after 7:30pm => 5% discount
        if((departureTime.isAfter(LocalTime.of(9,30)) && departureTime.isBefore(LocalTime.of(16, 0)))
                || (departureTime.isAfter(LocalTime.of(19,0)) && departureTime.isBefore(LocalTime.of(9,30)))){
            basePrice *= 0.95;
        }

        return basePrice;
    }

    private Double calculatePriceBasedOnDistance(TicketDTO ticketDTO){
        Double ticketPrice = 0.0;
        String startDestination = ticketDTO.getStartDestination();
        String endDestination = ticketDTO.getEndDestination();

        if((VIENNA.equals(startDestination) && FRANKFURT.equals(endDestination)
                ||(FRANKFURT.equals(startDestination) && VIENNA.equals(endDestination)))){
            ticketPrice += 50.00;
        }
        else if((VIENNA.equals(startDestination) && MUNICH.equals(endDestination)
                ||(MUNICH.equals(startDestination) && VIENNA.equals(endDestination)))){
            ticketPrice += 75.00;

        }else if((FRANKFURT.equals(startDestination) && MUNICH.equals(endDestination)
                ||(MUNICH.equals(startDestination) && FRANKFURT.equals(endDestination)))){
            ticketPrice += 100.00;
        }
        return ticketPrice;
    }
}
