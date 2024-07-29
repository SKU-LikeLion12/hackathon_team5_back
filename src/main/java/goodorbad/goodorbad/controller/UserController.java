package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.DTO.UserDTO;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원 가입
    @PostMapping("/add")
    public ResponseEntity<String> signUp(@RequestBody UserDTO.UserCreateRequest request){
        System.out.println("heheehehehehhe");
        User user=userService.signUp(request.getUserId(),request.getPassword(),request.getName(),request.getEmail());
        if(user==null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 존재하는 아이디 입니다."); //중복 아이디 체크

        String token=userService.login(request.getUserId(),request.getPassword());
        System.out.println("token"+token);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO.UserLoginRequest request){
        String token=userService.login(request.getUserId(), request.getPassword());
        if (token==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디, 비밀번호가 일치하지 않습니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    //회원 탈퇴
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserDTO.UserDeleteRequest request){
        userService.deleteUser(request.getToken());
    }

    //세션 로그인
    @PostMapping("/session-login")
    public ResponseEntity<String> sessionLogin(@RequestBody UserDTO.UserLoginRequest request, HttpSession session){
        String token = userService.login(request.getUserId(), request.getPassword());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디, 비밀번호가 일치하지 않습니다.");
        } else {
            session.setAttribute("userToken", token);
            session.setMaxInactiveInterval(3600); // 1시간 동안 세션 유지
            return ResponseEntity.status(HttpStatus.OK).body("세션 로그인 성공");
        }
    }

    //세션 로그아웃
    @PostMapping("/session-logout")
    public ResponseEntity<String> sessionLogout(HttpSession session){
        session.invalidate(); // 세션 무효화
        return ResponseEntity.status(HttpStatus.OK).body("세션 로그아웃 성공");
    }

}
