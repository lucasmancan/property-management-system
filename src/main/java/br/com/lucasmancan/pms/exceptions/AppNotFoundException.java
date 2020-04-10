package br.com.lucasmancan.pms.exceptions;

public class AppNotFoundException extends AppException {
    public AppNotFoundException(String message) {
        super(message);
    }

    public AppNotFoundException() {
    }
}
