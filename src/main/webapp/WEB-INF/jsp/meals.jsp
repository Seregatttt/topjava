<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список еды </title>
</head>
<body>
<div class=container3>
    <h1>Список еды</h1>
</div>
<table class=container5 border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>uuid</th>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Превышение</th>
        <th colspan=3>Action</th>
    </tr>
    <c:forEach items="${meals}" var="mealTo">
        <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
        <tr bgcolor=${mealTo.excess == true ? "red":"green"}>
            <td>${mealTo.uuid}</td>
            <td>${mealTo.dateTime.format( DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td>${mealTo.excess}</td>
            <td><a href="meals?action=view&mealsId=${mealTo.uuid}">View</a></td>
            <td><a href="meals?action=edit&mealsId=${mealTo.uuid}">Update</a></td>
            <td><a href="meals?action=delete&mealsId=${mealTo.uuid}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<div class=container3>
    <p><a href="meals?action=insert">Add Meals</a></p>
</div>
</body>
</html>