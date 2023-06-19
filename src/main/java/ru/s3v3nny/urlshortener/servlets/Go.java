package ru.s3v3nny.urlshortener.servlets;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.models.Result;
import ru.s3v3nny.urlshortener.services.LinkService;

import java.net.URI;

@Path("/go")
public class Go {

    LinkService service = new LinkService();

    @GET
    @Path("/{key}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirect(@PathParam("key") String key) {

        Result<Link, Error> result = service.getLink(key);

        if (result.getError() != null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(result.getError())
                    .build();

        }

        return Response
                .temporaryRedirect(URI.create(result.getValue().getLink()))
                .status(Response.Status.MOVED_PERMANENTLY)
                .build();

    }
}
