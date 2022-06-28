package ru.s3v3nny.urlshortener;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import ru.s3v3nny.urlshortener.servlets.AdminServlet;
import ru.s3v3nny.urlshortener.servlets.GoServlet;
import ru.s3v3nny.urlshortener.servlets.ShortNewServlet;


public class ShortenerServer {

    private Server server;

    public void start(int port) throws Exception {
        server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[] {connector});

        ServletHandler servletHandler = new ServletHandler();

        servletHandler.addServletWithMapping(ShortNewServlet.class, "/short-new");
        servletHandler.addServletWithMapping(GoServlet.class, "/go/*");
        servletHandler.addServletWithMapping(AdminServlet.class, "/admin/*");

        server.setHandler(servletHandler);

        server.start();
    }

    void stop() throws Exception {
        server.stop();
    }
}
