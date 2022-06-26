package ru.s3v3nny.urlshortener;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;


public class ShortenerServer {

    private Server server;

    public void start() throws Exception {
        server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.setConnectors(new Connector[] {connector});

        ServletHandler servletHandler = new ServletHandler();

        servletHandler.addServletWithMapping(ShortNew.class, "/short-new");
        servletHandler.addServletWithMapping(Redirect.class, "/go");
        servletHandler.addServletWithMapping(GetAllIDs.class, "/admin/all");

        server.setHandler(servletHandler);

        server.start();
    }

    void stop() throws Exception {
        server.stop();
    }
}
