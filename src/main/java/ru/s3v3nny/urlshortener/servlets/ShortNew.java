package ru.s3v3nny.urlshortener.servlets;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

@Path("/")
public class ShortNew {
    JsonConverter converter = new JsonConverter();
    LinkUtils utils = new LinkUtils();
    LinkService service = new LinkService();

    @POST
    @Path("short-new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shortNew(String input) {
        String shortID;

        String link = converter.getLink(input).getLink();
        if (utils.checkURL(link)) {
            shortID = service.shortLink(link);
        } else {
            var err = new Error();
            err.setMessage("Incorrect link");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(converter.errorToJson(err))
                    .build();
        }

        Link linkObj = new Link();
        linkObj.setLink("http://localhost:8080/go/" + shortID);

        return Response
                .status(Response.Status.OK)
                .entity(converter.linkToJson(linkObj))
                .build();
    }
}
