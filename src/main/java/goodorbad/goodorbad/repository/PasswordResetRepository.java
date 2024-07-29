package goodorbad.goodorbad.repository;

import goodorbad.goodorbad.domain.PasswordReset;
import goodorbad.goodorbad.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@RequiredArgsConstructor
public class PasswordResetRepository {
    private final EntityManager em;

    public PasswordReset save(PasswordReset passwordReset){
        em.persist(passwordReset);
        return passwordReset;
    }

    public PasswordReset findByToken(String token){
        try {
            return em.createQuery("select pr from PasswordReset pr where pr.token = :t", PasswordReset.class)
                    .setParameter("t", token)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Date findDateByToken(String token){
        try{
            return em.createQuery("select pr.expiryDate from PasswordReset pr where pr.token=:t", Date.class)
                    .setParameter("t",token)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public User findUserByToken(String token){
        try{
            return em.createQuery("select pr.user from PasswordReset pr where pr.token=:t", User.class)
                    .setParameter("t",token)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public void deletePasswordReset(PasswordReset passwordReset){
        em.remove(passwordReset);
    }
}
