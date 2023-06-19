package ru.s3v3nny.urlshortener.servlets;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import ru.s3v3nny.urlshortener.models.*;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.LinkService;

import java.util.List;

@Path("/admin")
public class Admin {
    LinkService service = new LinkService();


    @DELETE
    @Path("/{key}")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLink(@PathParam("key") String key) {

        Result<Message, Error> result = service.deleteLink(key);

        if (result.getError() != null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(result.getError())
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(result.getValue())
                .build();
    }

    @GET
    @Path("/all")
    @SneakyThrows
    @Produces(MediaType.APPLICATION_JSON)
    public Response showLinks() {

        Result<List, Error> result = service.getLinks();

        if (result.getError() != null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(result.getError())
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(result.getValue())
                    .build();
        }
    }
}
