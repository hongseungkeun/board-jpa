package com.board.core.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private static final String KEY = "SESSION-KEY";

    private SessionUtil(){

    }

    public static void createSession(Long id, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();

        if (hasKeyAttribute(session)) {
            invalidSession(session);
        }
        setLoginAttribute(id, httpServletRequest.getSession());
    }

    public static void invalidSession(HttpSession session) {
        session.invalidate();
    }

    private static boolean hasKeyAttribute(HttpSession session) {
        return session.getAttribute(KEY) != null;
    }

    private static void setLoginAttribute(Long id, HttpSession session) {
        session.setAttribute(KEY, id);
    }

}
