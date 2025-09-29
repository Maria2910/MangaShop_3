package ru.itis.mangashop.exceptions;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException() {
        super();
    }

    public WeakPasswordException(String message) {
        super(message);
    }

    public WeakPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
