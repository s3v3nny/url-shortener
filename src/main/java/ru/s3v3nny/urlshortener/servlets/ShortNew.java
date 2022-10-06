package ru.s3v3nny.urlshortener.servlets;

import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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