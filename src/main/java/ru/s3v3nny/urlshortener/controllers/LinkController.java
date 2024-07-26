package ru.s3v3nny.urlshortener.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.s3v3nny.urlshortener.dto.*;
import ru.s3v3nny.urlshortener.services.LinkService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/links")
@AllArgsConstructor
public class LinkController {
    private LinkService linkService;

    @GetMapping
    public ResponseEntity<?> getLinks() {
        ResponseDTO<List<ShortenedLinkDTO>, ErrorDTO> response = linkService.getLinks();
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatusCode.valueOf(response.getError().getCode()));
        }
        return ResponseEntity.ok(response.getValue());
    }

    @PostMapping
    public ResponseEntity<?> createLink(@RequestBody LinkDTO linkDTO) {
        ResponseDTO<ShortenedLinkDTO, ErrorDTO> response = linkService.create(linkDTO);
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatusCode.valueOf(response.getError().getCode()));
        }
        return ResponseEntity.ok(response.getValue());
    }

    @DeleteMapping
    @RequestMapping("/{key}")
    public ResponseEntity<?> deleteLink(@PathVariable String key) {
        ResponseDTO<MessageDTO, ErrorDTO> response = linkService.delete(key);
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatusCode.valueOf(response.getError().getCode()));
        }
        return ResponseEntity.ok(response.getValue());
    }
}
