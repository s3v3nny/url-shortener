# URL Shortener
Simple URL shortener on Java
## Structure
#### API
* GET /api/v1/links - Get All Links  
  Successful response example:
```json
[
  {
    "shortenedUrl": "<baseurl>/f8s9a01d",
    "url": "https://google.com",
    "createdAt": "2024-07-26T11:01:25.309473Z",
    "views": 999
  }
]
```
* POST /api/v1/links - Short new URL  
  Expected body:
```json
{
    "url": "https://google.com"
}
```
Successful response example:
```json
{
    "shortenedUrl": "<baseurl>/f8s9a01d",
    "url": "https://google.com",
    "createdAt": "2024-07-26T11:01:25.309473Z",
    "views": 999
}
```
* DELETE /api/v1/links/{key} - Delete link by key  
  Successful response example:
```json
{
    "message": "Link deleted"
}
```
#### Redirect
* GET /{key} - Redirect to URL

#### Error
* Response with error example:
```json
{
    "code": 400,
    "message": "Invalid URL"
}
```

## Stack
Java 17, Spring Boot 3, Spring Web, Spring Data JPA, PostgreSQL

