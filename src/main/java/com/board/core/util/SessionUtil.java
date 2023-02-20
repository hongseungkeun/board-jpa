package com.board.core.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private static final String SESSION_KEY = "SESSION-KEY";

    private SessionUtil(){

    }

    public static void createSession(Long id, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();

        if (hasKeyAttribute(session)) {
            removeSession(session);
        }
        createSession(id, httpServletRequest.getSession());
    }

    public static void removeSession(HttpSession session) {
        session.invalidate();
    }

    private static boolean hasKeyAttribute(HttpSession session) {
        return session.getAttribute(SESSION_KEY) != null;
    }

    private static void createSession(Long id, HttpSession session) {
        session.setAttribute(SESSION_KEY, id);
    }

}
