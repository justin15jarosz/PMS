package com.pm.authservice.service;

import com.pm.authservice.model.User;
import com.pm.authservice.respository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
