<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Food Menu Booking</title>
</head>
<body>
    <h1>Book Your Food</h1>
    <form action="FeatureServlet" method="post">
        <input type="hidden" name="action" value="bookFood">
        
        <label for="foodChoice">Food Choice:</label>
        <select name="foodChoice" required>
            <option value="Continental">Continental</option>
            <option value="South Indian">South Indian</option>
            <option value="North Indian">North Indian</option>
        </select><br><br>
        
        <label for="email">Email:</label>
        <input type="email" name="email" required><br><br>
        
        <button type="submit">Book Now</button>
    </form>

    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>
</body>
</html>
