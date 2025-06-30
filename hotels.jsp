<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Hotel Booking</title>
</head>
<body>
    <h1>Book Your Hotel</h1>
    <form action="FeatureServlet" method="post">
        <input type="hidden" name="action" value="bookHotel">
        
        <label for="roomType">Room Type:</label>
        <select name="roomType" required>
            <option value="Single">Single</option>
            <option value="Double">Double</option>
            <option value="Suite">Suite</option>
        </select><br><br>
        
        <label for="foodPreference">Food Preference:</label>
        <select name="foodPreference" required>
            <option value="Vegetarian">Vegetarian</option>
            <option value="Non-Vegetarian">Non-Vegetarian</option>
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
