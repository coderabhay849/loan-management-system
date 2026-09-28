package com.example.loanmanagement.service;

import com.example.loanmanagement.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUserStatus(Long id, boolean enabled);
}