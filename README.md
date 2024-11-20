# Artist Service API

This exercise was implemented based on the description in 
this [document](https://clarahub.notion.site/Backend-Challenge-10611d068efc80719ba8edbfbbfaa241). An 
attempt was made to cover most of the indicated requirements.

## Table of Contents
- [About The Project](#about-the-project)
- [Installation](#installation)
- [Usage](#usage)
- [Resources](#resources)

## About The Project

In this project you can see the use of the following technology stack:
1. Java 21
2. Spring Framework
3. Spring Boot
4. Spring Data JPA
5. Open Feign
6. Flyway
7. Postgres
8. JUnit
9. Mockito
10. Assertj
11. Jacoco
12. Testcontainers
13. Open API
14. Jib (Docker containers)
15. Others.

The exercise involves consuming a third-party API ([Discogs](https://www.discogs.com/)) from which information 
about artists and their respective albums can be obtained.

> **However!**
> 
> Due to the limited time I had I couldn't do much research on the discogs API. I didn't have the 
> opportunity to see the necessary APIs to obtain information from the artists (obtain several). 
> In fact I only have a couple of IDs which actually return information executing the next 
> endpoint: `https://api.discogs.com/artists/{artist_id}`
> 
> The IDs that can be used in the requests (if you want to see the interaction with Discogs API) are the following:
> 
> | ARTIST ID | ARTIST NAME |
> |:----------|:------------|
> | 262221    | The zombies |
> | 108713    | Nickelback  |
> | 103906    | Bit Shifter |
> Then for testing you can use those IDs in the requests to the endpoint mentioned above.
> 
> **Wait! I just realized that now requests with multiple IDs are working. Apparently service drops from time to time.**
> 
> By the way, the test data has the following range of identifiers.
> - **Artists:** from 1000 to 1028
> - **Albums:** from 2000 to 2109

### Features
1. **Artist Search and Discography Retrieval**:
    - Implement an endpoint that allows users to search for artists using the Discogs API.
    - Once an artist is selected, retrieve and store the artist's discography in a relational database (e.g., PostgreSQL, MySQL).
    - Ensure that the discography is stored in a way that allows for efficient querying and retrieval.

    
   Below is a flowchart to understand how the logic works.

   **SCENARIO**: The artist information for the specified `{{artistId}}` doesn't exist in the local database.

   ![Find artist by ID flowchart](/docs/images/find-artist-by-id-flowchart.png)

> <ol> 
>  <li>Request to the endpoint: `GET: /api/v1/artists/{artistId}`</li>
>  <li>Controller layer calls to service layer: `retrieveArtist`</li>
>  <li>Service layer verifies if the information exists in the local database through the repository: `findArtistById`</li>
>  <li>Although the scenario is focused on when the information is not found in the local database, it should be noted that at this point the return may or may not contain the required information.</li>
>    <ol>
>      <li>If the information already exists in the database, then the information is returned and the process ends.</li>
>      <li>If the information does not exist in the database, then it is searched through the client in the third-party API such as Discogs: `fetchArtist`</li>
>    </ol>
>  <li>Make a request to the third-party API</li>
>  <li>The information that is returned is converted to a DTO.</li>
>  <li>The information obtained from the third-party API is stored in the local database</li>
>  <li>Once stored in the database, it is returned as a result of the request</li>
> </ol>

2. **Advanced Artist Comparison**:
    - Implement an endpoint that allows users to compare two or more artists based on criteria such as:
        - Number of releases.
        - Active years (from the first release to the most recent).
        - Most common genres and tags associated with each artist.
    - Use the data stored in the database to perform these comparisons.

## Installation

### Prerequisites
- Docker
- Docker compose

### How to start the system
- Clone the repository:
```bash 
  git clone https://github.com/franco148/artist-service-api.git
```
- Go to the root folder of the repository: 
```bash
  cd artist-service-api
```
- Execute docker compose command:
```bash
  docker-compose up -d
```
- Verify that the **postgres-docker** and **artist-service** services are running:
```bash
  docker ps
```


## Usage
Once the services are running satisfactorily, open the browser of your choice and go to the 
following address: http://localhost:9191/swagger-ui/index.html. You can see the API documentation.

Make sure:
- Start on port 9191
- Select the **docker environment** in the servers dropdown.

![Artist Service API Documentation](/docs/images/open-api-docs.png)


## Resources

### Code coverage information.

![Jacoco code coverage](/docs/images/code-coverage-with-jacoco.png)

### Problems with sonarqube. 
My computer did not meet the system requirements required by SonarQube. The service could not start completely.

![SonarQube issues](/docs/images/sonarqube-not-starting.png)
