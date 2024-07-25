package goodorbad.goodorbad.repository;

import goodorbad.goodorbad.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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

//    //아이디 중복 확인 - 입력 받은 아이디를 데이터베이스에서 찾아서 동일한 아이디의 갯수 반환
//    public Long CountByUserId(String userId){
//        return em.createQuery("select count(*) from User u where u.userId=:ui",Long.class)
//                .setParameter("ui",userId)
//                .getSingleResult();
//    }

    public User findByUserId(String userId){
        try{
            return em.createQuery("select u from User u where u.userId=:ui",User.class)
                    .setParameter("ui",userId)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
}
