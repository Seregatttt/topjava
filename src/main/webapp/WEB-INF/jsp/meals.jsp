<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="meal.title"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h3><a href="index.jsp">Home</a></h3>
    <hr/>
    <h2><spring:message code="meal.title"/></h2>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt><spring:message code="meal.FromDate"/></dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.DateTo"/></dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.FromTime"/></dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.ToTime"/></dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.Filter"/></button>
    </form>
    <hr/>
    <a href="meals?action=create"><spring:message code="meal.AddMeal"/></a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="meal.Date"/></th>
            <th><spring:message code="meal.Description"/></th>
            <th><spring:message code="meal.Calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-mealExcess="${meal.excess}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}"><spring:message code="meal.Update"/></a></td>
                <td><a href="meals?action=delete&id=${meal.id}"><spring:message code="meal.Delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>