package goodorbad.goodorbad.DTO;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

public class UserDTO {

    //회원 가입 - 받아 오는 요청 정보
    @Data
    public static class UserCreateRequest{
        @Schema(description = "아이디",example = "test_id")
        private String userId;
        @Schema(description = "이름",example = "test_name")
        private String name;
        @Schema(description = "비밀번호",example = "test_pw")
        private String password;
        @Schema(description = "이메일",example = "test_email")
        private String email;
    }

    //로그인 - 받아 오는 요청 정보
    @Data
    public static  class UserLoginRequest{
        @Schema(description = "아이디",example = "test_id")
        private String userId;
        @Schema(description = "비밀번호",example = "test_pw")
        private String password;
    }

    //회원 탈퇴 - 토큰 정보 받아옴
    @Data
    public static  class UserDeleteRequest{
        @Schema(description = "토큰")
        private String token;
    }

    //응답 정보
    @Data
    @AllArgsConstructor
    public static class UserResponse{ //응답
        @Schema(description = "아이디",example = "test_id")
        private String userId;
    }

    //이메일로 아이디 찾기
    @Data
    public static  class FindUserIdRequest{
        private String email;
        private String name;
    }

    //비밀번호 찾기
    @Data
    public static class FindPasswordRequest{
        private String email;
        private String userId;
    }

//    @Data
//    public static class ResetPasswordRequest{
//        private String token;
//        private String newPassword;
//    }
}
