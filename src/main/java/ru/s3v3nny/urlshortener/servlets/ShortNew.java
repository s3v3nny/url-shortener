package ru.s3v3nny.urlshortener.servlets;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.models.Result;
import ru.s3v3nny.urlshortener.services.LinkService;

@Path("/")
public class ShortNew {
    LinkService service = new LinkService();

    @POST
    @Path("short-new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shortNew(String input) {

        Result<Link, Error> result = service.createNewShortUrl(input);

        if (result.getValue() == null) {
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
}
