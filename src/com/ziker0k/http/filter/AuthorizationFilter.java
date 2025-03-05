package com.ziker0k.http.filter;

import com.ziker0k.http.dto.UserDto;
import com.ziker0k.http.entity.Role;
import com.ziker0k.http.util.UrlPath;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.ziker0k.http.util.UrlPath.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, IMAGES, LOCALE, FILMS, PERSON, USER);
    private static final Set<String> ADMIN_PATH = Set.of(EDIT_FILM, EDIT_PERSON);
    private static final Set<String> USER_PATH = Set.of(REVIEW, LOGOUT);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();

        if((isAdminPath(uri) && isAdminLoggedIn(servletRequest)) ||
           (isUserPath(uri) && (isUserLoggedIn(servletRequest)) ||
            isPublicPath(uri))) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            var prevPage = ((HttpServletResponse) servletResponse).getHeader("referer");
            ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var userDto = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return userDto != null;
    }

    private boolean isAdminLoggedIn(ServletRequest servletRequest) {
        var userDto = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return (userDto != null && (userDto.getRole().equals(Role.ADMIN)));
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }

    private boolean isAdminPath(String uri) {
        return ADMIN_PATH.stream().anyMatch(uri::startsWith);
    }

    private boolean isUserPath(String uri) {
        return USER_PATH.stream().anyMatch(uri::startsWith);
    }
}
