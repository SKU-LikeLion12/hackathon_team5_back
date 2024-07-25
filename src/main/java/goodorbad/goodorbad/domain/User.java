package goodorbad.goodorbad.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true) //데이터베이스의 속성
    private String userId; //아이디
    private String password; //비밀 번호
    private String name; //이름
    private String email; //이메일

    public User(String userId, String password, String name, String email){
        this.userId=userId;
        this.setPassword(password);
        this.name=name;
        this.email=email;
    }

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    //PasswordEncoder는 인터페이스이지만, BCryptPasswordEncoder은 구현체 이기 때문에 객체를 생성할 수 있음

    public void setPassword(String password) {
        this.password = passwordEncoder.encode(password);
    }
    //encode() : 매개변수로 입력된 password를 암호화

    public boolean checkPassword(String rawPassword){
        return passwordEncoder.matches(rawPassword,this.password);
        //원시 비밀번호(rawPassword)와 암호화된 비빌번호(password)가 일치하는지 확인
        //이때, 원시 비밀번호를 암호화 하여 비교함
    }
}
