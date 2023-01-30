package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.LoginDTO;
import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.service.AppUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/food-corner/users")
@CrossOrigin(origins = "http://localhost:3000/",allowCredentials = "true")
public class AuthController {

    private final AppUserService appUserService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AppUserService appUserService, AuthenticationManager authenticationManager) {
        this.appUserService = appUserService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<Map<?,?>> register(
            @RequestBody @Valid RegisterDTO registerDTO,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            var registerErrors = errors(bindingResult);
            return new ResponseEntity<>(registerErrors, HttpStatus.BAD_REQUEST);
        }

        if (!registerDTO.getPassword().equals(registerDTO.getRepeatPassword())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("error", "Passwords do not match");
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            Map<String, Object> register = appUserService.register(registerDTO);
            response.setHeader("Authorization", "Bearer " + register.get("token"));
            return new ResponseEntity<>(register, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> registerErrors = new HashMap<>();
            registerErrors.put("error", e.getMessage());
            return new ResponseEntity<>(registerErrors, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<?, ?>> login(
            @RequestBody @Valid LoginDTO loginDTO,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            var bindingResultErrors = errors(bindingResult);
            return new ResponseEntity<>(bindingResultErrors, HttpStatus.BAD_REQUEST);
        }

        try {
            Map<String, Object> login = appUserService.login(loginDTO);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                    loginDTO.getPassword()));
            response.setHeader("Authorization", "Bearer " + login.get("token"));
            return new ResponseEntity<>(login, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> loginErrors = new HashMap<>();
            loginErrors.put("error", e.getMessage());
            return new ResponseEntity<>(loginErrors, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        response.setHeader("Authorization", "Bearer " + null);
        return new ResponseEntity<>(Collections.singletonMap("logout", "success"), HttpStatus.OK);
    }

    private Map<String, String> errors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errors.put("error", fieldError.getDefaultMessage());
        });
        return errors;
    }
}
