package com.example.energymanagement.dtos;

import com.example.energymanagement.entities.Role;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> {
    private String userId;
    private String name;
    private String username;
    private String password;
    private Role role;

    public UserDTO() {
    }

    public UserDTO(String userId, String name, String username, String password, Role role) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return userId;
    }

    public void setId(String id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPassword(String password) { this.password = password; }

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO personDTO = (UserDTO) o;
        return username == personDTO.username &&
                Objects.equals(name, personDTO.name) &&
                Objects.equals(password, personDTO.password) &&
                Objects.equals(role, personDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, password, role);
    }
}

