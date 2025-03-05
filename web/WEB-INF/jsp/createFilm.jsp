<%--
  Created by IntelliJ IDEA.
  User: maximg
  Date: 30.01.2025
  Time: 00:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="util/locale.jsp" %>
<html>
<head>
    <title><fmt:message key="page.createFilm.title"/></title>
    <style>
        <%@ include file="../css/style.css" %>
    </style>
</head>
<body>
<div id="container">
    <%@ include file="util/header.jsp" %>
    <form action="${pageContext.request.contextPath}/edit-film" method="post">
        <label for="film"><fmt:message key="page.createFilm.film"/>
            <input type="text" name="film" id="film">
        </label><br><br>
        <label for="description"><fmt:message key="page.createFilm.description"/>
            <textarea name="description" id="description"></textarea>
        </label><br><br>

        <label for="release-date"><fmt:message key="page.createFilm.releaseDate"/>
            <input type="date" name="release-date" id="release-date">
        </label><br><br>

        <c:if test="${not empty requestScope.countries}">
            <label for="country"><fmt:message key="page.createFilm.country"/>
                <select name="country" id="country">
                    <c:forEach var="country" items="${requestScope.countries}">
                        <option value="${country}">${country}</option>
                    </c:forEach>
                </select>
            </label><br><br>
        </c:if>

        <c:if test="${not empty requestScope.genres}">
            <label for="genre"><fmt:message key="page.createFilm.genre"/>
                <select name="genre" id="genre">
                    <c:forEach var="genre" items="${requestScope.genres}">
                        <option value="${genre}">${genre}</option>
                    </c:forEach>
                </select>
            </label><br><br>
        </c:if>

        <c:if test="${not empty requestScope.persons}">
            <label for="actors"><fmt:message key="page.createFilm.actors"/>
                <select id="actors" name="actors" size="4" multiple>
                    <c:forEach var="actor" items="${requestScope.persons}">
                        <option value="${actor.id}">${actor.fullName}</option>
                    </c:forEach>
                </select>
            </label><br><br>
        </c:if>

        <c:if test="${not empty requestScope.persons}">
            <label for="directors"><fmt:message key="page.createFilm.directors"/>
                <select id="directors" name="directors" size="4" multiple>
                    <c:forEach var="director" items="${requestScope.persons}">
                        <option value="${director.id}">${director.fullName}</option>
                    </c:forEach>
                </select>
            </label><br><br>
        </c:if>

        <button type="submit"><fmt:message key="page.createFilm.button.submit"/></button>

        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span><br>
                </c:forEach>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>