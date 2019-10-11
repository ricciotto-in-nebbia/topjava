<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%--<%@ page import="ru.javawebinar.topjava.model.MealTo" %>--%>
<%--
  Created by IntelliJ IDEA.
  User: Riccio
  Date: 03.10.2019
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>Meals</title>
    <style>
        <%@include file="/WEB-INF/styles.css" %>
    </style>
</head>
<body>
<div>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <div>
        <h2>Таблица приема пищи</h2>
    </div>
    <div>
        <table class=\"table\">
            <tr>
                <th class="tableHead">Дата / Время</th>
                <th class="tableHead">Описание</th>
                <th class="tableHead">Калории</th>
                <th class="tableHeadButtons">update</th>
                <th class="tableHeadButtons">delete</th>
            </tr>
            <jsp:useBean id="mealTosList" scope="request" type="java.util.ArrayList"/>
            <c:forEach items="${mealTosList}" var="mealTo">
                <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr style="${mealTo.excess ? 'color: #dd2c07':'color: #366799'}">
                    <td><c:out value="${TimeUtil.outputFormat(mealTo.dateTime)}"/></td>
                    <td><c:out value="${mealTo.description}"/></td>
                    <td><c:out value="${mealTo.calories}"/></td>
                    <td><a href="meals?action=edit&mealId=<c:out value="${mealTo.id}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
                </tr>
            </c:forEach>
            </tr>
        </table>
    </div>
    <div class="offset-1 col-2">
        <form id="createMeal" action="meals" method="post">
            <input type="datetime-local" name="datetime" required>
            <input type="text" name="description" minlength="3" maxlength="25" placeholder="Описание" required>
            <input type="number" name="calories" min="10" max="5000" required placeholder="Количество калорий">
            <input type="reset" value="Reset">
            <input type="submit" value="Create" name="action">
        </form>
    </div>
</div>
</body>
</html>