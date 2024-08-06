package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.exception.EmailNotFoundException;
import goodorbad.goodorbad.exception.IdNotFoundException;
import goodorbad.goodorbad.exception.NameOrEmailNotFoundException;
import goodorbad.goodorbad.exception.TokenExpiredException;
import goodorbad.goodorbad.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SignatureException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final JwtUtility jwtUtility;
    private final UserRepository userRepository;

    public User tokenToMember(String token){ //token을 넣으면 해당하는 userId를 찾아서
       return userRepository.findByUserId(jwtUtility.validateToken(token).getSubject());//userId 반환
       // return userRepository.findByUserId(jwtUtility.validateToken(token.substring(7)).getSubject());//userId 반환
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
        if (member==null) throw new IdNotFoundException(); //아이디가 존재 하지 않을 때
        return member;
    }

    //이메일로 아이디 찾기
    public String findUserIdByEmailAndName(String email,String name){
        String userId=userRepository.findUserIdByEmailAndName(email,name);
        if(userId==null) throw new NameOrEmailNotFoundException(); //이메일 혹은 이름이 존재 하지 않을 때
        return userId;
    }

    //이메일로 유저 객체 찾기
    public User findByEmail(String email){
        User user=userRepository.findByEmail(email);
        if (user==null) throw new EmailNotFoundException(); //이메일에 해당하는 유저가 없을 때
        return user;
    }

    public User findByEmailAndUserId(String email,String userId){
        User user=userRepository.findByEmailAndUserId(email,userId);
        if (user==null) throw new IdNotFoundException();
        return user;
    }

    public void save(User user){
        userRepository.save(user);
    }
}
