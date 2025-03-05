<%--
  Created by IntelliJ IDEA.
  User: maximg
  Date: 28.01.2025
  Time: 22:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="util/locale.jsp" %>
<html>
<head>
    <title>
        <fmt:message key="page.adEdit.title"/>
    </title>
    <style><%@ include file="../css/style.css" %></style>
</head>
<body>
<div id="container">
    <%@ include file="util/header.jsp" %>

    <form action="${pageContext.request.contextPath}/edit-person" method="post">
        <label for="full_name"><fmt:message key="page.adEdit.fullName"/>
            <input type="text" name="full_name" id="full_name" required>
        </label><br>
        <label for="birthday"><fmt:message key="page.adEdit.birthday"/>
            <input type="date" name="birthday" id="birthday" required>
        </label><br>
        <button type="submit"><fmt:message key="page.adEdit.button.submit"/></button>
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
