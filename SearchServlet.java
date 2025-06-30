package main.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

    static class Train {
        String id;
        String name;
        String initialDestination;
        String finalDestination;
        String arrivalTime;
        int totalSeats;
        int bookedSeats;
        String distance;
        List<String> bookedSeatNumbers;
        List<Section> sections;

        Train(String id, String name, String initialDestination, String finalDestination, String arrivalTime, int totalSeats, String distance) {
            this.id = id;
            this.name = name;
            this.initialDestination = initialDestination;
            this.finalDestination = finalDestination;
            this.arrivalTime = arrivalTime;
            this.totalSeats = totalSeats;
            this.bookedSeats = 0;
            this.distance = distance;
            this.bookedSeatNumbers = new ArrayList<>();
            this.sections = new ArrayList<>();
            sections.add(new Section("AC", 40));
            sections.add(new Section("Non-AC", 30));
            sections.add(new Section("Luxury", 20));
            sections.add(new Section("Side-by-Side", 30));
        }

        int getAvailableSeats() {
            return totalSeats - bookedSeats;
        }

        boolean isSeatBooked(String seatNumber) {
            return bookedSeatNumbers.contains(seatNumber);
        }

        void bookSeat(String seatNumber) {
            if (!isSeatBooked(seatNumber)) {
                bookedSeatNumbers.add(seatNumber);
                bookedSeats++;
            }
        }

        List<Section> getSections() {
            return sections;
        }
    }

    static class Section {
        String name;
        int seats;
        List<String> bookedSeats;

        Section(String name, int seats) {
            this.name = name;
            this.seats = seats;
            this.bookedSeats = new ArrayList<>();
        }

        int getAvailableSeats() {
            return seats - bookedSeats.size();
        }

        boolean isSeatBooked(String seatNumber) {
            return bookedSeats.contains(seatNumber);
        }

        void bookSeat(String seatNumber) {
            if (!isSeatBooked(seatNumber)) {
                bookedSeats.add(seatNumber);
            }
        }
    }

    List<Train> trains = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        trains.add(new Train("101", "Express A", "CityA", "CityB", "10:00 AM", 100, "250 km"));
        trains.add(new Train("102", "Express B", "CityC", "CityD", "02:30 PM", 80, "300 km"));
        trains.add(new Train("103", "Express C", "CityA", "CityE", "06:15 PM", 120, "500 km"));
        trains.add(new Train("104", "Express D", "CityF", "CityG", "08:45 AM", 90, "150 km"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        showHeader(out);

        switch (action != null ? action : "") {
            case "bookPage":
                showBookingPage(out, request.getParameter("trainId"));
                break;
            case "searchResult":
                showSearchResultPage(out, request);
                break;
            case "enquiry":
                showEnquiryPage(out);
                break;
            case "contact":
                showContactForm(out);
                break;
            default:
                showSearchFormPage(out);
                break;
        }

        showTrainAnimationAndInfo(out);
        out.println("</body></html>");
    }

    private void showHeader(PrintWriter out) {
        out.println("<html><head><title>Railway Booking</title></head><body>");
        out.println("<div style='background-color:#004080;color:white;padding:15px;display:flex;justify-content:space-between;'>");
        out.println("<div style='font-size:24px;font-weight:bold;'>Welcome to RBS</div>");
        out.println("<div>");
        out.println("<a href='/RailwayBooking/search?action=' style='color:white;margin-left:20px;'>Search</a>");
        out.println("<a href='/RailwayBooking/search?action=enquiry' style='color:white;margin-left:20px;'>Enquiry</a>");
        out.println("<a href='/RailwayBooking/search?action=contact' style='color:white;margin-left:20px;'>Contact</a>");
        out.println("</div></div>");
        

        // Icons for the features (you can change the icon URLs to your own)
        out.println("<html>");
        out.println("<head>");
        
        out.println("<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\">"); // Font Awesome CDN
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; padding: 20px; background-color:hsl(0, 83.30%, 44.70%); }");
        out.println(".feature-icons { display: flex; justify-content: space-around; margin-top: 400px; }");
        out.println(".feature-icons a { text-decoration: none; color: black; padding: 10px; border-radius: 5px; background-color:hsl(0, 20.00%, 73.50%); transition: background-color 0.3s; }");
        
        out.println(".feature-icons i { font-size: 40px; }"); // Set icon size
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        

        out.println("<div class=\"feature-icons\">");
        out.println("<style>");
        out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
        out.println("html, body { height: 100vh; overflow-y: hidden; background-color: #e0fafd; }");
        out.println("</style>");

        out.println("<a href=\"http://localhost:9091/RailwayBooking/features?type=hotel\" title=\"Hotel\">"
                + "<i class=\"fas fa-bed\"></i><br>Hotel</a>");

        out.println("<a href=\"http://localhost:9091/RailwayBooking/features?type=lounges\" title=\"Lounges\">"
                + "<i class=\"fas fa-couch\"></i><br>Lounges</a>");

        out.println("<a href=\"http://localhost:9091/RailwayBooking/features?type=restrooms\" title=\"Restrooms\">"
                + "<i class=\"fas fa-restroom\"></i><br>Restrooms</a>");

        out.println("<a href=\"http://localhost:9091/RailwayBooking/features?type=food\" title=\"Food\">"
                + "<i class=\"fas fa-utensils\"></i><br>Food</a>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }
     

    private void showSearchFormPage(PrintWriter out) {
        
    
    out.println("<style>");
    out.println(".search-bottom-container {");
    out.println("  position: fixed;");
    out.println("  bottom: 70px;");
    out.println("  left: 0;");
    out.println("  width: 100%;");
    out.println("  background:rgb(170, 163, 238);");
    out.println("  padding: 20px;");
    out.println("  box-shadow: 0 -2px 10px rgba(0,0,0,0.1);");
    out.println("  z-index: 999;");
    out.println("}");

    out.println(".search-box-title {");
    out.println("  text-align: center;");
    out.println("  font-size: 18px;");
    out.println("  font-weight: bold;");
    out.println("  color: #004080;");
    out.println("  margin-bottom: 10px;");
    out.println("}");

    out.println(".search-form {");
    out.println("  display: flex;");
    out.println("  justify-content: center;");
    out.println("  align-items: center;");
    out.println("  gap: 15px;");
    out.println("  max-width: 900px;");
    out.println("  margin: 0 auto;");
    out.println("}");

    out.println(".search-form input[type='text'] {");
    out.println("  padding: 10px;");
    out.println("  border: 1px solid #ccc;");
    out.println("  border-radius: 5px;");
    out.println("  font-size: 14px;");
    out.println("  flex: 1;");
    out.println("}");

    out.println(".search-form button {");
    out.println("  padding: 10px 20px;");
    out.println("  background-color: #004080;");
    out.println("  color: white;");
    out.println("  border: none;");
    out.println("  border-radius: 5px;");
    out.println("  cursor: pointer;");
    out.println("  font-size: 14px;");
    out.println("}");

    out.println(".search-form button:hover {");
    out.println("  background-color: #0066cc;");
    out.println("}");
    out.println("</style>");

    out.println("<div class='search-bottom-container'>");
    out.println("  <div class='search-box-title'>Search Train</div>");
    out.println("  <form class='search-form' method='get' action='/RailwayBooking/search'>");
    out.println("    <input type='hidden' name='action' value='searchResult'/>");

    out.println("    <input type='text' name='initialDestination' placeholder='Initial Destination' required/>");
    out.println("    <input type='text' name='finalDestination' placeholder='Final Destination' required/>");

    out.println("    <button type='submit'>Search</button>");
    out.println("  </form>");
    out.println("</div>");





    }

    private void showSearchResultPage(PrintWriter out, HttpServletRequest request) {
        out.println("<h2>Search Results</h2>");
        String initial = request.getParameter("initialDestination");
        String fin = request.getParameter("finalDestination");
        boolean found = false;

        for (Train train : trains) {
            if (train.initialDestination.equalsIgnoreCase(initial) && train.finalDestination.equalsIgnoreCase(fin)) {
                found = true;
                out.println("<p><strong>Train ID:</strong> " + train.id + "<br>");
                out.println("<strong>Name:</strong> " + train.name + "<br>");
                out.println("<strong>Arrival:</strong> " + train.arrivalTime + "<br>");
                out.println("<form method='get' action='/RailwayBooking/search'>");
                out.println("<input type='hidden' name='action' value='bookPage'/>");
                out.println("<input type='hidden' name='trainId' value='" + train.id + "'/>");
                out.println("<input type='submit' value='Book This Train'/></form><hr></p>");
            }
        }
        if (!found) out.println("<p>No trains found matching your route.</p>");
    }

    private void showEnquiryPage(PrintWriter out) {
        out.println("<h2>Train Enquiry</h2>");
        for (Train train : trains) {
            out.println("<p><strong>ID:</strong> " + train.id + ", <strong>Name:</strong> " + train.name +
                    ", <strong>From:</strong> " + train.initialDestination + ", <strong>To:</strong> " + train.finalDestination +
                    ", <strong>Arrival:</strong> " + train.arrivalTime + ", <strong>Distance:</strong> " + train.distance + "</p>");
        }
    }

    private void showContactForm(PrintWriter out) {
        out.println("<h2>Contact Us</h2>");
        out.println("<form method='post' action='mailto:railwaybooking6301@gmail.com' enctype='text/plain'>");
        out.println("Your Email: <input type='email' name='from' required/><br><br>");
        out.println("Message:<br><textarea name='body' rows='5' cols='40'></textarea><br><br>");
        out.println("<input type='submit' value='Send Message'/>");
        out.println("</form>");
    }

    private void showBookingPage(PrintWriter out, String trainId) {
        Train selectedTrain = null;
        for (Train train : trains) {
            if (train.id.equals(trainId)) {
                selectedTrain = train;
                break;
            }
        }

        if (selectedTrain != null) {
            out.println("<h2>Book Train: " + selectedTrain.name + "</h2>");
            out.println("<p>From: " + selectedTrain.initialDestination + " To: " + selectedTrain.finalDestination + "</p>");
            out.println("<p>Arrival Time: " + selectedTrain.arrivalTime + " | Distance: " + selectedTrain.distance + "</p>");
            out.println("<form method='post' action='/RailwayBooking/book'>");
            out.println("<input type='hidden' name='trainId' value='" + selectedTrain.id + "'/>");
            for (Section section : selectedTrain.getSections()) {
                out.println("<h3>" + section.name + " (" + section.getAvailableSeats() + " available)</h3>");
                for (int i = 1; i <= section.seats; i++) {
                    String seat = section.name.charAt(0) + String.valueOf(i);
                    if (section.isSeatBooked(seat)) {
                        out.println("<button disabled>" + seat + "</button>");
                    } else {
                        out.println("<button name='selectedSeats' value='" + seat + "'>" + seat + "</button>");
                    }
                }
            }
            out.println("<br>Email: <input type='email' name='email' required/><br><br>");
            out.println("<input type='submit' value='Confirm Booking'/></form>");
        } else {
            out.println("<p>Train not found.</p>");
        }
    }
    private void showTrainAnimationAndInfo(PrintWriter out) {
    out.println("<style>");
    out.println("body { margin: 0; overflow-x: hidden; font-family: Arial, sans-serif; background-color: #e0f7fa; }");

    // Smaller background image
    // Outer wrapper
    out.println(".landmark-wrapper {");
    out.println("  width: 100%;bottom: 473px;");
    out.println("  height: 3in;");
    out.println("  overflow: hidden;");
    out.println("  position: relative;");
    out.println("  z-index: 1;");
    out.println("}");

    // Inner container with animation
    out.println(".landmark-scroll {");
    out.println("  display: flex;");
    out.println("  width: 100%;");  // because we have 2 images
    out.println("  animation: scroll-landmarks 30s linear infinite;");
    out.println("}");

    // Each image
    out.println(".landmark-image {");
    out.println("  width: 100%;");
    out.println("  height: 3in;");
    out.println("  flex-shrink: 0;");
    out.println("}");

    out.println("@keyframes scroll-landmarks {");
    out.println("  0% { transform: translateX(0); }");
    out.println("  100% { transform: translateX(-50%); }");
    out.println("}");

  

    // Train container
    out.println(".train-container { height: 400px; position: relative; background-color: transparent; }");
    out.println(".train { position: absolute; bottom: 850px; left: 100vw; width: 500px; height: 100px; animation: move-train 10s linear infinite; z-index: 3; }");

    // Engine, compartments, wheels (same as before)
    out.println(".engine-front { background: #222; width: 80px; height: 46px; border-radius: 0.5rem 0 0 0.5rem; position: absolute; bottom: 14px; animation: body-upDown 0.3s infinite; }");
    out.println(".chimney { background: #555; width: 12px; height: 30px; position: absolute; top: -30px; left: 30px; border-radius: 4px; }");
    out.println(".smoke, .smoke-2, .smoke-3, .smoke-4 { position: absolute; width: 20px; height: 20px; background: rgba(255,255,255,0.6); border-radius: 50%; animation: smoke 2s infinite ease-in-out; }");
    out.println(".smoke-2 { animation-delay: 0.3s; top: -20px; left: 2px; }");
    out.println(".smoke-3 { animation-delay: 0.6s; top: -30px; left: -2px; }");
    out.println(".smoke-4 { animation-delay: 0.9s; top: -40px; left: 4px; }");

    out.println(".engine-body { background: red; width: 55px; height: 70px; position: absolute; bottom: 14px; left: 80px; animation: body-upDown 0.3s 0.1s infinite; }");
    out.println(".engine-window { width: 20px; height: 20px; background: lightblue; border-radius: 4px; position: absolute; top: 20px; left: 15px; }");
    out.println(".engine-dome { position: absolute; width: 55px; height: 15px; background: red; border-radius: 50% 50% 0 0; top: -2px; left: 80px; animation: body-upDown 0.3s 0.1s infinite; }");

    out.println(".connector { position: absolute; width: 12px; height: 6px; background: yellow; bottom: 35px; z-index: 2; }");
    out.println(".connector-1 { left: 135px; } .connector-2 { left: 240px;width:7; } .connector-3 { left: 340px;width:7; }");

    out.println(".compartment { background: #005f99; width: 80px; height: 50px; position: absolute; bottom: 14px; animation: body-upDown 0.3s infinite; display: flex; justify-content: space-around; align-items: center; padding: 0 5px; }");
    out.println(".compartment-one { left: 150px; } .compartment-two { left: 250px; animation-delay: 0.1s; } .compartment-three { left: 350px; animation-delay: 0.2s; }");
    out.println(".compartment-window { width: 18px; height: 20px; background: lightblue; border-radius: 4px; }");
    out.println(".compartment-dome { position: absolute; width: 89px; height: 10px; background:rgb(0, 71, 153); border-radius: 50% 50% 0 0; animation: body-upDown 0.3s infinite; }");
    out.println(".dome-one { top: -48px; left: 152px; } .dome-two { top: -48px; left: 252px; animation-delay: 0.1s; } .dome-three { top: -48px; left: 352px; animation-delay: 0.2s; }");

    out.println(".wheel-holder { position: absolute; bottom: 0; height: 28px; width: 100%; }");
    out.println(".wheel { position: absolute; width: 28px; height: 28px; background: orange; border-radius: 50%; animation: rotate 1s linear infinite; border: 3px solid rgba(0, 0, 0, 0.6); overflow: hidden; }");
    out.println(".wheel::before { content: ''; position: absolute; top: 50%; left: 50%; width: 8px; height: 8px; background: #222; border-radius: 50%; transform: translate(-50%, -50%); z-index: 2; }");
    out.println(".wheel::after { content: ''; position: absolute; top: 50%; left: 50%; width: 20px; height: 2px; background: #111; transform: translate(-50%, -50%) rotate(45deg); transform-origin: center; z-index: 1; }");

    out.println(".wheel-1 { left: 5px; } .wheel-2 { left: 45px; } .wheel-3 { left: 87px; } .wheel-4 { left: 157px; } .wheel-5 { left: 195px; }");
    out.println(".wheel-6 { left: 257px; } .wheel-7 { left: 295px; } .wheel-8 { left: 357px; } .wheel-9 { left: 395px; }");

    out.println(".track { position: absolute; bottom: 835px; width: 300%; height: 40px; background: #996633; z-index: 2; overflow: hidden; }");
    out.println(".rail { position: absolute; width: 100%; height: 4px; background: silver; z-index: 3; }");
    out.println(".rail-left { top: 8px; } .rail-right { top: 28px; }");

    out.println(".sleeper-container { position: absolute; top: 0; width: 100%; height: 100%; display: flex; gap: 12px; padding-left: 10px; box-sizing: border-box; z-index: 1; flex-wrap: wrap; }");
    out.println(".sleeper { width: 8px; height: 40px; background: #4b3621; flex-shrink: 0; }");

    out.println("@keyframes move-train { 0% { transform: translateX(0); } 100% { transform: translateX(-2500px); } }");
    out.println("@keyframes rotate { 100% { transform: rotate(-360deg); } }");
    out.println("@keyframes body-upDown { 0%, 100% { transform: translateY(0); } 50% { transform: translateY(-2px); } }");
    out.println("@keyframes smoke { 0% { opacity: 0.6; transform: scale(0.5) translateY(0); } 100% { opacity: 0; transform: scale(1.5) translateY(-50px); } }");
    out.println("</style>");

    // HTML structure
    out.println("<div class='landmark-wrapper'>");
    out.println("  <div class='landmark-scroll'>");
    out.println("    <img src='http://localhost:9091/images/6.png' class='landmark-image' alt='Landmarks'/>");
    out.println("    <img src='http://localhost:9091/images/6.png' class='landmark-image' alt='Landmarks'/>");
    out.println("  </div>");
    out.println("</div>");


    out.println("<div class='train-container'>");
    out.println("  <div class='train'>");
    out.println("    <div class='engine-front'><div class='chimney'>");
    out.println("      <div class='smoke'></div><div class='smoke smoke-2'></div><div class='smoke smoke-3'></div><div class='smoke smoke-4'></div>");
    out.println("    </div></div>");
    out.println("    <div class='engine-body'><div class='engine-window'></div></div>");
    out.println("    <div class='engine-dome'></div>");
    out.println("    <div class='connector connector-1'></div><div class='connector connector-2'></div><div class='connector connector-3'></div>");
    out.println("    <div class='compartment compartment-one'><div class='compartment-window'></div><div class='compartment-window'></div></div>");
    out.println("    <div class='compartment compartment-two'><div class='compartment-window'></div><div class='compartment-window'></div></div>");
    out.println("    <div class='compartment compartment-three'><div class='compartment-window'></div><div class='compartment-window'></div></div>");
    out.println("    <div class='wheel-holder'>");
    out.println("<div class='compartment-dome dome-one'></div><div class='compartment-dome dome-two'></div><div class='compartment-dome dome-three'></div>");
    for (int i = 1; i <= 9; i++) {
        out.println("<div class='wheel wheel-" + i + "'></div>");
    }
    out.println("    </div></div>");

    // Track
    out.println("  <div class='track'><div class='rail rail-left'></div><div class='rail rail-right'></div>");
    out.println("    <div class='sleeper-container'>");
    for (int i = 0; i < 76; i++) {
        out.println("<div class='sleeper'></div>");
    }
    out.println("    </div></div>");
    out.println("</div>");
    

}

}

        
    