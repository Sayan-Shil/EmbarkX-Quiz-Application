package com.embarkx.embarkxquiz.mapper;

import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.models.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserResponseDTO toResponseDTO(User user);
    User toEntity(UserResponseDTO userResponseDTO);
}
