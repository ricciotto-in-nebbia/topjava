<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Riccio
  Date: 07.10.2019
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Meal</title>
</head>
<body>
<div class="offset-1 col-2">
    <c:forEach var="meal" items="${requestScope.meals}">
        <form id="updateMeal" action="meals" method="post">
            <label><input class="tdInput" type="datetime-local" name="datetime" value="${meal.getDateTime()}"
                          required></label>
            <label><input class="tdInput" type="text" name="description" minlength="3" maxlength="25"
                          placeholder="Описание" value="${meal.getDescription()}" required></label>
            <label><input class="tdInput" type="number" name="calories" min="10" max="5000" required
                          value="${meal.getCalories()}"></label>

            <button type="reset" name="Reset">Reset</button>

            <button type="submit" name="actionMeal"
                    value="updateMeal?<c:out value="${meal.getId()}"/>">OK
            </button>

        </form>
    </c:forEach>
</div>
</body>
</html>
