package br.com.lucasmancan.pms.controllers;

import br.com.lucasmancan.pms.exceptions.AppException;
import br.com.lucasmancan.pms.exceptions.AppNotFoundException;
import br.com.lucasmancan.pms.models.AppResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
@Log4j2
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private ObjectError error;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        log.log(Level.ERROR, "" + ex.getMessage(), ex);
        return new ResponseEntity(
                AppResponse.OOPS, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({HttpMessageConversionException.class, IllegalArgumentException.class})
    public final ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        log.log(Level.ERROR, "" + ex.getMessage(), ex);


        var response = new AppResponse();

        response.setMessage("The request is not valid.");
        return new ResponseEntity(
                response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.log(Level.ERROR, "" + e.getMessage(), e);
        return new ResponseEntity(
                AppResponse.OOPS, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(AppNotFoundException ex, WebRequest request) {

        var response = new AppResponse();

        response.setMessage(ex.getMessage() != null ? ex.getMessage() :"The resource was not found.");
        log.log(Level.ERROR, "" + ex.getMessage(), ex);

        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<Object> handleAppException(AppException ex, WebRequest request) {


        var response = new AppResponse();


        response.setMessage(ex.getMessage() != null ? ex.getMessage() :"Internal Error.");
        log.log(Level.ERROR, "" + ex.getMessage(), ex);

        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }


        var response = new AppResponse();
        response.setMessage("Argument validation failed.");
        response.setErrors(details);
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

}
