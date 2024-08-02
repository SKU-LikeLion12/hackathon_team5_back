package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //토큰
    @ExceptionHandler(SignatureException.class) //JwtUtility Class 서명 예외 오류가 발생 했을 때
    public ResponseEntity<String> JwtSignature(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("서명 검증에 실패했습니다. 토큰이 올바르지 않습니다.");
    }
    @ExceptionHandler(ExpiredJwtException.class) //JwtUtility Class 토큰이 만료 되었을 때
    public ResponseEntity<String> JwtExpire(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> Exception(Exception e){
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("토큰이 유효하지 않습니다.");
//    }

    //디비에 없음
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> IdNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 회원입니다.");
    }

    @ExceptionHandler(EmailNotFoundException.class) //UserService에서 이메일이 없을 때
    public ResponseEntity<String> EmailNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 이메일입니다.");
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> TokenExpired(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료 되었습니다.");
    }

    @ExceptionHandler(NameOrEmailNotFoundException.class)
    public ResponseEntity<String> IdOrEmailNotFoundException(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이름 혹은 이메일이 존재하지 않습니다.");
    }

    //다이어리 수정
    @ExceptionHandler(DateNotFoundException.class)
    public ResponseEntity<String> DateNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("일치하는 날짜가 없습니다.");
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    public ResponseEntity<String> DiaryNotFound(Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("다이어리를 찾을 수 없습니다.");
    }

}
