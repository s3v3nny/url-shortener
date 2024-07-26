package ru.s3v3nny.urlshortener.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.s3v3nny.urlshortener.dto.*;
import ru.s3v3nny.urlshortener.entities.Link;
import ru.s3v3nny.urlshortener.repositories.LinkRepository;
import ru.s3v3nny.urlshortener.util.EntityConverter;
import ru.s3v3nny.urlshortener.util.LinkUtil;
import ru.s3v3nny.urlshortener.util.URLUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LinkService {

    private LinkUtil linkUtil;
    private LinkRepository repository;
    private URLUtil urlUtil;
    private EntityConverter converter;

    public ResponseDTO<ShortenedLinkDTO, ErrorDTO> create(LinkDTO linkDTO) {
        String url = linkDTO.getUrl();

        if(!urlUtil.validateURL(url)) {
            var err = new ErrorDTO();
            err.setMessage("Invalid URL");
            err.setCode(400);
            return new ResponseDTO<>(null, err);
        }

        Link link = repository.findByUrl(url);

        if(link != null) {
            return new ResponseDTO<>(converter.entityToShortenedLinkDto(link), null);
        }

        String key = linkUtil.generateKey();
        while(repository.findByKey(key) != null) {
            key = linkUtil.generateKey();
        }
        link = new Link();
        link.setKey(key);
        link.setUrl(url);
        link.setCreatedAt(Instant.now());
        link.setViews(0L);
        repository.save(link);
        return new ResponseDTO<>(converter.entityToShortenedLinkDto(link), null);
    }

    public ResponseDTO<List<ShortenedLinkDTO>, ErrorDTO> getLinks() {
        List<Link> links = repository.findAll();
        if (links.isEmpty()) {
            var err = new ErrorDTO();
            err.setMessage("No links found");
            err.setCode(400);
        }
        List<ShortenedLinkDTO> shortenedLinks = new ArrayList<>();
        for (var link : links) {
            ShortenedLinkDTO shortenedLink = converter.entityToShortenedLinkDto(link);
            shortenedLinks.add(shortenedLink);
        }
        return new ResponseDTO<>(shortenedLinks, null);
    }

    public ResponseDTO<MessageDTO, ErrorDTO> delete (String key) {
        Link link = repository.findByKey(key);
        if(link == null) {
            var err = new ErrorDTO();
            err.setMessage("Link not found");
            err.setCode(400);
            return new ResponseDTO<>(null, err);
        }
        repository.delete(link);
        var msg = new MessageDTO();
        msg.setMessage("Link deleted");
        return new ResponseDTO<>(msg, null);
    }
}
