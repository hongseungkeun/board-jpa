package com.board.application.user.service;

import com.board.application.user.domain.User;
import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.dto.LoginUserRequest;
import com.board.application.user.repository.UserRepository;
import com.board.core.exception.AlreadyExistUserException;
import com.board.core.exception.UserNotFoundException;
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
        isExistEmail(request.email());

        User user = request.toUser();
        userRepository.save(user);
    }

    public Long loginUser(LoginUserRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

        user.isPossibleLogin(request.password());

        return user.getId();
    }

    public User findUserById(Long userId){
       return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void isExistEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw new AlreadyExistUserException(ErrorCode.ALREADY_EXIST_USER);
        }
    }
}
