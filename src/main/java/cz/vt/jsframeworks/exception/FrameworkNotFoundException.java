package cz.vt.jsframeworks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FrameworkNotFoundException extends RuntimeException {
    
    public FrameworkNotFoundException(Long id) {
        super("The framework id '" + id + "' does not exist in our records");
    }

    public FrameworkNotFoundException(String string) {
        super("The framework with string '" + string + "' in name does not exist in our records");
    }
    
}
