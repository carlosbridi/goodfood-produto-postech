package com.goodfood.product.domain;

public class NaoEncontradoException extends RuntimeException {

  private static final long serialVersionUID = -1104311020397886380L;

  public NaoEncontradoException(String message) {
    super(message);
  }

  public NaoEncontradoException(String message, Throwable e) {
    super(message, e);
  }

}
