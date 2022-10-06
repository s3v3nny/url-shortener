package ru.s3v3nny.urlshortener.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortenedLink {
    private String key;
    private String link;
}
