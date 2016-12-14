package com.cs.test.mail;

import com.cs.test.db.entity.Chapter;
import com.cs.test.utils.DateFormatter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Created by admin on 2016/12/14.
 */
@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;


	@Value("${spring.mail.username}")
	private String mailFrom;

	@Value("csnjupt@126.com;576906562@qq.com;804210245@qq.com")
	private String mailTo;

/*
	@Autowired
	private VelocityEngine velocityEngine;*/
	
	private final static Logger log = LoggerFactory.getLogger(MailService.class);
		

	public void sendSampleMail(){
		log.info("sendmail....");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mailFrom);//发送者.
		message.setTo(mailTo);//接收者.
		message.setSubject("测试邮件（邮件主题）");//邮件主题.
		message.setText("这是邮件内容");//邮件内容.

		mailSender.send(message);//发送邮件
		log.info("end sendmail.");
	}



	public void testSendHtml() {
		MimeMessage message = null;
		try {
			message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(mailFrom);
			helper.setTo(mailTo);
			helper.setSubject("标题：发送Html内容");

			StringBuffer sb = new StringBuffer();
			sb.append("<h1>大标题-h1</h1>")
			  .append("<p style='color:#F00'>红色字</p>")
			  .append("<p style='text-align:left'>左对齐</p>");
			helper.setText(sb.toString(), true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		mailSender.send(message);
	}


	public void sendChapterNotify(Chapter chapter) {
		if(chapter == null) return;
		try {
			log.info(String.format("send notify%s", String.format("书籍%s->章节%s 更新推送", chapter.getBookName(), chapter.getName())));
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(mailFrom);
			helper.setTo(mailTo);
			helper.setSubject(String.format("书籍%s->章节%s 更新推送", chapter.getBookName(), chapter.getName()));

			String content = chapter.getContent();
			content = Arrays.stream(content.split("\r\n")).filter(StringUtils::isNotBlank).map(s->	String.format("<p style='text-align:left'>    %s</p>", s)).collect(Collectors.joining("</br>"));
			log.info(content);


			StringBuffer sb = new StringBuffer();
			sb.append(String.format("<h2>%s</h2>", chapter.getName()))
			  .append(String.format("</br>"))
			  .append(content)
			  .append(String.format("</br>"))
			  .append(String.format("</br>"))
			  .append(String.format("<p style='text-align:left'>更新时间%s</p>", DateFormatter.longDate(new Date())));
			helper.setText(sb.toString(), true);
			mailSender.send(message);
		} catch (MessagingException e) {
			log.info("send notify exception!");
		}
	}
/*
	public void sendTemplateMail() {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(mailFrom);
			helper.setTo("dingfuzhao@vcredit.com");
			helper.setSubject("主题：模板邮件");
			Map<String, Object> model = new HashedMap();
			model.put("username", mailTo);
			String text = VelocityEngineUtils.mergeTemplateIntoString(
					velocityEngine, "template.vm", "UTF-8", model);
			helper.setText(text, true);
			mailSender.send(mimeMessage);
		}catch (Exception e){}
	}*/

}
