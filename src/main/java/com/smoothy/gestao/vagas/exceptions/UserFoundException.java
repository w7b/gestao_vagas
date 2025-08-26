package com.smoothy.gestao.vagas.exceptions;

public class UserFoundException extends RuntimeException {
    public UserFoundException() {
        super("O usuario já existe");
    }
}
