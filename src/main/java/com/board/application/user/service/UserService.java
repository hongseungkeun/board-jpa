package com.board.application.user.service;

import com.board.application.user.domain.User;
import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserRequest request){
        User user = request.toUser();
        userRepository.save(user);
    }
}
