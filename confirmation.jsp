<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html>
<head>
    <title>Booking Confirmation</title>
    <style>
        .confirmation-container {
            font-family: Arial, sans-serif;
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            border: 2px solid #4CAF50;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        .confirmation-container h2 {
            text-align: center;
            color: #333;
        }
        .seat-info {
            font-size: 18px;
            margin-bottom: 15px;
        }
        .seat-status {
            font-weight: bold;
        }
        .section {
            margin: 20px 0;
        }
        .available {
            color: green;
        }
        .booked {
            color: red;
        }
        .seat {
            display: inline-block;
            width: 30px;
            height: 30px;
            margin: 3px;
            text-align: center;
            line-height: 30px;
            font-size: 12px;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<%-- Retrieving request attributes --%>
<%
    String seatNumber = (String) request.getAttribute("seatNumber");
    String section = (String) request.getAttribute("section");
    String email = (String) request.getAttribute("email");
    
    // Seat booking count per section (retrieved from the servlet)
    int bookedAC = (int) request.getAttribute("bookedAC");
    int bookedNonAC = (int) request.getAttribute("bookedNonAC");
    int bookedLuxury = (int) request.getAttribute("bookedLuxury");
    int bookedGeneral = (int) request.getAttribute("bookedGeneral");
    int bookedFullLuxury = (int) request.getAttribute("bookedFullLuxury");

    // Total available seats per section
    int availableAC = (int) request.getAttribute("availableAC");
    int availableNonAC = (int) request.getAttribute("availableNonAC");
    int availableLuxury = (int) request.getAttribute("availableLuxury");
    int availableGeneral = (int) request.getAttribute("availableGeneral");
    int availableFullLuxury = (int) request.getAttribute("availableFullLuxury");
%>

<div class="confirmation-container">
    <h2>Booking Confirmation</h2>

    <p class="seat-info"><strong>Seat Number:</strong> <%= seatNumber %></p>
    <p class="seat-info"><strong>Section:</strong> <%= section %></p>
    <p class="seat-info"><strong>Email:</strong> <%= email %></p>

    <h3>Seat Availability</h3>

    <div class="section">
        <p class="seat-status">AC Section:</p>
        <p class="<%= bookedAC > 0 ? "booked" : "available" %>">Booked Seats: <%= bookedAC %> / 30 | Available Seats: <%= availableAC %> / 30</p>
    </div>

    <div class="section">
        <p class="seat-status">Non-AC Section:</p>
        <p class="<%= bookedNonAC > 0 ? "booked" : "available" %>">Booked Seats: <%= bookedNonAC %> / 30 | Available Seats: <%= availableNonAC %> / 30</p>
    </div>

    <div class="section">
        <p class="seat-status">Luxury Section:</p>
        <p class="<%= bookedLuxury > 0 ? "booked" : "available" %>">Booked Seats: <%= bookedLuxury %> / 30 | Available Seats: <%= availableLuxury %> / 30</p>
    </div>

    <div class="section">
        <p class="seat-status">General Section:</p>
        <p class="<%= bookedGeneral > 0 ? "booked" : "available" %>">Booked Seats: <%= bookedGeneral %> / 30 | Available Seats: <%= availableGeneral %> / 30</p>
    </div>

    <div class="section">
        <p class="seat-status">Full Luxury Section:</p>
        <p class="<%= bookedFullLuxury > 0 ? "booked" : "available" %>">Booked Seats: <%= bookedFullLuxury %> / 30 | Available Seats: <%= availableFullLuxury %> / 30</p>
    </div>

    <br>
    <p><strong>Thank you for booking with us! Your seat is confirmed.</strong></p>
    <p>If you have any questions, feel free to contact us at <%= email %>.</p>
</div>

</body>
</html>
