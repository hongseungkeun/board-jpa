package com.board.application.user.controller;

import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request){
        userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
