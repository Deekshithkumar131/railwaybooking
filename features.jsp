<!-- featureIcons.jsp -->
<style>
.feature-icons {
    display: flex;
    justify-content: center;
    gap: 40px;
    padding: 20px;
    background-color: #f2f2f2;
    font-size: 24px;
    font-family: Arial, sans-serif;
}
.feature-icons div {
    cursor: pointer;
    text-align: center;
    border: 1px solid #ccc;
    border-radius: 10px;
    padding: 10px 20px;
    background-color: white;
    box-shadow: 2px 2px 10px rgba(0,0,0,0.1);
    transition: transform 0.2s, background-color 0.2s;
}
.feature-icons div:hover {
    background-color: #e0e0e0;
    transform: scale(1.05);
}
</style>

<div class="feature-icons">
    <div onclick="location.href='/RailwayBooking/features?action=hotels'">
        🏨<br>Hotels
    </div>
    <div onclick="location.href='/RailwayBooking/features?action=restrooms'">
        🛏️<br>Resting Rooms
    </div>
    <div onclick="location.href='/RailwayBooking/features?action=lounges'">
        🛋️<br>Lounge
    </div>
    <div onclick="location.href='/RailwayBooking/features?action=foodMenu'">
        🍽️<br>Food Menu
    </div>
</div>
