package com.gmailbykeyword.GmailByKeyword;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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
		conn.setLoginCredentials("ssummersmiths@gmail.com", "TestSummer4*".toCharArray());
		gmailClient.setConnection(conn);
		      
		final List<GmailMessage> messages = gmailClient.getMessagesBy(
		        GmailClient.EmailSearchStrategy.KEYWORD,"DevOps");
		
		
		try {
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
	        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
	      
		    SessionFactory factory = meta.getSessionFactoryBuilder().build();  
		    Session session = factory.openSession();
		    Transaction t = session.beginTransaction();
		    
		    EmailAddress from;
			String email;
			Date send_date;
			String subject;
			
			int i = 0;
			for (GmailMessage message : messages) {
				
				JavaMailGmailMessage java_message = (JavaMailGmailMessage) message;
				from = java_message.getFrom();
				send_date = java_message.getSendDate();
				subject = java_message.getSubject();
				email = from.getEmail();
				
			    System.out.println("FROM: ".concat(email).concat(" SEND DATE: ".concat(send_date.toString()).concat(" SUBJECT: ".concat(subject))) );
			    System.out.println("\n");
			    
			    if(!t.isActive()) {
			    	t.begin();
			    }
			    
			    String queryString = "select 1 from Emails_by_keyword e where e.email = :email AND e.subject = :subject AND e.send_date = :send_date";
			    Query query = session.createQuery(queryString);
			    query.setParameter("email", email);
			    query.setParameter("subject", subject);
			    query.setParameter("send_date", new java.sql.Date(((Date) send_date).getTime()));
			    
			    	Integer result = (Integer) ((org.hibernate.query.Query) query).uniqueResult();
			    	
			    	if(result == null) {
				    	
						Emails_by_keyword e1=new Emails_by_keyword();    
					    e1.setEmail(email);    
					    e1.setSubject(subject);    
					    e1.setSend_date(new java.sql.Date(((Date) send_date).getTime()));    
					     
					    session.save(e1);  
					    t.commit();  
					    System.out.println("Successfully saved");
					    System.out.println("\n");
					    i=+1;
					}
			    	else {
			    		System.out.println("Registro existente. No se guardara.");
					    System.out.println("\n");
			    	}
			    	
			    	session.disconnect();
			}
			
			factory.close();  
		    //session.close();   
		    
		    System.out.println( "Registros insertados: ".concat((String.valueOf(i))));
		    
		} 	catch(HibernateException exception){
			     System.out.println("Problem creating session factory");
			     exception.printStackTrace();
		}  
    	
    	System.out.println( "Finish processing!" );
    }
}
