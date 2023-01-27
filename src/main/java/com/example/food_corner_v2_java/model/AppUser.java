package com.example.food_corner_v2_java.model;

import com.example.food_corner_v2_java.model.enums.UserRolesEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String address;

    @Column(name = "user_role", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRolesEnum userRole;


    public AppUser() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.userRole.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AppUser setName(String name) {
        this.name = name;
        return this;
    }


    public String getEmail() {
        return email;
    }

    public AppUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public AppUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AppUser setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AppUser setCity(String city) {
        this.city = city;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AppUser setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserRolesEnum getUserRole() {
        return userRole;
    }


    public AppUser setUserRole(UserRolesEnum userRole) {
        this.userRole = userRole;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id.equals(appUser.id) && email.equals(appUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
