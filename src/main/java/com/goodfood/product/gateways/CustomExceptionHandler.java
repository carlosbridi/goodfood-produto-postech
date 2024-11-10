package com.goodfood.product.gateways;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.goodfood.product.domain.NaoEncontradoException;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(NaoEncontradoException.class)
  public HttpEntity<Object> handleNotFoundException(final NaoEncontradoException ex) {
    HttpHeaders httpHeaders = new HttpHeaders();
    return new ResponseEntity<>(ex.getMessage(), httpHeaders, HttpStatus.NOT_FOUND);
  }
}
