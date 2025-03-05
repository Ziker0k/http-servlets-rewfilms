<%--
  Created by IntelliJ IDEA.
  User: maximg
  Date: 04.02.2025
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="util/locale.jsp" %>
<html>
<head>
    <title><fmt:message key="page.person.title"/></title>
    <style>
        <%@ include file="../css/style.css" %>
    </style>
</head>
<body>
<div id="container">
    <%@ include file="util/header.jsp" %>

    <h3><fmt:message key="page.person.name"/>: ${requestScope.person.fullName}</h3>
    <h3><fmt:message key="page.person.birthday"/>: ${requestScope.person.birthday}</h3>

    <c:if test="${not empty requestScope.filmsAsActor}">
        <h2><fmt:message key="page.person.filmsLikeActor"/></h2>
        <ul>
            <c:forEach var="film" items="${requestScope.filmsAsActor}">
                <li>
                    <a href="${pageContext.request.contextPath}/films?filmId=${film.id}">${film.name}</a><br>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${not empty requestScope.filmsAsDirector}">
        <h2>
            <fmt:message key="page.person.filmsLikeDirector"/>
        </h2>
        <ul>
            <c:forEach var="film" items="${requestScope.filmsAsDirector}">
                <li>
                    <a href="${pageContext.request.contextPath}/films?filmId=${film.id}">${film.name}</a><br>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <%@ include file="util/footer.jsp" %>
</div>
</body>
</html>