package com.ziker0k.http.servlet;

import com.ziker0k.http.dto.CreatePersonDto;
import com.ziker0k.http.exception.ValidationException;
import com.ziker0k.http.service.PersonService;
import com.ziker0k.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.ziker0k.http.util.UrlPath.EDIT_PERSON;

@WebServlet(EDIT_PERSON)
public class EditPersonServlet extends HttpServlet {
    private static final PersonService personService = PersonService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("adEdit")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var actorOrDirectorDto = CreatePersonDto.builder()
                .fullName(req.getParameter("full_name"))
                .birthday(req.getParameter("birthday"))
                .build();

        try {
            personService.create(actorOrDirectorDto);
            resp.sendRedirect(EDIT_PERSON);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
