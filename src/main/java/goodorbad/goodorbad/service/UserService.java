package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final JwtUtility jwtUtility;
    private final UserRepository userRepository;

    public User tokenToMember(String token){ //token을 넣으면 해당하는 userId를 찾아서
        return userRepository.findByUserId(jwtUtility.validateToken(token).getSubject());//userId 반환
    }

    //회원 탈퇴
    public boolean deleteUser(String token){
        User user=tokenToMember(token);
        if (user==null) return false;
        userRepository.deleteUser(user);
        return true;
    }

    //로그인
    public String login(String userId, String password){
        User user=userRepository.findByUserId(userId);
        if(user!=null &&user.checkPassword(password)){
            return jwtUtility.generateToken(userId);
        }
        return null;
    }

    public User findByUserId(String userId){
        User member= userRepository.findByUserId(userId);
        if (member==null) return null; //만약 찾으려고한 userId가 존재하지 않는다면
        return member;
    }

    public User signUp(String userId,String passwd,String nickname){
        User member=userRepository.findByUserId(userId); //userId인 member 객체가 있나 검사
        if(member!=null) return null; //있다면 이미 있는 userId 이므로 등록시키면 안됨
        return userRepository.save(new User(userId,passwd,nickname)); //데이터베이스에 새로운 member 객체를 저장
    }
}
