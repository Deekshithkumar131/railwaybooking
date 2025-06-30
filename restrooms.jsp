<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Restroom Booking</title>
</head>
<body>
    <h1>Book Your Restroom</h1>
    <form action="FeatureServlet" method="post">
        <input type="hidden" name="action" value="bookRestroom">
        
        <label for="hallType">Hall Type:</label>
        <select name="hallType" required>
            <option value="Standard">Standard</option>
            <option value="Premium">Premium</option>
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
