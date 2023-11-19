package com.example.RailwayTicketingPortal.service;

import com.example.RailwayTicketingPortal.domain.Reservation;
import com.example.RailwayTicketingPortal.repository.ReservationRepository;
import com.example.RailwayTicketingPortal.service.custom.ValidationService;
import com.example.RailwayTicketingPortal.service.dto.ReservationDTO;
import com.example.RailwayTicketingPortal.service.mapper.ReservationMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ReservationService {

    private static final String CREATED = "CREATED";
    private static final String UPDATED = "UPDATED";
    private static final String CANCELLED = "CANCELLED";

    private final ReservationMapper reservationMapper;

    private final ReservationRepository reservationRepository;

    private final ValidationService validationService;

    public ReservationDTO create(ReservationDTO reservationDTO) throws Exception{
        if (reservationDTO == null) {
            throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
        }
        validationService.validateReservationParameters(reservationDTO.getUserId(), reservationDTO.getTicketId());

        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        reservation.setStatus(CREATED);
        reservation = reservationRepository.save(reservation);
        if (reservation.getId() != null) {
            return reservationMapper.toDTO(reservation);
        }
        throw new HttpServerErrorException(HttpStatus.EXPECTATION_FAILED);
    }

    public ReservationDTO update(ReservationDTO reservationDTO, Long reservationId) {
        if (reservationDTO != null && reservationId != null) {
            Reservation currentReservation = getById(reservationId);

            Reservation reservation = reservationMapper.toEntity(reservationDTO);
            reservation.setId(currentReservation.getId());
            reservation.setStatus(UPDATED);

            Reservation updatedReservation = reservationRepository.save(reservation);
            return reservationMapper.toDTO(updatedReservation);
        }
        throw new HttpServerErrorException(HttpStatus.NOT_MODIFIED);
    }

    public Reservation getById(Long id) {
        if (id != null) {
            Optional<Reservation> reservation = reservationRepository.findById(id);
            return reservation.orElseThrow(() -> new HttpServerErrorException(HttpStatus.NO_CONTENT));
        }
        throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
    }

    public List<Reservation> getAllForAUser(Long userId) throws Exception{
        validationService.validateUser(userId);
        return reservationRepository.findAllByUserId(userId);
    }

    public ReservationDTO cancel(ReservationDTO reservationDTO) throws Exception {
        validationService.validateReservation(reservationDTO);
        Reservation currentReservation = getById(reservationDTO.getId());
        currentReservation.setStatus(CANCELLED);
        reservationRepository.save(currentReservation);
        return reservationMapper.toDTO(currentReservation);

    }
}

