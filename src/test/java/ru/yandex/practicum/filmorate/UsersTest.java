package ru.yandex.practicum.filmorate;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UsersTest {

    @BeforeAll
    public static void createUser() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"dolore\",\n  \"name\": \"Nick Name\",\n  \"email\": \"mail@mail.ru\",\n  \"birthday\": \"1946-08-20\"\n}")
                .asString();
        assertEquals(201, response.getStatus());
    }

    @Test
    void createUserFailLogin() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"dolore ullamco\",\n  \"email\": \"yandex@mail.ru\",\n  \"birthday\": \"2446-08-20\"\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void createUserFailEmail() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"dolore ullamco\",\n  \"name\": \"\",\n  \"email\": \"mail.ru\",\n  \"birthday\": \"1980-08-20\"\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void createUserFailBirthDay() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"dolore\",\n  \"name\": \"\",\n  \"email\": \"test@mail.ru\",\n  \"birthday\": \"2446-08-20\"\n}")
                .asString();
        assertEquals(400, response.getStatus());
    }

    @Test
    void userUpdateUnknown() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"doloreUpdate\",\n  \"name\": \"est adipisicing\",\n  \"id\": 9999,\n  \"email\": \"mail@yandex.ru\",\n  \"birthday\": \"1976-09-20\"\n}")
                .asString();
        assertEquals(500, response.getStatus());
    }

    @Test
    void getAllUsers() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("http://localhost:8080/users")
                .header("Accept", "*/*")
                .asString();
        assertEquals(200, response.getStatus());
    }

    @Test
    void createUserWithEmptyName() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"common\",\n  \"email\": \"friend@common.ru\",\n  \"birthday\": \"2000-08-20\"\n}")
                .asString();
        assertEquals(201, response.getStatus());
    }

    @Test
    void userUpdate() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.put("http://localhost:8080/users")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .body("{\n  \"login\": \"doloreUpdate\",\n  \"name\": \"est adipisicing\",\n  \"id\": 1,\n  \"email\": \"mail@yandex.ru\",\n  \"birthday\": \"1976-09-20\"\n}")
                .asString();
        assertEquals(200, response.getStatus());
    }
}
