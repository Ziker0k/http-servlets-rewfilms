package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.CreateFilmDto;
import com.ziker0k.http.entity.Country;
import com.ziker0k.http.entity.Genre;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.service.PersonService;
import com.ziker0k.http.service.FilmService;
import com.ziker0k.http.util.JspHelper;
import com.ziker0k.http.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebServlet(UrlPath.EDIT_FILM)
public class EditFilmServlet extends HttpServlet {
    PersonService personService = PersonService.getInstance();
    FilmService filmService = FilmService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var persons = personService.findAll();

        req.setAttribute("countries", Arrays.asList(Country.values()));
        req.setAttribute("genres", Arrays.asList(Genre.values()));
        req.setAttribute("persons", persons);

        req.getRequestDispatcher(JspHelper.getPath("createFilm")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var filmDto = CreateFilmDto.builder()
                .name(req.getParameter("film"))
                .description(req.getParameter("description"))
                .releaseDate(req.getParameter("release-date"))
                .country(req.getParameter("country"))
                .genre(req.getParameter("genre"))
                .actors(req.getParameterValues("actors"))
                .directors(req.getParameterValues("directors"))
                .build();
        try {
            filmService.create(filmDto);
            resp.sendRedirect("/films");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
