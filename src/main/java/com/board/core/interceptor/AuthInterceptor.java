package com.board.core.interceptor;

import com.board.application.post.service.PostService;
import com.board.core.exception.CustomException;
import com.board.core.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private PostService postService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HttpSession session = request.getSession(false);

        if(HttpMethod.GET.matches(request.getMethod())){
            return true;
        }

        if(session == null){
            throw new CustomException(ErrorCode.AUTH_FAILED);
        }

        return true;
    }
}
