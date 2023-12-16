package com.koreait.bnb.service;

import com.koreait.bnb.domains.vo.UserVO;
import com.koreait.bnb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
@PropertySource("classpath:mail/mailMessages.properties")
public class UserMailService {
    private static final String FIND_ID_TEMPLATE_PATH = "template/find-id.html";

    @Autowired private TemplateEngine emailTemplateEngine;
    @Autowired private JavaMailSender mailSender;
    @Autowired private UserMapper userMapper;

    @Value("${fromEmailAddr}")
    private String fromEmail;

    public void find_id(String userEmail) throws MessagingException{
        UserVO userVO = userMapper.get_user_by_email(userEmail);
        // 해당 유저가 존재함
        if(userVO == null){
            final Context ctx = new Context(Locale.KOREAN);
            ctx.setVariable("userId", "[NOT FOUNDED]");
            send_mail(userEmail, fromEmail, FIND_ID_TEMPLATE_PATH, ctx);
        }
        // 유저가 존재하지 않음
        else{
            final Context ctx = new Context(Locale.KOREAN);
            ctx.setVariable("userId", userVO.getId());
            send_mail(userEmail, fromEmail, FIND_ID_TEMPLATE_PATH, ctx);
        }
    }

    private void send_mail(String to, String from, String templatePath, Context context) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        final String htmlContent = emailTemplateEngine.process(templatePath, context);
        helper.setFrom(from); //누가 보내는가?
        helper.setTo(to); //누구한테 보내는가?
        helper.setText(htmlContent, true); //메일 내용은 무엇인가?
        // 메세지 발송
        mailSender.send(mimeMessage);
    }
}
