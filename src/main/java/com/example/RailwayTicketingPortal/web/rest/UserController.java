package com.example.RailwayTicketingPortal.web.rest;

import com.example.RailwayTicketingPortal.domain.User;
import com.example.RailwayTicketingPortal.service.UserService;
import com.example.RailwayTicketingPortal.service.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) throws Exception {
        UserDTO createdUser = userService.create(userDTO);
        return ResponseEntity.ok().body(createdUser);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO playerDTO,
                                              @RequestParam Long userId) {
        UserDTO updatedUser = userService.update(playerDTO,userId);
        return ResponseEntity.ok().body(updatedUser);
    }


    @GetMapping("/user/{userId}")
    public User getPlayerById(@RequestParam Long userId) {
        return userService.getById(userId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUser(@RequestParam Long userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
}
