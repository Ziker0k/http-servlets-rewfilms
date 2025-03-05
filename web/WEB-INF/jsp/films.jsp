<%--
  Created by IntelliJ IDEA.
  User: maximg
  Date: 03.02.2025
  Time: 22:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="util/locale.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="page.homepage.title"/></title>
    <style><%@ include file="../css/style.css"%></style>
</head>

<body>

<div id="container">
    <%@ include file="util/header.jsp"%>

    <div id="content">
        <div id="menu">
            <div id="block1">
                <h2>Меню</h2>
                <form action="" method="get">
                    <h3><fmt:message key="page.homepage.typeYear"/></h3>
                    <label for="year">
                        <input type="text" name="year" id="year" required>
                    </label>
                    <button type="submit"><fmt:message key="page.homepage.button.submit"/></button>
                    <c:if test="${param.error != null}">
                        <div style="color: red">
                            <span><fmt:message key="page.homepage.error"/></span>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>
        <div id="content-right">
            <h2><fmt:message key="page.homepage.films"/></h2>
            <ul>
                <c:forEach var="film" items="${requestScope.films}">
                    <li>
                        <a href="${pageContext.request.contextPath}/films?filmId=${film.id}">${film.name}</a><br>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div id="footer">
        <h2><fmt:message key="page.homepage.footer"/></h2>
    </div>
</div>
</body>
</html>