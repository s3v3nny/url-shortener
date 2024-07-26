package ru.s3v3nny.urlshortener.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.s3v3nny.urlshortener.dto.ErrorDTO;
import ru.s3v3nny.urlshortener.dto.ResponseDTO;
import ru.s3v3nny.urlshortener.services.RedirectService;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class RedirectController {

    private RedirectService redirectService;

    @GetMapping
    @RequestMapping("/{key}")
    public ResponseEntity<?> redirect(@PathVariable String key) {
        ResponseDTO<String, ErrorDTO> response = redirectService.redirect(key);
        if (response.getError() != null) {
            return new ResponseEntity<>(response.getError(), HttpStatusCode.valueOf(response.getError().getCode()));
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", response.getValue());
        return new ResponseEntity<>(headers, HttpStatusCode.valueOf(302));
    }
}
