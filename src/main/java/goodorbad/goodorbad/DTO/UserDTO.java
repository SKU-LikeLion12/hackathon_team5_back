package goodorbad.goodorbad.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserDTO {

    //회원 가입 - 받아 오는 요청 정보
    @Data
    public static class UserCreateRequest{
        private String userId;
        private String name;
        private String password;
        private String email;
    }

    //로그인 - 받아 오는 요청 정보
    @Data
    public static  class UserLoginRequest{
        private String userId;
        private String password;
    }

    //회원 탈퇴 - 토큰 정보 받아옴
    @Data
    public static  class UserDeleteRequest{
        private String token;
    }

    //응답 정보
    @Data
    @AllArgsConstructor
    public static class UserResponse{ //응답
        private String userId;
    }


}
