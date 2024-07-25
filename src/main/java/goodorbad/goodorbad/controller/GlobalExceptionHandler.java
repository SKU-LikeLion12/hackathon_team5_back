package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.exception.IdNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IdNotFoundException.class) //MemberService Class 에서 throw IdNotFoundException
    public ResponseEntity<String> IdNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");
    }

    @ExceptionHandler(SignatureException.class) //JwtUtility Class 서명 예외 오류가 발생 했을 때
    public ResponseEntity<String> JwtSignature(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("서명 검증에 실패했습니다. 토큰이 올바르지 않습니다.");
    }
    @ExceptionHandler(ExpiredJwtException.class) //JwtUtility Class 토큰이 만료 되었을 때
    public ResponseEntity<String> JwtExpire(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
    }
}
