<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Riccio
  Date: 07.10.2019
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>Update Meal</title>
</head>
<body>
<div class="offset-1 col-2">

    <form action="meals" method="post">
        <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>

        <input type="hidden" name="mealId" value="<c:out value="${requestScope.mealId}" />" required/>

        <input type="datetime-local" name="datetime" value="<c:out value="${meal.dateTime}" />" required/>

        <input type="text" name="description" minlength="3" maxlength="25" placeholder="Описание" value="<c:out value="${meal.description}" />" required/>

        <input type="number" name="calories" min="10" max="5000" value="<c:out value="${meal.calories}" />" required>

        <input type="submit" name="action" value="Update"/>
    </form>
</div>
</body>
</html>
