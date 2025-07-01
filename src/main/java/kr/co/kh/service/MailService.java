package kr.co.kh.service;

import kr.co.kh.model.payload.request.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Service
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            simpleMailMessage.setTo("song6115@naver.com");
            simpleMailMessage.setSubject("테스트 메일 제목");
            simpleMailMessage.setText("테스트 메일 내용");

            javaMailSender.send(simpleMailMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    public void sendMimeMessage(EmailRequest emailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            mimeMessageHelper.setTo(emailRequest.getMailTo());
            mimeMessageHelper.setSubject(emailRequest.getUsername() + "님");

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html lang='ko'>");
            sb.append("<body>");


            if (emailRequest.getMailType().equals("emailAuth")) {
                // ✅ 인증번호 생성
                String authCode = generateAuthCode();
                emailRequest.setAuthCode(authCode); // 필요한 경우 외부 전달용

                sb.append("<div style=\"margin:100px;\">");
                sb.append("<h1> PickGame 회원가입 인증번호 </h1>");
                sb.append("<div align=\"center\" style=\"border:1px solid black; padding:20px;\">");
                sb.append("<h3> </h3>");

                sb.append("<h1 style='color:blue;'>" + authCode + "</h1>"); // ✅ 인증번호 삽입
                sb.append("</div>");
                sb.append("</div>");

                log.info("발송된 인증번호: {}", authCode);
                // 비번 찾기 >>> 비번 자동 변경 후 메일 발송
                // 아이디를 db에서 찾는다 > 찾아 졌으면 비번을 랜덤하게 바꾸고 바꾼 비번을 메일로 날린다.

            } else if (emailRequest.getMailType().equals("passwordAuth")) {
                sb.append("<div style=\"margin:100px;\">");
                sb.append("<h1> 테스트 메일 </h1>");
                sb.append("<div align=\"center\" style=\"border:1px solid black;\">");
                sb.append("<h3> 테스트 메일 내용 </h3>");
                sb.append("</div>");
                sb.append("</div>");
            }

            sb.append("</body>");
            sb.append("</html>");

            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    // ✅ 인증번호 생성 메서드 추가
    private String generateAuthCode() {
        int length = 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }
}