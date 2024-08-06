package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.DTO.UserDTO;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.service.EmailService;
import goodorbad.goodorbad.service.PasswordResetService;
import goodorbad.goodorbad.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PasswordResetController {
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;
    private final UserService userService;

    //비밀번호 찾기 - 아이디, 이메일 입력
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserDTO.FindPasswordRequest request) {
        //사용자 아이디, 이메일 검사 필요
//        User requestUser=userService.findByUserId(request.getUserId());
        String token = passwordResetService.createPasswordResetToken(request.getEmail(),request.getUserId());
        emailService.sendPasswordResetEmail(request.getEmail(), token);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserDTO.ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 성공적으로 변경되었습니다.");
    }
}
