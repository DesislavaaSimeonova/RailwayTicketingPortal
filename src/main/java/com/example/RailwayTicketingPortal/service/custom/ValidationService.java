package com.example.RailwayTicketingPortal.service.custom;

import com.example.RailwayTicketingPortal.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ValidationService {

    private static final String USER_NOT_EXIST = "User does not exist";

    private final UserRepository userRepository;

    public void validateUser(Long userId) throws Exception {
        if(userId == null || !userRepository.existsById(userId)) throw new Exception(USER_NOT_EXIST);
    }
}
