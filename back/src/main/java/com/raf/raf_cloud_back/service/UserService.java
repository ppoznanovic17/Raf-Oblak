package com.raf.raf_cloud_back.service;

import com.raf.raf_cloud_back.dto.*;
import com.raf.raf_cloud_back.model.User;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface UserService {

    public LoginResponse login(LoginRequest loginRequest);

    public List<UserResponse> getAllUsers();

    public UserResponse getUserById(Long id);

    public UserResponse createUser(UserCreateRequest request) ;

    public UserResponse updateUser(Long id, UserUpdateRequest request);

    public void deleteUser(Long id);


}
