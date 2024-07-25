package goodorbad.goodorbad.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtUtility {
    private String secret="yourSecretKeyaokfosdfsoeifoifjosifjoisjdoifjosiejoijdovmldxggxxdgxd";
    private static final long EXPIRATION_TIME=1000L*60*60; //1시간

    //토큰생성
    public String generateToken(String userId){
        return Jwts.builder()
                .setSubject(userId) //setSubject()에 매개변수로 받은 userId를 넣어 토큰의 주체로 설정
                .setIssuedAt(new Date()) //토큰 발행시간
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME)) //토큰 만료시간
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                //secret 값을 바이트로 변환 후, 비밀키로 사용
                //jwt 서명 알고리즘 = HS512
                .compact();
    }

    //토큰 유효성 검사, 토큰에 관련된 사용자 또는 자원에 대한 정보를 제공
    public Claims validateToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8)) //검증을 위해 서명 키를 설정 secret
                    .parseClaimsJws(token) //제공된 토큰을 넣어서 파싱하고 검증
                    .getBody(); //검증이 성공하면 JWT에서 클레임을 추출
            return claims;
        }catch(Exception ex){ //모든 예외를 null 찍고 예외 문장 출력하는 게 아니라, 모든 예외를 던짐
            throw ex;
        }
//        }catch(SignatureException ex){
//            System.out.println("Invalid JWT signature"); //서명 검증 실패
//        }catch(ExpiredJwtException ex){
//            System.out.println("Expired JWT token"); //토큰 만료
//        }catch(Exception ex){
//            System.out.println("Invalid JWT token"); //기타 예외
//        }
//        return null;
    }
}
