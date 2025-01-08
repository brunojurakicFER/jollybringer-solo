package hr.fer.jollybringer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleAllExceptions(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        // Check for the specific error condition
        if (response.getStatus() == 999 && "None".equals(request.getAttribute("jakarta.servlet.error.message"))) {
            response.sendRedirect("/dashboard");
        } else {
            // Handle other exceptions
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        }
    }
}