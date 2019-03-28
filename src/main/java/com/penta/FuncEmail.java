//package com.pentachord.util.function;
//
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import javax.mail.internet.MimeUtility;
//
//import com.pentachord.cmm.vo.fileVO;
//import com.pentachord.util.SMTPAuthenticator;
//
///**
// * @Title : SMS,MAIL 관련 함수
// * @date : 2017. 01. 23.
// * @author : 펜타코드
// */
//public class FuncEmail {
//
//	/**
//	 * 메일 보내는 함수 (일반메일)
//	 * 메일 보내는데 실패시 false, 성공하면 true
//	 * @param from_mail
//	 * @param from_name
//	 * @param to_mail
//	 * @param to_name
//	 * @param subject
//	 * @param content
//	 * @return
//	 */
//	public static Boolean sendMail(String from_mail, String from_name, String to_mail, String to_name, String subject, String content) {
//		configUtil config = configUtil.getInstance();
//		
//		Properties prop = new Properties();
//		prop.setProperty("mail.transport.protocol", "smtp");
//		prop.put("mail.smtp.host", config.getString("smtp.host"));
//		prop.put("mail.smtp.port", config.getString("smtp.port"));
//		
//		prop.put("mail.smtp.starttls.enable", "true"); 
//		prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//		prop.put("mail.smtp.auth", "true"); 
//		
//		Authenticator auth = new SMTPAuthenticator(config.getString("smtp.username"), config.getString("smtp.password"));
//		Session session_mail = Session.getDefaultInstance(prop, auth);
//		
//		MimeMessage message = new MimeMessage(session_mail);
//		try {
//			message.setSender(new InternetAddress(from_mail, from_name));
//			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to_mail, to_name));
//			
//			message.setSubject(subject, "UTF-8");
//			message.setContent(content, "text/html;charset=UTF-8");
//			message.setSentDate(new Date());
//			Transport.send(message);
//		} catch (UnsupportedEncodingException e) {
//			return false; 
//		} catch (MessagingException e) {
//			return false;
//		} catch (Exception e) {
//			//e.printStackTrace();
//			return false;
//		}	
//		
//		return true;
//	}
//	
//	/**
//	 * 메일 보내는 함수 (첨부파일 포함)
//	 * 메일 보내는데 실패시 false, 성공하면 true
//	 * @param from_mail
//	 * @param from_name
//	 * @param to_mail
//	 * @param to_name
//	 * @param subject
//	 * @param content
//	 * @return
//	 */
//	public static Boolean sendMail_attach(String from_mail, String from_name, String to_mail, String to_name, String subject, String content, List<fileVO> attachList) {
//		
//		configUtil config = configUtil.getInstance();
//		
//		Properties prop = new Properties();
//		prop.setProperty("mail.transport.protocol", "smtp");
//		prop.put("mail.smtp.host", config.getString("smtp.host"));
//		prop.put("mail.smtp.port", config.getString("smtp.port"));
//		
//		prop.put("mail.smtp.starttls.enable", "true"); 
//		prop.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//		prop.put("mail.smtp.auth", "true"); 
//		
//		Authenticator auth = new SMTPAuthenticator(config.getString("smtp.username"), config.getString("smtp.password"));
//		Session session_mail = Session.getDefaultInstance(prop, auth);
//		
//		MimeMessage message = new MimeMessage(session_mail);
//		try {
//			message.setSender(new InternetAddress(from_mail, from_name));
//			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to_mail, to_name));
//			message.setSubject(subject, "UTF-8");
//			
//			//본문 입력
//			Multipart partPack = new MimeMultipart();
//			MimeBodyPart bodyPart = new MimeBodyPart();
//			bodyPart.setText(content, "text/html;charset=UTF-8");
//			partPack.addBodyPart(bodyPart);
//			
//			//첨부파일 입력
//			if (attachList!=null && attachList.size()>0) {
//				File file = null;
//				FileDataSource dataSource = null;
//				for (fileVO fileVO : attachList) {
//					bodyPart = new MimeBodyPart();
//					file = new File(fileVO.getFile_path(), fileVO.getFile_name());
//					dataSource = new FileDataSource(file);
//					
//					bodyPart.setDataHandler(new DataHandler(dataSource));
//					bodyPart.setFileName(URLEncoder.encode(fileVO.getFile_orgname(), "UTF-8"));
//					partPack.addBodyPart(bodyPart);
//				}
//			}
//			
//			message.setContent(partPack);
//			Transport.send(message);
//		} catch (UnsupportedEncodingException e) {
//			return false; 
//		} catch (MessagingException e) {
//			return false;
//		} catch (Exception e) {
//			return false;
//		}
//		
//		return true;
//	}
//	
//}
