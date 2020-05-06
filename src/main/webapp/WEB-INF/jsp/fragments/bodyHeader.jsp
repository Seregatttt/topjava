<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-dark bg-dark py-0">
    <div class="container">
        <a href="meals" class="navbar-brand"><img src="resources/images/icon-meal.png"> <spring:message
                code="app.title"/></a>
        <sec:authorize access="isAuthenticated()">
            <form:form class="form-inline my-2" action="logout" method="post">
                <sec:authorize access="hasRole('ADMIN')">
                    <a class="btn btn-info mr-1" href="users"><spring:message code="user.title"/></a>
                </sec:authorize>
                <a class="btn btn-info mr-1" href="profile">${userTo.name} <spring:message code="app.profile"/></a>
                <button class="btn btn-primary my-1" type="submit">
                    <span class="fa fa-sign-out"></span>
                </button>
            </form:form>
        </sec:authorize>
        <sec:authorize access="isAnonymous()">
            <form:form class="form-inline my-2" id="login_form" action="spring_security_check" method="post">
                <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                <button class="btn btn-success" type="submit">
                    <span class="fa fa-sign-in"></span>
                </button>
            </form:form>
        </sec:authorize>
        <%--        https://coderoad.ru/21683021/-requestScope-javax-servlet-forward-query_string-приходит-как-пустой
                    https://stackoverflow.com/questions/14096956/concepts-for-page-path-in-jsp-and-servlets
                    https://sprosi.pro/questions/1265726/webxml-404-perenapravit-na-servlet-kak-poluchit-originalnyiy-uri --%>
        <%--        <a href="${requestScope['javax.servlet.forward.request_uri']}?language=en">En</a>--%>
        <%--        <a href="${requestScope['javax.servlet.forward.request_uri']}?language=ru">Ru</a>--%>
        <%--        https://ru.stackoverflow.com/questions/494548/Как-сверстать-элемент-выбора-языка-на-сайте--%>


        <div class="dropdown">
            <div class="dropbtn-${pageContext.response.locale}">${pageContext.response.locale}</div>
            <div class="dropdown-content">
                <a href="${requestScope['javax.servlet.forward.request_uri']}?language=ru">RU</a>
                <a href="${requestScope['javax.servlet.forward.request_uri']}?language=en">EN</a>
            </div>
        </div>

    </div>
</nav>
