package ru.s3v3nny.urlshortener;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.s3v3nny.urlshortener.servlets.*;


public class ShortenerServer {

    private Server server;

    public void start(int port) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        server = new Server();
        server.setHandler(context);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        ServletHolder holder = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        holder.setInitOrder(0);

        holder.setInitParameter("jersey.config.server.provider.classnames", Admin.class.getCanonicalName());
        holder.setInitParameter("jersey.config.server.provider.classnames", Go.class.getCanonicalName());
        holder.setInitParameter("jersey.config.server.provider.classnames", ShortNew.class.getCanonicalName());

        server.start();
    }

    void stop() throws Exception {
        server.stop();
    }
}
