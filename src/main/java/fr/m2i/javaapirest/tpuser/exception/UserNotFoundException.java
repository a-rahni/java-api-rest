
package fr.m2i.javaapirest.tpuser.exception;

/**
 *
 * @author rahni
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message){
        super(message);
    }
    
    
}
