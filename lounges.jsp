<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lounge Booking</title>
</head>
<body>
    <h1>Book Your Lounge</h1>
    <form action="FeatureServlet" method="post">
        <input type="hidden" name="action" value="bookLounge">
        
        <label for="loungeType">Lounge Type:</label>
        <select name="loungeType" required>
            <option value="Business">Business</option>
            <option value="Luxury">Luxury</option>
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
