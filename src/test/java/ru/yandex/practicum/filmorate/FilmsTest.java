package ru.yandex.practicum.filmorate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FilmsTest {

    @BeforeAll
    public static void createFilm() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"name\": \"nisi eiusmod\",\n  \"description\": \"adipisicing\",\n  \"releaseDate\": \"1967-03-25\",\n  \"duration\": 100\n}")
                .asString();
        assertEquals(201, response.getStatus());
    }

    @Test
    void getAllFilms() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("http://localhost:8080/films")
                .header("Accept", "*/*")
                .asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void createFilmFailName() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"name\": \"\",\n  \"description\": \"Description\",\n  \"releaseDate\": \"1900-03-25\",\n  \"duration\": 200\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void createFilmFailDescription() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"name\": \"Film name\",\n  \"description\": \"Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.\",\n    \"releaseDate\": \"1900-03-25\",\n  \"duration\": 200\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void createFilmFailReleaseDate() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"name\": \"Name\",\n  \"description\": \"Description\",\n  \"releaseDate\": \"2090-03-25\",\n  \"duration\": 200\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void createFilmFailDuration() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"name\": \"Name\",\n  \"description\": \"Descrition\",\n  \"releaseDate\": \"1980-03-25\",\n  \"duration\": -200\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void filmUpdateUnknown() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"id\": 9999,\n  \"name\": \"Film Updated\",\n  \"releaseDate\": \"1989-04-17\",\n  \"description\": \"New film update decription\",\n  \"duration\": 190,\n  \"rate\": 4\n}")
                .asString();
        assertEquals(500, response.getStatus());
    }

    @Test
    void filmUpdate() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put("http://localhost:8080/films")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"id\": 1,\n  \"name\": \"Film Updated\",\n  \"releaseDate\": \"1989-04-17\",\n  \"description\": \"New film update decription\",\n  \"duration\": 190,\n  \"rate\": 4\n}")
                .asString();
        assertEquals(200, response.getStatus());
    }
}
