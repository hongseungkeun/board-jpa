package com.board.application.user.service;

import com.board.application.user.domain.User;
import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.dto.LoginUserRequest;
import com.board.application.user.repository.UserRepository;
import com.board.core.exception.CustomException;
import com.board.core.exception.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(CreateUserRequest request){
        validEmail(request.email());

        User user = request.toUser();
        userRepository.save(user);
    }

    public Long loginUser(LoginUserRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.isPossibleLogin(request.password());

        return user.getId();
    }

    private void validEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new CustomException(ErrorCode.EXIST_USER);
        }
    }
}
