package com.bukup.vetclinic.controller.exceptionhandler;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ViewExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException e, Model model) {
        log.info(e.getClass().getSimpleName() + ": " + e.getMessage());
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error/error";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    public String handleEntityExistsException(EntityExistsException e, Model model) {
        log.info(e.getClass().getSimpleName() + ": " + e.getMessage());
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error/error";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e, Model model) {
        log.info(e.getClass().getSimpleName() + ": " + e.getMessage());
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error/error";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e, Model model) {
        log.info(e.getClass().getSimpleName() + ": " + e.getMessage());
        model.addAttribute("exceptionMessage", e.getMessage());
        return "error/error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String handleAnyException(Exception e) {
        log.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return "error/500";
    }
}
