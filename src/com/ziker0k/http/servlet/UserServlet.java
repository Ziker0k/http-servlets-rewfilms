package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.ReviewDto;
import com.ziker0k.http.dto.UserDto;
import com.ziker0k.http.service.ReviewService;
import com.ziker0k.http.service.UserService;
import com.ziker0k.http.util.JspHelper;
import com.ziker0k.http.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(UrlPath.USER)
public class UserServlet extends HttpServlet {
    UserService userService = UserService.getInstance();
    ReviewService reviewService = ReviewService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userId = req.getParameter("userId");
        try {
            var userDto = userService.findById(userId);
            if (userDto != null) {
                var reviews = reviewService.findAllByUserId(userDto.getId());
                req.setAttribute("reviews", reviews);
                req.setAttribute("user", userDto);
            }
            req.getRequestDispatcher(JspHelper.getPath("user")).forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendRedirect(UrlPath.LOGIN);
        }
    }
}
