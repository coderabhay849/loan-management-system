package com.example.loanmanagement.service.serviceimpl;

import com.example.loanmanagement.dto.UserResponse;
import com.example.loanmanagement.entity.User;
import com.example.loanmanagement.exception.ResourceNotFoundException;
import com.example.loanmanagement.repository.UserRepository;
import com.example.loanmanagement.service.UserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        return mapToResponse(user);
    }

    @Override
    public UserResponse updateUserStatus(
            Long id,
            boolean enabled) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"));

        user.setEnabled(enabled);

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName(),
                user.isEnabled()
        );
    }
}
