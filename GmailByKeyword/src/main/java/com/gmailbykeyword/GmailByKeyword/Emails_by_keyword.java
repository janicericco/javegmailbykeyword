package com.gmailbykeyword.GmailByKeyword;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="email_by_keyword")
public class Emails_by_keyword {
	
	
	@Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
	@Column(name="email")
	private String email;
	@Column(name="subject")
	private String subject;  
	@Column(name="send_date")
	private Date send_date;  
	 
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {  
	    return email;  
	}  
	public void setEmail(String email) {  
	    this.email = email;  
	}  
	public String getSubject() {  
	    return subject;  
	}  
	public void setSubject(String subject) {  
	    this.subject = subject;  
	}  
	public Date getSend_date() {
		return send_date;
	}
	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}
	
}
