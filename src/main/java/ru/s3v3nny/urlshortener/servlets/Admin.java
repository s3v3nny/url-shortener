package ru.s3v3nny.urlshortener.servlets;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.models.Result;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;

@Path("/admin")
public class Admin {
    JsonConverter converter = new JsonConverter();
    LinkService service = new LinkService();


    @DELETE
    @Path("/{key}")
    @SneakyThrows
    public Response deleteLink(@PathParam("key") String key) {

        Result<Link, Error> result = service.deleteLink(key);

        if (result.getError() != null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(result.getError()))
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .build();
    }

    @GET
    @Path("/all")
    @SneakyThrows
    public Response showLinks() {

        Result<String, Error> result = service.getLinks();

        if (result.getError() != null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(result.getError()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(result.getValue())
                    .build();
        }
    }
}
