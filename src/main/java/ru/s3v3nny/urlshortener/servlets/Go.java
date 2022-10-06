package ru.s3v3nny.urlshortener.servlets;

import lombok.SneakyThrows;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/go")
public class Go {
    JsonConverter converter = new JsonConverter();

    LinkUtils utils = new LinkUtils();
    LinkService service = new LinkService();

    @GET
    @Path("/{key}")
    @SneakyThrows
    public Response redirect(@PathParam("key") String key) {
        if (!utils.checkKey(key)) {
            var err = new Error();
            err.setMessage("Incorrect key");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(err))
                    .build();

        }


        String link = service.getLink(key);
        if (link == null) {
            var err = new Error();
            err.setMessage("Link doesn't exist in repository");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(err))
                    .build();
        } else {
            return Response
                    .temporaryRedirect(URI.create(link))
                    .status(Response.Status.MOVED_PERMANENTLY)
                    .build();
        }

    }
}
