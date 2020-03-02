<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Просмотр</title>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
</head>
<body>
<h3><a href="/topjava">Home</a></h3>
<hr>
<h2>View</h2>
    <dl class="cf">
        <dt>uuid :</dt>
        <dd><input
                type="text" name="uuid" readonly
                value="<c:out value="${meal.uuid}" />"/>
        <dt>datetime :</dt>
        <dd><input type="text" name="datetime" readonly
                   value="${meal.dateTime.format( DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))}"/>
        </dd>
        <dt>description :</dt>
        <dd><input
                type="text" name="description" readonly
                value="<c:out value="${meal.description}" />"/>
        </dd>
        <dt>calories :</dt>
        <dd><input
                type="text" name="calories" readonly
                value="<c:out value="${meal.calories}" />"/>
        </dd>
        <dt></dt>
        <dd>
            <a href="meals">Ok</a>
        </dd>
    </dl>
</body>
</html>