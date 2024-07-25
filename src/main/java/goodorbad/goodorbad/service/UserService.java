package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.exception.IdNotFoundException;
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

    //회원 가입
    public User signUp(String userId,String passwd,String name, String email){
        User user=userRepository.findByUserId(userId);
        if(user!=null) return null; //중복 아이디 검토
        return userRepository.save(new User(userId,passwd,name,email)); //데이터베이스에 새로운 member 객체를 저장
    }

    //회원 탈퇴
    public boolean deleteUser(String token){
        User user=tokenToMember(token);
        if (user==null) return false;
        userRepository.deleteUser(user);
        return true;
    }

    //로그인 - 토큰 발급
    public String login(String userId, String password){
        User user=userRepository.findByUserId(userId);
        if(user!=null &&user.checkPassword(password)){
            return jwtUtility.generateToken(userId);
        }
        return null; //로그인 실패
    }

    public User findByUserId(String userId){
        User member= userRepository.findByUserId(userId);
        if (member==null) throw new IdNotFoundException(); //만약 찾으려고한 userId가 존재하지 않는다면
        return member;
    }
}
