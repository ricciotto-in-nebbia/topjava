<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

            <jsp:useBean id="mealToList" scope="request" type="java.util.List"/>
            <c:forEach items="${mealToList}" var="mealTo">
                <jsp:useBean id="mealTo" type="ru.javawebinar.topjava.model.MealTo"/>

                <tr style="${mealTo.excess ? 'color: #dd2c07':'color: #366799'}">
                    <td><c:out value="${TimeUtil.outputFormat(mealTo.dateTime)}"/></td>
                    <td><c:out value="${mealTo.description}"/></td>
                    <td><c:out value="${mealTo.calories}"/></td>
                    <td class="td-btn-updateDelete">
                        <form action="meals" method="post">
                            <button type="submit" name="actionMeal"
                                    value="updateJSP?<c:out value="${mealTo.id}"/>" class="btn-updateDelete">edit
                            </button>
                        </form>
                    </td>
                    <td class="td-btn-updateDelete">
                        <form action="meals" method="post">
                            <button type="submit" name="actionMeal"
                                    value="deleteMeal?<c:out value="${mealTo.id}"/>" class="btn-updateDelete">
                                delete
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>


            </tr>
        </table>
    </div>
    <div class="offset-1 col-2">
        <form id="createMeal" action="meals" method="post">
            <label><input class="tdInput" type="datetime-local" name="datetime" required></label>
            <label><input class="tdInput" type="text" name="description" minlength="3" maxlength="25"
                          placeholder="Описание" required></label>
            <label><input class="tdInput" type="number" name="calories" min="10" max="5000" required
                          placeholder="Количество калорий"></label>
            <input type="reset" value="Reset">
            <input type="submit" value="Create" name="actionMeal">
        </form>
    </div>
</div>
</body>
</html>
