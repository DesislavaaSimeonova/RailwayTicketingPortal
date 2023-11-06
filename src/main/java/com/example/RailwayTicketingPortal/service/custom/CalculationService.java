package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.dto.TicketDTO;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import com.example.RailwayTicketingPortal.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    public BigDecimal calculatePrice(List<TicketDTO> tickets) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        for (TicketDTO ticketDTO : tickets) {
            BigDecimal basePrice = calculatePriceBasedOnDistance(ticketDTO);
            basePrice = calculatePriceBasedOnHour(basePrice, ticketDTO);
            if(ticketDTO.getUserId() != null){
                basePrice = calculatePriceBasedOnYears(basePrice, ticketDTO);
            }
            total = total.add(basePrice);
        }
        return total;
    }

    private BigDecimal calculatePriceBasedOnYears(BigDecimal basePrice, TicketDTO ticketDTO) throws Exception {
        validationService.validateUser(ticketDTO.getUserId());
        Optional<User> optionalUser = userRepository.findById(ticketDTO.getUserId());
        UserDTO userDTO = new UserDTO();
        if(optionalUser.isPresent()){
            userDTO = userMapper.toDTO(optionalUser.get());
        }

        boolean isWithUnder16YearsOld = userDTO.isWithChildUnder16Years();
        boolean isWithFamilyCard = userDTO.isWithFamilyCard();

        if(isWithUnder16YearsOld && isWithFamilyCard){
            basePrice = basePrice.multiply(BigDecimal.valueOf(0.5));

        }else if(isWithUnder16YearsOld){
            basePrice = basePrice.multiply(BigDecimal.valueOf(0.9));
        }

        boolean isWithOver60sCard = userDTO.isWithOver60sRailwayCard();
        if (isWithOver60sCard){
            basePrice = basePrice.multiply(BigDecimal.valueOf(0.66));
        }

        return formatPrice(basePrice);
    }

    private BigDecimal calculatePriceBasedOnHour(BigDecimal basePrice, TicketDTO ticketDTO){
        LocalTime departureTime = ticketDTO.getDepartureTime();

        boolean isNotInMorningRushHour = (departureTime.isAfter(LocalTime.of(9,30)) && departureTime.isBefore(LocalTime.of(16, 0)));
        boolean isNotInEveningRushHour =  (departureTime.isAfter(LocalTime.of(19,30)) && departureTime.isBefore(LocalTime.of(9,30)));

        if(isNotInMorningRushHour || isNotInEveningRushHour){
            basePrice = basePrice.multiply(BigDecimal.valueOf(0.95));
        }
        return basePrice;
    }

    private BigDecimal calculatePriceBasedOnDistance(TicketDTO ticketDTO){
        BigDecimal ticketPrice = BigDecimal.ZERO;
        String startDestination = ticketDTO.getStartDestination();
        String endDestination = ticketDTO.getEndDestination();

        boolean isDestinationFromViennaToFrankfurt = (VIENNA.equals(startDestination) && FRANKFURT.equals(endDestination));
        boolean isDestinationFromFrankfurtToVienna = (FRANKFURT.equals(startDestination) && VIENNA.equals(endDestination));
        boolean isDestinationFromViennaToMunich = (VIENNA.equals(startDestination) && MUNICH.equals(endDestination));
        boolean isDestinationFromMunichToVienna = (MUNICH.equals(startDestination) && VIENNA.equals(endDestination));
        boolean isDestinationFromFrankfurtToMunich = (FRANKFURT.equals(startDestination) && MUNICH.equals(endDestination));
        boolean isDestinationFromMunichToFrankfurt = (MUNICH.equals(startDestination) && FRANKFURT.equals(endDestination));

        if(isDestinationFromViennaToFrankfurt || isDestinationFromFrankfurtToVienna) {
            ticketPrice = ticketPrice.add(BigDecimal.valueOf(50.00));
        }
        else if(isDestinationFromViennaToMunich || isDestinationFromMunichToVienna){
            ticketPrice = ticketPrice.add(BigDecimal.valueOf(75.00));

        }else if(isDestinationFromFrankfurtToMunich ||isDestinationFromMunichToFrankfurt){
            ticketPrice = ticketPrice.add(BigDecimal.valueOf(100.00));
        }
        return ticketPrice;
    }

    private BigDecimal formatPrice(BigDecimal price){
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String formattedPrice = decimalFormat.format(price);
        return new BigDecimal(formattedPrice);
    }
}
