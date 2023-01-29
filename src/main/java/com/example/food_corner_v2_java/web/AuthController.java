package com.example.food_corner_v2_java.web;

import com.example.food_corner_v2_java.model.dto.RegisterDTO;
import com.example.food_corner_v2_java.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/food-corne/users")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterDTO registerDTO
    ) {

        System.out.println(registerDTO);
        String register = appUserService.register(registerDTO);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
//            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok("Tuka e!!!!");
    }


}
