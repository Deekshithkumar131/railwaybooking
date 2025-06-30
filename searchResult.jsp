<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Train Search Results</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { text-align: center; color: #333; }
        .train-details { margin: 20px 0; padding: 15px; border: 1px solid #ddd; border-radius: 5px; }
        .train-details h3 { margin-top: 0; color: #333; }
        .book-button { padding: 8px 12px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; }
        .book-button:hover { background-color: #45a049; }
    </style>
</head>
<body>

<h1>Train Search Results</h1>

<c:if test="${not empty trains}">
    <h2>Matching Trains</h2>
    <c:forEach var="train" items="${trains}">
        <div class="train-details">
            <h3>${train.name}</h3>
            <p><strong>Train ID:</strong> ${train.id}</p>
            <p><strong>Departure:</strong> ${train.initialDestination} → ${train.finalDestination}</p>
            <p><strong>Arrival Time:</strong> ${train.arrivalTime}</p>
            <form method="post" action="book">
                <input type="hidden" name="trainId" value="${train.id}" />
                <label>Select Seat:</label>
                <input type="text" name="seatNumbers" placeholder="e.g. AC-12" required />
                <br><label>Email:</label>
                <input type="email" name="email" required />
                <br><button class="book-button" type="submit">Book This Train</button>
            </form>
        </div>
    </c:forEach>
</c:if>

<c:if test="${empty trains}">
    <p>No trains found matching your criteria.</p>
</c:if>

<br>
<a href="search">Back to Search</a>

</body>
</html>
