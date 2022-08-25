
package fr.m2i.javaapirest.tpuser.exception;


public class UserDaoErrorException extends RuntimeException {
    public UserDaoErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}