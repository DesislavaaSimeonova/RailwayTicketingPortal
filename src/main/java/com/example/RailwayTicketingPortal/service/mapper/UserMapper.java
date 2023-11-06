package com.example.RailwayTicketingPortal.service.mapper;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
