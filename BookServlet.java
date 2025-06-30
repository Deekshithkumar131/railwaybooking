package main.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class BookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processBooking(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processBooking(req, resp);
    }

    private void processBooking(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String trainId = req.getParameter("trainId");
        String restroomId = req.getParameter("restroomId");
        String userEmail = req.getParameter("email");
        String type = req.getParameter("type"); // For feature bookings

        if (userEmail == null || userEmail.isEmpty()) {
            userEmail = "deekshithshiva8@gmail.com";
        }

        resp.setContentType("text/html");

        try {
            if (trainId != null) {
                sendConfirmationEmail(userEmail, "Train Booking Confirmation",
                        "✅ Your booking for Train ID " + trainId + " is confirmed.");
                resp.getWriter().write("<p>✅ Train booking email sent for Train ID: " + trainId + "</p>");
            } else if (restroomId != null) {
                sendConfirmationEmail(userEmail, "Restroom Booking Confirmation",
                        "🚻 Your restroom booking (Hall ID " + restroomId + ") is confirmed.");
                resp.getWriter().write("<p>🚻 Restroom booking email sent for Hall ID: " + restroomId + "</p>");
            } else if ("hotel".equals(type)) {
                String roomType = req.getParameter("roomType");
                String food = req.getParameter("food");
                sendConfirmationEmail(userEmail, "Hotel Booking Confirmation",
                        "🏨 Your hotel booking is confirmed.\nRoom Type: " + roomType + "\nFood: " + food);
                resp.getWriter().write("<p>🏨 Hotel booking email sent.</p>");
            } else if ("restroom".equals(type)) {
                String hallType = req.getParameter("hallType");
                String bedType = req.getParameter("bedType");
                sendConfirmationEmail(userEmail, "Restroom Booking Confirmation",
                        "🛌 Your restroom is booked.\nHall: " + hallType + "\nBed: " + bedType);
                resp.getWriter().write("<p>🛌 Restroom feature booking email sent.</p>");
            } else if ("lounge".equals(type)) {
                String sofaType = req.getParameter("sofaType");
                sendConfirmationEmail(userEmail, "Lounge Booking Confirmation",
                        "🛋️ Your lounge booking is confirmed.\nSofa: " + sofaType);
                resp.getWriter().write("<p>🛋️ Lounge booking email sent.</p>");
            } else if ("foodMenu".equals(type)) {
                String menuType = req.getParameter("menuType");
                sendConfirmationEmail(userEmail, "Food Order Confirmation",
                        "🍽️ Your food order is confirmed.\nMenu: " + menuType);
                resp.getWriter().write("<p>🍽️ Food menu order email sent.</p>");
            } else {
                resp.getWriter().write("<p>❌ No valid booking details provided.</p>");
            }
        } catch (Exception e) {
            resp.getWriter().write("<p>❌ Error sending booking email: " + e.getMessage() + "</p>");
        }
    }

    private void sendConfirmationEmail(String to, String subject, String body) throws MessagingException {
        final String from = "railwaybooking6301@gmail.com";
        final String password = "bejubtlkqkobcecq";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        System.out.println("✅ Email sent to " + to + " with subject: " + subject);
    }
}
