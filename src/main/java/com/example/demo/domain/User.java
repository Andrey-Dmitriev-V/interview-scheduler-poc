package com.example.demo.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;
import java.util.Objects;

import com.example.demo.dto.CreateUserRequestDto;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name="users", schema="public")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(length=50, nullable=false)
    private String username;

    @Column(length=50, nullable=false)
    private String password;

    @Column(name = "user_type", length=50, nullable=false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(targetEntity = Schedule.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<Schedule> schedules;

    public User(Long id, String username, String password, UserType userType, List<Schedule> schedules) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.schedules = schedules;
    }

    public User(CreateUserRequestDto userRequestDto) {
        this.username = userRequestDto.username();
        this.password = userRequestDto.password();
        this.userType = userRequestDto.userType();
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id.equals(user.id) && username.equals(user.username) && password.equals(user.password) && userType == user.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, userType);
    }



}