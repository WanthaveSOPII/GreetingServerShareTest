package com.example.greetingserver.config;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PermissionHandler {
    @ExceptionHandler(value = { SignatureException.class })
    @ResponseBody
    public Exception authorizationException(SignatureException e){
        return new Exception("验证出错");
    }
}