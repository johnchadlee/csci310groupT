package csci310;

import java.util.Properties;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail  
{  
	
public static void Send(String from, String to, String text) throws MessagingException{   
      String host = "localhost";//or IP address  
      
      Properties properties = System.getProperties();  
      properties.put("mail.smtp.starttls.enable", "true");
      properties.setProperty("mail.smtp.host", "smtp.gmail.com");  //
      properties.setProperty("mail.smtp.port", "587");//
      properties.put("mail.smtp.auth", true);
      properties.put("mail.debug", true);
      final String password="/7suw_\\fT!Xb~@<w";//change accordingly  
      Transport transport;
      
      Session session = Session.getDefaultInstance(properties);  
      transport = session.getTransport("smtp");
      transport.connect("smtp.gmail.com", from, password);
      //Session session = Session.getInstance(properties, null);
  
      try{  

         MimeMessage message = new MimeMessage(session);  
         message.setFrom(new InternetAddress(from)); 
//         message.setFrom(new InternetAddress("Sender Name" + "<" + "no-reply@domain.com" + ">"));
         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
         message.setSubject("One Time Password");  
         message.setText(text);  
         message.saveChanges();
         // Send message  
         InternetAddress[] address = {new InternetAddress(to)};
         transport.sendMessage(message, address);  
         System.out.println("message sent successfully....");  
         transport.close();

      }catch (MessagingException mex) {mex.printStackTrace();}  
   }  
} 