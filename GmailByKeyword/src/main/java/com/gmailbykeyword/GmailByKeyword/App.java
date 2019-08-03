package com.gmailbykeyword.GmailByKeyword;

import java.util.Date;
import java.util.List;

import com.gmailbykeyword.GmailByKeyword.javamail.*;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
    {
        
		GmailClient gmailClient = new ImapGmailClient();//.. some new instance of GmailClient
		GmailConnection conn = new ImapGmailConnection();
		conn.setLoginCredentials("janicericco@gmail.com", "rtuezxyuyhhdvpsm".toCharArray());
		gmailClient.setConnection(conn);
		      
		final List<GmailMessage> messages = gmailClient.getMessagesBy(
		        GmailClient.EmailSearchStrategy.KEYWORD,"Repertorio");
		EmailAddress from;
		String email;
		Date send_date;
		String subject;
		for (GmailMessage message : messages) {
			JavaMailGmailMessage java_message = (JavaMailGmailMessage) message;
			from = java_message.getFrom();
			send_date = java_message.getSendDate();
			subject = java_message.getSubject();
			email = from.getEmail();
			
		    System.out.println("FROM: ".concat(email) );
		    System.out.println("\n");
		    System.out.println("SEND DATE: ".concat(send_date.toString()) );
		    System.out.println("\n");
		    System.out.println("SUBJECT: ".concat(subject) );
		    System.out.println("\n");
		}
    	
    	System.out.println( "Hello World!" );
    }
}
