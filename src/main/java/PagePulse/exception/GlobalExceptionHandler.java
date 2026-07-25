package PagePulse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleValidationException(MethodArgumentNotValidException e){

        Map<String,String> error = new HashMap<>();

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .filter(fieldError -> fieldError.getCode().equals("NotBlank"))
                .map(fieldError -> fieldError.getDefaultMessage())
                .findFirst()
                .orElse(
                        e.getBindingResult()
                                .getFieldError()
                                .getDefaultMessage()
                );

        error.put("message", message);
        error.put("status","400");

        return error;
    }
}