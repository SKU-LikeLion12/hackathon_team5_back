package goodorbad.goodorbad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        String subject = "GoodOrBad 웹 비밀번호 재설정";
        String text = "비밀번호를 재설정하려면 다음 링크를 클릭하세요: \n" +
//        "http://localhost:3000/reset-password?token="+token;
        "https://team5.sku-sku.com/passwordFind?token="+token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); //수신자 이메일
        message.setSubject(subject); //제목
        message.setText(text); //내용

        mailSender.send(message);
    }
}
