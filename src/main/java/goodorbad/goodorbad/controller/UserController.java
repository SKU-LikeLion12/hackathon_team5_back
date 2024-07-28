package goodorbad.goodorbad.controller;

import goodorbad.goodorbad.DTO.UserDTO;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.service.EmailService;
import goodorbad.goodorbad.service.PasswordResetService;
import goodorbad.goodorbad.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.valves.rewrite.RewriteCond;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원 가입
    @Operation(summary = "회원가입",description = "아이디, 비밀번호, 이름, 이메일을 입력하고 회원가입 시도",tags={"user"},
            responses={@ApiResponse(responseCode = "201", description = "유저 생성 성공 후 토큰 반환"),
                    @ApiResponse(responseCode = "403",description = "중복 아이디로 인한 생성 실패")}
    )
    @PostMapping("/add")
    public ResponseEntity<String> signUp(@RequestBody UserDTO.UserCreateRequest request){
        User user=userService.signUp(request.getUserId(),request.getPassword(),request.getName(),request.getEmail());
        if(user==null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 존재하는 아이디 입니다."); //중복 아이디 체크

        String token=userService.login(request.getUserId(),request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    //로그인
    @Operation(summary = "로그인",description = "아이디, 비밀번호 입력하고 로그인 시도",tags={"user"},
            responses={@ApiResponse(responseCode = "200", description = "로그인 성공 후 토큰 반환"),
                    @ApiResponse(responseCode = "401",description = "아이디, 비밀번호 불일치로 로그인 실패")}
    )
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO.UserLoginRequest request){
        String token=userService.login(request.getUserId(), request.getPassword());
        if (token==null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 혹은 비밀번호가 일치하지 않습니다.");
        else
            return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    //회원 탈퇴
    @Operation(summary = "회원 탈퇴",description = "토큰 입력 하고 회원 탈퇴 시도",tags={"user"},
            responses={@ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")}
    )
    @DeleteMapping("/delete")
    public void deleteUser(@RequestBody UserDTO.UserDeleteRequest request){
        userService.deleteUser(request.getToken());
    }

    //아이디 찾기 - 이름, 이메일
    @GetMapping("/find/userId")
    public String findUserIdByEmail(@RequestBody UserDTO.FindUserIdRequest request){
        return userService.findUserIdByEmailAndName(request.getEmail(),request.getName());
    }



}
