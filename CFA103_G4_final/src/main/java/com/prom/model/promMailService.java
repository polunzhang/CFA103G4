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

	// �]�w�ǰe�l��:�ܦ��H�H��Email�H�c,Email�D��,Email���e
	public void sendMail(String to, String mname,String mainprom,String promTimeStart,String promTimeEnd) {

		try {
			// �]�w�ϥ�SSL�s�u�� Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			// ���]�w gmail ���b�� & �K�X (�N�ǥѧA��Gmail�ӶǰeEmail)
			// �����NmyGmail���i�w���ʸ��C�����ε{���s���v�j���}
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

			// �]�w�H�����D��
//			message.setSubject(subject, "UTF-8");
			// �]�w�H�������e
////			message.setText(messageText);
//			message.setContent(messageText, "text/html;charset=UTF-8");
			
			
			
			
			
			
//			Transport.send(message);
//			System.out.println("�ǰe���\!");
			
			 // �]�w�D��
            message.setSubject("BIB:�q���z�A�z���|���ϥΪ��̷s�S�f");

            // ��r�����A�`�N img src �����n�� cid:���U�����ɪ�header
            MimeBodyPart textPart = new MimeBodyPart();
            StringBuffer html = new StringBuffer();
            html.append("<h3>�˷R��"+mname+"�z�n:</h1>");
            html.append("<h3>�z�M�ݪ���������BIB�A���z���ѥH�U�̷s�S�f��T:</h3><br>");
            html.append(mainprom);
//            html.append("<img src='cid:image'/><br>");
            textPart.setContent(html.toString(), "text/html; charset=UTF-8");

            // ���ɳ����A�`�N html �� cid:image�A�hheader�n�]<image>
            MimeBodyPart picturePart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource("C:\\Users\\Tibame\\Desktop\\���i�ϥ�\\Ru\\prom_pic (3).jpg");
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
            System.out.println("�ǰe���\!");
			
			
			
			
			
			
			
			
		} catch (MessagingException e) {
			System.out.println("�ǰe����!");
			e.printStackTrace();
		}
	}

//	public static void main(String args[]) {
//
//		String to = "c6b7yntz@gmail.com";
//
////		String subject = "BIB:�̷s�S�f���ʳq��";
//
//		String mname = "������";
//		String	promTimeStart = ("2022-01-06");
//		String	promTimeEnd = ("2022-01-06");
//	
//				
//		String mainprom = "�P�~�y! �����۵P���d�������G�F�ԡA��"+promTimeStart+"��"+promTimeEnd+"�����K���u�f�C";
//		
//		promMailService mailService = new promMailService();
//		mailService.sendMail(to, mname,mainprom,promTimeStart,promTimeEnd);
//
//	}

}