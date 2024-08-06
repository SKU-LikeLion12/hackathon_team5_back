package goodorbad.goodorbad.service;

import goodorbad.goodorbad.domain.PasswordReset;
import goodorbad.goodorbad.domain.User;
import goodorbad.goodorbad.exception.EmailNotFoundException;
import goodorbad.goodorbad.exception.TokenExpiredException;
import goodorbad.goodorbad.repository.PasswordResetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional
public class PasswordResetService {
    private final UserService userService;
    private final PasswordResetRepository tokenRepository;
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1시간

    public String createPasswordResetToken(String email,String userId) {
        User user = userService.findByEmailAndUserId(email,userId);
//        if (!user.getUserId().equals(requestUser.getUserId())) {
//            throw new EmailNotFoundException();
//        }
            String token = UUID.randomUUID().toString(); //비밀번호 재설정을 위한 토큰 발행

            PasswordReset resetToken = new PasswordReset();
            resetToken.setToken(token);
            resetToken.setUser(user);
            resetToken.setExpiryDate(new Date(System.currentTimeMillis() + EXPIRATION_TIME));

            tokenRepository.save(resetToken);
            return token;


    }


    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        PasswordReset passwordReset = tokenRepository.findByToken(token);
        Date expiryDate=tokenRepository.findDateByToken(token);

        if (passwordReset==null || expiryDate.before(new Date())) {
            throw new TokenExpiredException();
        }

        User user = tokenRepository.findUserByToken(token);
//        if (user == null) {
//            throw new EmailNotFoundException();
//        }

        //비밀번호 재설정, 토큰 제거
        user.setPassword(newPassword);
        userService.save(user);
        tokenRepository.deletePasswordReset(passwordReset);

        return true;
    }
}
