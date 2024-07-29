package goodorbad.goodorbad.repository;

import goodorbad.goodorbad.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    //사용자 생성 == 회원 가입
    public User save(User user){
        em.persist(user);
        return user;
    }

    //사용자 삭제 == 회원 탈퇴
    public void deleteUser(User user){
        em.remove(user);
    }

    //아이디로 사용자 찾기
    public User findByUserId(String userId){
        try{
            return em.createQuery("select u from User u where u.userId=:ui",User.class)
                    .setParameter("ui",userId)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    //이메일로 아이디 찾기
    public String findUserIdByEmailAndName(String email,String name){
        try{
            return em.createQuery("select u.userId from User u where u.email=:e and u.name=:n", String.class)
                    .setParameter("e",email)
                    .setParameter("n",name)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    //비밀번호 찾기
    public User findByEmail(String email){
        try{
            return em.createQuery("select u from User u where u.email=:e",User.class)
                    .setParameter("e",email)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}
