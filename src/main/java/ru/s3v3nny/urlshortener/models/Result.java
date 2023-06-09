package ru.s3v3nny.urlshortener.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<Value, Error> {
    Value value;
    Error error;
}
