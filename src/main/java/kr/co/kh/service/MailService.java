package kr.co.kh.service;

import kr.co.kh.model.payload.request.EmailRequest;
import kr.co.kh.model.vo.MemberVO;
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
    private final EmailAuthService emailAuthService;


    public MailService(JavaMailSender javaMailSender, EmailAuthService emailAuthService) {
        this.javaMailSender = javaMailSender;
        this.emailAuthService = emailAuthService;


    }

    public void sendSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // http://localhost:8080/api/join/mail?to=수신자이메일@gmail.com&subject=인증메일&mailType=emailAuth
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


            mimeMessageHelper.setTo(emailRequest.getMailTo());//
            //mimeMessageHelper.setSubject("[PickGame] 아래 인증번호를 입력해주세요");

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html lang='ko'>");
            sb.append("<body>");
            log.info("메일 수신자: {}", emailRequest.getMailTo());


            if (emailRequest.getMailType().equals("emailAuth")) {
                mimeMessageHelper.setSubject("[PickGame] 아래 회원가입 인증번호를 입력해주세요");
                // ✅ 인증번호 생성
                String authCode = generateAuthCode();
                emailRequest.setAuthCode(authCode); //
                // 필요한 경우 외부 전달용

                sb.append("<!DOCTYPE html>");
                sb.append("<html lang='ko'>");
                sb.append("<head><meta charset='UTF-8'></head>");
                sb.append("<body>");
                sb.append("<div style=\"max-width:600px; margin:50px auto; padding:30px; border:1px solid #e0e0e0; border-radius:8px; font-family:Arial,sans-serif; background-color:#f9f9f9;\">");
                sb.append("<div style=\"font-size:22px; color:#333; margin-bottom:20px; text-align:center;\">[PickGame] 회원가입 인증번호</div>");
                sb.append("<div style=\"background-color:#fff; padding:20px; text-align:center; border-radius:6px; border:1px dashed #007BFF;\">");
                sb.append("<div style=\"font-size:32px; font-weight:bold; color:#007BFF; letter-spacing:8px;\">" + authCode + "</div>");
                sb.append("</div>");
                sb.append("<div style=\"margin-top:30px; font-size:12px; color:#888; text-align:center;\">본 인증번호는 5분간 유효합니다.<br>타인에게 공유하지 마세요.</div>");
                sb.append("</div>");
                sb.append("</body>");
                sb.append("</html>");

                log.info("발송된 인증번호: {}", authCode);

                // ✅ 인증번호 저장
                emailAuthService.saveAuthCode(emailRequest.getMailTo(), authCode);




            } else if (emailRequest.getMailType().equals("passwordAuth")) {
                mimeMessageHelper.setSubject(emailRequest.getName());
                sb.append("<div style=\"margin:100px;\">");
                sb.append("<h1> 테스트 메일 </h1>");
                sb.append("<div align=\"center\" style=\"border:1px solid black;\">");
                sb.append("<h3> 테스트 메일 내용 </h3>");
                sb.append("</div>");
                sb.append("</div>");
            } else if (emailRequest.getMailType().equals("findId")) {
                mimeMessageHelper.setSubject("[PickGame] " +emailRequest.getName() + "님 아이디 찾기 요청");
                sb.append("<!DOCTYPE html>");
                sb.append("<html lang='ko'>");
                sb.append("<head><meta charset='UTF-8'></head>");
                sb.append("<body>");
                sb.append("<div style=\"max-width:600px; margin:50px auto; padding:30px; border:1px solid #e0e0e0; border-radius:8px; font-family:Arial,sans-serif; background-color:#f9f9f9;\">");
                sb.append("<div style=\"font-size:22px; color:#333; margin-bottom:20px; text-align:center;\">[PickGame] 아이디 </div>");
                sb.append("<div style=\"background-color:#fff; padding:20px; text-align:center; border-radius:6px; border:1px dashed #007BFF;\">");
                sb.append("<div style=\"font-size:32px; font-weight:bold; color:#007BFF; letter-spacing:8px;\">").append(emailRequest.getUsername()).append("</div>");
                sb.append("</div>");
                sb.append("<div style=\"margin-top:30px; font-size:12px; color:#888; text-align:center;\">\n" +
                        "요청하신 아이디 정보를 보내드립니다.<br>\n" +
                        "본 메일은 본인 확인을 위해 발송되었습니다.\n" +
                        "</div>\n");
                sb.append("</div>");
                sb.append("</body>");
                sb.append("</html>");

            } else if (emailRequest.getMailType().equals("changePw")) {
                mimeMessageHelper.setSubject("[PickGame] " +emailRequest.getName() + "님 임시 비밀번호 요청");
                sb.append("<!DOCTYPE html>");
                sb.append("<html lang='ko'>");
                sb.append("<head><meta charset='UTF-8'></head>");
                sb.append("<body>");
                sb.append("<div style=\"max-width:600px; margin:50px auto; padding:30px; border:1px solid #e0e0e0; border-radius:8px; font-family:Arial,sans-serif; background-color:#f9f9f9;\">");
                sb.append("<div style=\"font-size:22px; color:#333; margin-bottom:20px; text-align:center;\">[PickGame] 임시 비밀번호</div>");
                sb.append("<div style=\"background-color:#fff; padding:20px; text-align:center; border-radius:6px; border:1px dashed #007BFF;\">");
                sb.append("<div style=\"font-size:32px; font-weight:bold; color:#007BFF; letter-spacing:8px;\">").append(emailRequest.getPassword()).append("</div>");
                sb.append("</div>");
                sb.append("<div style=\"margin-top:30px; font-size:12px; color:#888; text-align:center;\">\n" +
                        "아래 임시 비밀번호로 로그인 후 반드시 비밀번호를 변경해주세요.<br>\n" +
                        "타인에게 노출되지 않도록 주의해주세요.\n" +
                        "</div>");
                sb.append("</div>");
                sb.append("</body>");
                sb.append("</html>");

            }

            sb.append("</body>");
            sb.append("</html>");


            log.info(sb.toString());

            mimeMessageHelper.setText(sb.toString(), true);
            javaMailSender.send(mimeMessage);

            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }
    }

    // ✅ 인증번호 생성 메서드 추가
    public String generateAuthCode() {
        int length = 6;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }


}