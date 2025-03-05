package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.PersonDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.service.FilmService;
import com.ziker0k.http.service.PersonService;
import com.ziker0k.http.util.JspHelper;
import com.ziker0k.http.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.PERSON)
public class PersonServlet extends HttpServlet {
    private final PersonService personService = PersonService.getInstance();
    private final FilmService filmService = FilmService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var personId = req.getParameter("personId");
        try {
            var personDto = personService.findById(personId);
            req.setAttribute("person", personDto);
            if (personDto != null) {
                var filmsAsActor = filmService.findByActorId(personDto.getId());
                var filmsAsDirector = filmService.findByDirectorId(personDto.getId());
                req.setAttribute("filmsAsActor", filmsAsActor);
                req.setAttribute("filmsAsDirector", filmsAsDirector);
            }
        } catch (ValidationException exception) {
            req.setAttribute("error", exception.getMessage());
        } finally {
            req.getRequestDispatcher(JspHelper.getPath("person")).forward(req, resp);
        }


//        req.setAttribute("filmsAsActor", filmService.findByActorId(personId));
//        req.setAttribute("filmsAsDirector", filmService.findByDirectorId(personId));
//        req.getRequestDispatcher(JspHelper.getPath("person")).forward(req, resp);
    }
}
