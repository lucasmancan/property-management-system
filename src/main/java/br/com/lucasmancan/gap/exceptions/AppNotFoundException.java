package br.com.lucasmancan.gap.exceptions;

public class AppNotFoundException extends Exception {
    public AppNotFoundException(String message) {
        super(message);
    }

    public AppNotFoundException() {
    }
}
