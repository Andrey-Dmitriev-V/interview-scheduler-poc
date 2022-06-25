package com.example.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.User;
import com.example.demo.domain.UserType;
import com.example.demo.dto.CreateUserRequestDto;
import com.example.demo.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUsersCreated() {
        CreateUserRequestDto userRequestDto = new CreateUserRequestDto("test", "test", UserType.ADMIN);
        User user = new User(userRequestDto);
        user = userRepository.save(user);
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty(), "Failed to create user");
        assertTrue(users.contains(user));

        users = userRepository.findByUsername(user.getUsername());
        assertFalse(users.isEmpty(), "Failed to find created user by name");
        users = userRepository.findByUsername("No such user");
        assertTrue(users.isEmpty(), "Found unexpected user");
    }

}
