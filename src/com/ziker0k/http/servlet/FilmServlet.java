package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.FilmDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.service.PersonService;
import com.ziker0k.http.service.FilmService;
import com.ziker0k.http.service.ReviewService;
import com.ziker0k.http.util.JspHelper;
import com.ziker0k.http.util.UrlPath;
import com.ziker0k.http.validator.Error;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/films")
public class FilmServlet extends HttpServlet {
    private final FilmService filmService = FilmService.getInstance();
    private final PersonService personService = PersonService.getInstance();
    private final ReviewService reviewService = ReviewService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var filmId = req.getParameter("filmId");
        var year = req.getParameter("year");
        if (filmId != null) {
            FilmDto filmDto = filmService.findById(Long.parseLong(filmId));
            var allActorsByFilmId = personService.findAllActorsByFilmId(Long.parseLong(filmId));
            var allDirectorsByFilmId = personService.findAllDirectorsByFilmId(Long.parseLong(filmId));
            var reviews = reviewService.findAllByFilmId(Long.parseLong(filmId));
            req.setAttribute("film", filmDto);
            req.setAttribute("actors", allActorsByFilmId);
            req.setAttribute("directors", allDirectorsByFilmId);
            req.setAttribute("reviews", reviews);
            req.setAttribute("ratings" , List.of("0", "1", "2", "3", "4", "5"));

            req.getRequestDispatcher(JspHelper.getPath("film")).forward(req, resp);
        }
        else if (year != null) {
            try {
                req.setAttribute("films", filmService.findAllByReleaseYear(year));
            } catch (ValidationException e) {
                req.setAttribute("errors", e.getErrors());
            }
            req.getRequestDispatcher(JspHelper.getPath("films")).forward(req, resp);
        }
        else {
            req.setAttribute("films", filmService.findAll());
            req.getRequestDispatcher(JspHelper.getPath("films")).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/review?filmId=" + req.getParameter("filmId")).include(req, resp);

        if(req.getAttribute("errors") != null) {
            doGet(req, resp);
        } else {
            var prevPage = req.getHeader("referer");
            var page = prevPage != null ? prevPage : UrlPath.FILMS;
            resp.sendRedirect(page);
        }
    }
}
