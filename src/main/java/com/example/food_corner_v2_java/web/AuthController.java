package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.service.AppUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/food-corner/users")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody @Valid RegisterDTO registerDTO,
            BindingResult bindingResult,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        String token = appUserService.register(registerDTO);
        response.setHeader("Authorization", "Bearer " + token);
        return new ResponseEntity<>(Collections.singletonMap("token", token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
//            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok("Tuka e!!!!");
    }


}
