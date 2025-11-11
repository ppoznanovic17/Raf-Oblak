package com.raf.raf_cloud_back.service.implementation;

import com.raf.raf_cloud_back.dto.*;
import com.raf.raf_cloud_back.exception.BadRequestException;
import com.raf.raf_cloud_back.exception.UnauthorizedException;
import com.raf.raf_cloud_back.model.User;
import com.raf.raf_cloud_back.repository.UserRepository;
import com.raf.raf_cloud_back.security.JwtUtil;
import com.raf.raf_cloud_back.security.PasswordUtil;
import com.raf.raf_cloud_back.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordUtil passwordUtil;

    private final JwtUtil jwtUtil;

    UserServiceImpl(UserRepository userRepository, PasswordUtil passwordUtil, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordUtil = passwordUtil;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Neispravni kredencijali"));

        if(!passwordUtil.checkPassword(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Neispravni kredencijali");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getPermissions());

        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                new ArrayList<>(user.getPermissions())
        );
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen"));
        return mapToResponse(user);
    }

    public UserResponse createUser(UserCreateRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Korisnik sa ovim emailom vec postoji");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordUtil.hashPassword(request.getPassword()));
        user.setPermissions(new HashSet<>(request.getPermissions()));

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    public UserResponse updateUser(Long id, UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Korisnik nije pronadjen"));

        // Proveri da li email nije zauzet od strane drugog korisnika
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Korisnik sa ovim emailom vec postoji");
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPermissions(new HashSet<>(request.getPermissions()));

        user = userRepository.save(user);
        return mapToResponse(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Korisnik nije pronadjen");
        }
        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                new ArrayList<>(user.getPermissions())
        );
    }

}
