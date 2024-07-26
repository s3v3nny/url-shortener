package ru.s3v3nny.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedLinkDTO {
    private String shortenedUrl;
    private String url;
    private Instant createdAt;
    private Long views;
}
