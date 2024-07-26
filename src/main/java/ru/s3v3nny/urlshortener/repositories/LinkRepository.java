package ru.s3v3nny.urlshortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.s3v3nny.urlshortener.entities.Link;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByKey(String key);
    Link findByUrl(String url);
}
