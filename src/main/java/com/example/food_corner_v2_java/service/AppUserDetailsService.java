//package com.example.food_corner_v2_java.service;
//
//import com.example.food_corner_v2_java.model.UserRoles;
//import com.example.food_corner_v2_java.model.Users;
//import com.example.food_corner_v2_java.repository.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class AppUserDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public AppUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.
//                findByEmail(username).
//                map(this::map).
//                orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found!"));
//    }
//
//    private UserDetails map(Users user) {
//        return
//                User.builder().
//                        username(user.getEmail()).
//                        password(user.getPassword()).
//                        authorities(user.
//                                getUserRoles().
//                                stream().
//                                map(this::map).
//                                toList()).
//                        build();
//    }
//
//    private GrantedAuthority map(UserRoles userRole) {
//        return new SimpleGrantedAuthority("ROLE_" +
//                userRole.getUserRoles().name());
//    }
//}
