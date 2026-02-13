package com.embarkx.embarkxquiz.services.user;

import com.embarkx.embarkxquiz.dto.response.auth.UserResponseDTO;
import com.embarkx.embarkxquiz.enums.UserRole;
import com.embarkx.embarkxquiz.exception.custom.ResourceNotFoundException;
import com.embarkx.embarkxquiz.mapper.UserMapper;
import com.embarkx.embarkxquiz.models.user.User;
import com.embarkx.embarkxquiz.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        log.debug("Fetching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id  "+id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserByEmail(String email) {
        log.debug("Fetching user with email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("User not found with email  "+email));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        log.debug("Fetching all users");
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponseDTO).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getUsersByRole(UserRole role) {
        log.debug("Fetching users by role: {}", role);
        List<User> users = userRepository.findByRole(role);
        return users.stream().map(userMapper::toResponseDTO).toList();
    }

    @Override
    public UserResponseDTO updateUser(Long id, User updatedUser) {
       log.debug("Fetching existing user with id: {}", id);
       User user = userRepository.findById(id)
               .orElseThrow(()->new ResourceNotFoundException("User not found with id  "+id));
        log.debug("Updating user with id: {}", id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setProfileImageUrl(updatedUser.getProfileImageUrl());

        User savedUser = userRepository.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("Deleting user with id: {}", id);
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User not found with id  "+id);
        }
        userRepository.deleteById(id);

    }

    @Override
    public void activateUser(Long id) {
        log.debug("Activating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id  "+id));
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    public void deactivateUser(Long id) {
        log.debug("Deactivating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id  "+id));
        user.setIsActive(true);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
