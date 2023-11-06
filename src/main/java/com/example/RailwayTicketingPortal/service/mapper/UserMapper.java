package com.example.RailwayTicketingPortal.service.mapper;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "withFamilyCard", ignore = true)
    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}
