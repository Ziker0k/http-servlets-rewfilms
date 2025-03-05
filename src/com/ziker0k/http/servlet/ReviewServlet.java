package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.CreateReviewDto;
import com.ziker0k.http.dto.CreateUserDto;
import com.ziker0k.http.dto.UserDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.service.ReviewService;
import com.ziker0k.http.util.UrlPath;
import com.ziker0k.http.validator.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(UrlPath.REVIEW)
public class ReviewServlet extends HttpServlet {
    private final ReviewService reviewService = ReviewService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user");
        var reviewDto = CreateReviewDto.builder()
                .filmId(req.getParameter("filmId"))
                .user(user)
                .description(req.getParameter("description"))
                .rating(req.getParameter("rating"))
                .build();
        try {
            reviewService.create(reviewDto);
        } catch (ValidationException validationException) {
            req.setAttribute("errors", validationException.getErrors());
//            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, validationException.getErrors().toString());
        }
    }
}
