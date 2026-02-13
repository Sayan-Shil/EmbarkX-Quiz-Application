package com.embarkx.embarkxquiz.services.user;

import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.enums.UserRole;
import com.embarkx.embarkxquiz.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserResponseDTO getUserById(Long id);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getUsersByRole(UserRole role);
    UserResponseDTO updateUser(Long id, User user);
    void deleteUser(Long id);
    void activateUser(Long id);
    void deactivateUser(Long id);
    Boolean existsByEmail(String email);
}
