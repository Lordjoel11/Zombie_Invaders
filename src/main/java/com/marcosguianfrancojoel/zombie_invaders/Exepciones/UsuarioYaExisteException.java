package com.marcosguianfrancojoel.zombie_invaders.Exepciones;

public class UsuarioYaExisteException extends RuntimeException {
    public UsuarioYaExisteException(String message) {
        super(message);
    }
}
