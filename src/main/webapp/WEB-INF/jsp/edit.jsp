<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
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
<h2>${typeWork}</h2>

<form method="POST" action='meals' name="frmMeal">
    <dl class="cf">
        <dt></dt>
        <dd><input
                type="hidden" name="uuid"
                value="<c:out value="${meal.uuid}" />"/>
        <dt>datetime :</dt>
        <dd><input type="text" name="datetime"
                   value="${f:formatLocalDateTime(meal.dateTime)}"/>
        </dd>
        <dt>description :</dt>
        <dd><input
                type="text" name="description"
                value="<c:out value="${meal.description}" />"/>
        </dd>
        <dt>calories :</dt>
        <dd><input
                type="text" name="calories"
                value="<c:out value="${meal.calories}" />"/>
        </dd>
        <dt></dt>
        <dd>
            <input type="submit" value="Submit"/>
        </dd>
    </dl>
</form>

</body>
</html>