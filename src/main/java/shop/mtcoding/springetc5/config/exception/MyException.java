package shop.mtcoding.springetc5.config.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException{
    private HttpStatus httpStatus;
    
    public MyException(String msg) {
        this(msg, HttpStatus.BAD_REQUEST);
    }

    public MyException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }

    
}
