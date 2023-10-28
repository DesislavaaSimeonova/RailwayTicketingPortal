package com.example.RailwayTicketingPortal.service;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.repository.UserRepository;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import com.example.RailwayTicketingPortal.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserDTO create(UserDTO userDTO) {

        if(userDTO == null){
            throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
        }
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        if(user.getId() != null){
            return userMapper.toDTO(user);
        }
        throw new HttpServerErrorException(HttpStatus.EXPECTATION_FAILED);
    }

    public UserDTO update(UserDTO userDTO, Long id) {

        if(userDTO != null && id != null){
            User currentUser =getById(id);

            User user = userMapper.toEntity(userDTO);
            user.setId(currentUser.getId());

            User updatedUser = userRepository.save(user);
            return userMapper.toDTO(updatedUser);
        }
        throw new HttpServerErrorException(HttpStatus.NOT_MODIFIED);
    }

    public User getById(Long id) {
        if(id != null){
            Optional<User> user = userRepository.findById(id);
            return user.orElseThrow(() ->  new HttpServerErrorException(HttpStatus.NO_CONTENT));
        }
        throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
    }

    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public void delete(Long id) {
        if(id != null && userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        else throw new HttpServerErrorException(HttpStatus.EXPECTATION_FAILED);
    }
}
