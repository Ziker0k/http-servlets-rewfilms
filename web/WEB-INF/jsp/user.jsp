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
    <title><fmt:message key="page.user.title"/></title>
    <style>
        <%@ include file="../css/style.css" %>
    </style>
</head>
<body>
<div id="container">
    <%@ include file="util/header.jsp" %>

    <h3><fmt:message key="page.user.name"/>: ${requestScope.user.name}</h3>
    <h3><fmt:message key="page.user.birthday"/>: ${requestScope.user.birthday}</h3>

    <c:if test="${not empty requestScope.reviews}">
        <h2><fmt:message key="page.user.reviews"/></h2>
        <c:forEach var="review" items="${requestScope.reviews}">
            <div id="field-review">
                <span><fmt:message key="page.film.reviews.rating"/>: ${review.rating}</span><br>
                <comment><fmt:message key="page.film.reviews.description"/>: ${review.description}</comment>
            </div>
            <br><br>
        </c:forEach>
    </c:if>

    <%@ include file="util/footer.jsp" %>
</div>
</body>
</html>
