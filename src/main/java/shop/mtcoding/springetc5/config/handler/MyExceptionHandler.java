package shop.mtcoding.springetc5.config.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.springetc5.config.exception.MyException;

@RestControllerAdvice
public class MyExceptionHandler {
    
    @ExceptionHandler(MyException.class)
    public ResponseEntity<?> error1(MyException e){
        return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
    }
}
