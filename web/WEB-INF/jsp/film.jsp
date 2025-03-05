<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="util/locale.jsp" %>
<html>
<head>
    <title><fmt:message key="page.header.films"/></title>
    <style>
        <%@ include file="../css/style.css" %>
    </style>
</head>
<body>
<div id="container">
    <%@ include file="util/header.jsp" %>
    <h2>${requestScope.film.name}</h2>
    <h2>${requestScope.film.description}</h2>
    <c:if test="${not empty requestScope.actors}">
        <div>
            <h3><fmt:message key="page.film.actors"/></h3><br>
            <c:forEach var="actor" items="${requestScope.actors}" varStatus="loop">
                <a href="${pageContext.request.contextPath}/person?personId=${actor.id}">${actor.fullName}</a>
                <a>${!loop.last ? ',' : ''}</a>
            </c:forEach>
        </div>
        <br>
    </c:if>
    <c:if test="${not empty requestScope.directors}">
        <div>
            <h3><fmt:message key="page.film.directors"/></h3><br>
            <c:forEach var="director" items="${requestScope.directors}" varStatus="loop">
                <a href="${pageContext.request.contextPath}/person?personId=${director.id}">${director.fullName}</a>
                <a>${!loop.last ? ',' : ''}</a>
            </c:forEach>
        </div>
    </c:if>

    <c:if test="${not empty sessionScope.user}">
        <div id="add-review">
            <form action="${pageContext.request.contextPath}/films?filmId=${requestScope.film.id}" method="post">
                <label for="description"><fmt:message key="page.film.description"/>
                    <textarea name="description" id="description"></textarea>
                </label><br><br>
                <label for="rating"><fmt:message key="page.film.rating"/>
                    <select name="rating" id="rating">
                        <c:forEach var="rating" items="${requestScope.ratings}">
                            <option value="${rating}">${rating}</option>
                        </c:forEach>
                    </select>
                </label><br><br>
                <button type="submit"><fmt:message key="page.film.button.submit"/></button>
            </form>
        </div>
    </c:if>

    <c:if test="${not empty requestScope.reviews}">
        <div id="reviews">
            <h3><fmt:message key="page.film.reviews"/></h3><br>
            <c:forEach var="review" items="${requestScope.reviews}">
                <div id="field-review">
                    <span><fmt:message key="page.film.reviews.username"/>: </span>
                    <a href="${pageContext.request.contextPath}/user?userId=${review.user.id}">${review.user.name}</a>
                    <span><fmt:message key="page.film.reviews.rating"/>: ${review.rating}</span><br>
                    <comment><fmt:message key="page.film.reviews.description"/>: ${review.description}</comment>
                </div>
                <br><br>
            </c:forEach>
        </div>
    </c:if>
</div>
</body>
</html>
