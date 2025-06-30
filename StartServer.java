package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import main.servlet.BookServlet;
import main.servlet.SearchServlet;
import main.servlet.FeatureServlet;

public class StartServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(9091);

        // Servlet handlers
        ServletContextHandler servletHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletHandler.setContextPath("/RailwayBooking");
        servletHandler.addServlet(SearchServlet.class, "/search");
        servletHandler.addServlet(BookServlet.class, "/book");
        servletHandler.addServlet(new ServletHolder(new FeatureServlet()), "/features");

        // Static image resource handler
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("C:/railwaybooking/src/main/webapp/images");
        resourceHandler.setDirectoriesListed(false);

        ContextHandler imageContext = new ContextHandler("/images");
        imageContext.setHandler(resourceHandler);

        // Combine handlers
        HandlerList handlers = new HandlerList();
        handlers.addHandler(imageContext);
        handlers.addHandler(servletHandler);
        server.setHandler(handlers);

        // Shutdown hook for JVM termination
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutdown hook triggered. Stopping server...");
                server.stop();
                System.exit(0);  // Force JVM to exit
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        // Auto-shutdown timer (e.g., 5 minutes)
        int autoKillTimeMs = 5 * 60 * 1000;
        new Thread(() -> {
            try {
                Thread.sleep(autoKillTimeMs);
                System.out.println("Auto-shutdown triggered after timeout. Stopping server...");
                server.stop();
                System.exit(0);  // Force kill JVM process
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // Start server
        server.start();
        System.out.println("Server running at: http://localhost:9091/RailwayBooking/search");
        System.out.println("Feature page at: http://localhost:9091/RailwayBooking/features");
        server.join();
    }
}
