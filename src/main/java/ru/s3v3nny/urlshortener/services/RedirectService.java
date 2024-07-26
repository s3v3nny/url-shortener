package ru.s3v3nny.urlshortener.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.s3v3nny.urlshortener.dto.ErrorDTO;
import ru.s3v3nny.urlshortener.dto.ResponseDTO;
import ru.s3v3nny.urlshortener.entities.Link;
import ru.s3v3nny.urlshortener.repositories.LinkRepository;
import ru.s3v3nny.urlshortener.util.EntityConverter;

@Service
@AllArgsConstructor
@Slf4j
public class RedirectService {

    private LinkRepository linkRepository;
    private EntityConverter converter;

    @GetMapping
    @RequestMapping("/{key}")
    public ResponseDTO<String, ErrorDTO> redirect (@PathVariable String key) {
        Link link = linkRepository.findByKey(key);
        if (link == null) {
            var err = new ErrorDTO();
            err.setMessage("Link not found");
            err.setCode(404);
            return new ResponseDTO<>(null, err);
        }
        link.setViews(link.getViews() + 1);
        linkRepository.save(link);
        String url = converter.entityToShortenedLinkDto(link).getUrl();
        return new ResponseDTO<>(url, null);
    }
}
