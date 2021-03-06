package com.example.demo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.domain.Schedule;
import com.example.demo.domain.User;
import com.example.demo.domain.UserType;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.UserRepository;

import static java.time.DayOfWeek.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    private static final TreeSet<DayOfWeek> WORK_DAYS = new TreeSet<>(
            EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)
    );

    private User user = new User("test", "test", UserType.ADMIN);

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        scheduleRepository.deleteAll();
        user = userRepository.save(user);
    }
    @AfterEach
    void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    void createdUser_ShouldBeTheSameAndHaveNoSchedule() {
        List<User> users = userRepository.findAll();
        assertFalse(users.isEmpty(), "Failed to create user");
        assertTrue(users.contains(user));
        users = userRepository.findByUsername(user.getUsername());
        assertFalse(users.isEmpty(), "Failed to find created user by name");
        users = userRepository.findByUsername("No such user");
        assertTrue(users.isEmpty(), "Found unexpected user");
        List<Schedule> schedules = scheduleRepository.findAll();
        assertTrue(schedules.isEmpty());
    }

    @Test
    void createSchedule_ShouldBePersisted() {
        Schedule schedule =
                new Schedule(
                        LocalDate.now(), LocalDate.now().plusDays(30),
                        (short) 10, (short) 15,
                        WORK_DAYS
                );
        user.setSchedules(List.of(schedule));
        userRepository.save(user);
        List<Schedule> schedules = scheduleRepository.findAll();
        assertFalse(schedules.isEmpty());
    }


}
