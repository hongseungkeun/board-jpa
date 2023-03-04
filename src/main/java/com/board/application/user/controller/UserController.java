package com.board.application.user.controller;

import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.dto.LoginUserRequest;
import com.board.application.user.service.UserService;
import com.board.core.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginUserRequest request, HttpServletRequest httpServletRequest) {
        Long id = userService.loginUser(request);
        SessionUtil.createSession(id, httpServletRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logoutUser(HttpSession session) {
        SessionUtil.removeSession(session);

        return ResponseEntity.ok().build();
    }
}
