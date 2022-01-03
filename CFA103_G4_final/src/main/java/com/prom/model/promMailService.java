package com.prom.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class promMailService {

	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String mname,String mainprom,String promTimeStart,String promTimeEnd) {

		try {
			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
			// ●須將myGmail的【安全性較低的應用程式存取權】打開
			final String myGmail = "bibcfa103g4@gmail.com";
			final String myGmail_password = "aa830518";
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});
			
			Transport transport = session.getTransport();

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// 設定信中的主旨
//			message.setSubject(subject, "UTF-8");
			// 設定信中的內容
////			message.setText(messageText);
//			message.setContent(messageText, "text/html;charset=UTF-8");
			
			
			
			
			
			
//			Transport.send(message);
//			System.out.println("傳送成功!");
			
			 // 設定主旨
            message.setSubject("BIB:通知您，您有尚未使用的最新特惠");

            // 文字部份，注意 img src 部份要用 cid:接下面附檔的header
            MimeBodyPart textPart = new MimeBodyPart();
            StringBuffer html = new StringBuffer();
            html.append("<h3>親愛的"+mname+"您好:</h1>");
            html.append("<h3>您專屬的美食殿堂BIB，為您提供以下最新特惠資訊:</h3><br>");
            html.append(mainprom);
//            html.append("<img src='cid:image'/><br>");
            textPart.setContent(html.toString(), "text/html; charset=UTF-8");

            // 圖檔部份，注意 html 用 cid:image，則header要設<image>
            MimeBodyPart picturePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource("C:\\Users\\Tibame\\Desktop\\報告使用\\Ru\\prom_pic (3).jpg");
            picturePart.setDataHandler(new DataHandler(fds));
            picturePart.setFileName(fds.getName());
            picturePart.setHeader("Content-ID", "<image>");

            Multipart email = new MimeMultipart();
            email.addBodyPart(picturePart);
            email.addBodyPart(textPart);
 
            message.setContent(email);

            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO))
                                  ;
            transport.close();
            System.out.println("傳送成功!");
			
			
			
			
			
			
			
			
		} catch (MessagingException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
	}

//	public static void main(String args[]) {
//
//		String to = "c6b7yntz@gmail.com";
//
////		String subject = "BIB:最新特惠活動通知";
//
//		String mname = "李先生";
//		String	promTimeStart = ("2022-01-06");
//		String	promTimeEnd = ("2022-01-06");
//	
//				
//		String mainprom = "周年慶! 本店招牌健康美食水果沙拉，自"+promTimeStart+"至"+promTimeEnd+"全面八折優惠。";
//		
//		promMailService mailService = new promMailService();
//		mailService.sendMail(to, mname,mainprom,promTimeStart,promTimeEnd);
//
//	}

}