package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/sessions")
public class SessionServlet extends HttpServlet {
    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession();
        var user = (UserDto) session.getAttribute(USER);
        if(user == null) {
            var guest = UserDto.builder()
                    .id(25L)
                    .name("Guest")
                    .email("guest@gmail.com")
                    .build();
            session.setAttribute(USER, guest);
        }

    }
}
