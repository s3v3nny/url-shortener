package ru.s3v3nny.urlshortener.servlets;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import java.sql.SQLException;


@Path("/admin")
public class Admin {
    JsonConverter converter = new JsonConverter();
    LinkUtils utils = new LinkUtils();
    LinkService service = new LinkService();


    @DELETE
    @Path("/{key}")
    @SneakyThrows
    public Response deleteLink(@PathParam("key") String key) {

        if (!utils.checkKey(key)) {
            var err = new Error();
            err.setMessage("Incorrect key");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(err))
                    .build();
        }

        if (service.deleteLink(key)) {
            return Response
                    .status(Response.Status.OK)
                    .build();
        } else {
            var err = new Error();
            err.setMessage("Link doesn't exist in repository");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(err))
                    .build();
        }
    }

    @GET
    @Path("/all")
    public Response showLinks() {
        String result = "";

        try {
            result = service.getLinks();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result == null) {
            var err = new Error();
            err.setMessage("Repository is null");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(err))
                    .build();
        } else {
            return Response
                    .status(Response.Status.OK)
                    .entity(result)
                    .build();
        }
    }
}
