package ru.yandex.practicum.filmorate.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
    @ExceptionHandler(NotFoundException.class)
    public void handleNoSuchElementFoundException(NotFoundException ex) {

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bad Request")
    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequestException(BadRequestException ex) {

    }
}
