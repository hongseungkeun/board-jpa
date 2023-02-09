package com.board.application.user.controller;

import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.dto.LoginUserRequest;
import com.board.application.user.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginUserRequest request, HttpSession session) {
        Long id = userService.loginUser(request);
        session.setAttribute("SESSION-KEY", id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
