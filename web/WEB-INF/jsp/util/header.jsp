<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="header">
    <div>
        <div id="logout">
            <c:if test="${not empty sessionScope.user}">
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit"><fmt:message key="page.header.logout"/></button>
                </form>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <form action="${pageContext.request.contextPath}/login" method="get">
                    <button type="submit"><fmt:message key="page.header.login"/></button>
                </form>
            </c:if>
        </div>
        <div id="locale">
            <form action="${pageContext.request.contextPath}/locale" method="post">
                <button type="submit" name="lang" value="ru_RU">RU</button>
                <button type="submit" name="lang" value="en_US">EN</button>
            </form>
        </div>
    </div>
    <div id="links">
        <a href="${pageContext.request.contextPath}/films"><fmt:message key="page.header.films"/></a>
        <c:if test="${not empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/edit-film"><fmt:message key="page.header.createFilm"/></a>
            <a href="${pageContext.request.contextPath}/edit-person"><fmt:message key="page.header.createPerson"/></a>
        </c:if>
    </div>
</div>