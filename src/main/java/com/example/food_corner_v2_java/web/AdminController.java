package com.example.food_corner_v2_java.web;


import com.example.food_corner_v2_java.model.dto.UserDTO;
import com.example.food_corner_v2_java.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/food-corner/users")
public class AdminController {

    private final AppUserService appUserService;

    public AdminController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserDTO>> findAllUsers(Principal principal) {

        if(!this.appUserService.isAdmin(principal.getName())) {
            return ResponseEntity.status(403).body(null);
        }

        try {
            List<UserDTO> allUsers = this.appUserService.findAllUsers(principal.getName());
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/admin/{id}")
    public ResponseEntity<UserDTO> makeAdmin(@PathVariable Long id, Principal principal) {

        if(!this.appUserService.isAdmin(principal.getName())) {
            return ResponseEntity.status(403).body(null);
        }

        if(id.equals(1L) || id.equals(2L) || id.equals(3L)){
            return ResponseEntity.status(403).body(null);
        }

        try {
            UserDTO userDTO = this.appUserService.editUserRole(id);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }
}
