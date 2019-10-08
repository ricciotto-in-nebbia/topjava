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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <div>
            <form id="filter" action="meals">
                <div class="offset-1 col-2">
                    <label for="startDate">От даты</label>
                    <input class="inputForm" type="date" name="startDate" id="startDate">
                </div>
                <div class="col-2">
                    <label for="endDate">До даты</label>
                    <input class="inputForm" type="date" name="endDate" id="endDate">
                </div>
                <div class="offset-2 col-2">
                    <label for="startTime">От времени</label>
                    <input class="inputForm" type="time" name="startTime" id="startTime">
                </div>
                <div class="col-2">
                    <label for="endTime">До времени</label>
                    <input class="inputForm" type="time" name="endTime" id="endTime">
                </div>
                <p><input type="reset" value="Reset" name="Reset"> <input type="submit" value="Ok" name="Ok"><br></p>
            </form>
        </div>
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

            <jsp:useBean id="mealTosMap" scope="request" type="java.util.Map"/>
            <c:forEach items="${mealTosMap.entrySet()}" var="mealTo">
                <jsp:useBean id="mealTo" type="java.util.Map.Entry"/>

                <tr style="${mealTo.value.excess ? 'color: #dd2c07':'color: #366799'}">
                    <td><c:out value="${TimeUtil.outputFormat(mealTo.value.dateTime)}"/></td>
                    <td><c:out value="${mealTo.value.description}"/></td>
                    <td><c:out value="${mealTo.value.calories}"/></td>

                    <td><a href="meals?action=edit&mealId=<c:out value="${mealTo.key}"/>">Update</a></td>
                    <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.key}"/>">Delete</a></td>
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
