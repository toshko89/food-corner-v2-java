package com.example.food_corner_v2_java.model;

import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRolesEnum userRoles;

    public UserRoles() {
    }

    public Long getId() {
        return id;
    }

    public UserRoles setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRolesEnum getUserRoles() {
        return userRoles;
    }

    public UserRoles setUserRoles(UserRolesEnum userRoles) {
        this.userRoles = userRoles;
        return this;
    }
}
