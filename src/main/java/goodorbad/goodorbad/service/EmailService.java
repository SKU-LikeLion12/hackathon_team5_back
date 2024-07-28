package goodorbad.goodorbad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "GoodOrBad 웹 비밀번호 재설정";
        String text = "비밀번호를 재설정하려면 다음 링크를 클릭하세요: \n" +
                "http://localhost:8080/reset-password.html?token=" + token; //서버 배포 후 해당 도메인으로 변경

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
