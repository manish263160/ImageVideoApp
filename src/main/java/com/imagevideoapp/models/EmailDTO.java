package com.imagevideoapp.models;
import java.io.Serializable;

public class EmailDTO implements Serializable{
	private static final long serialVersionUID = -626507878053792983L;
	private String to;
	private String cc;
	private String bcc;
	private String from;
	private String subject;
	private String text;
	private String StringArrayToString(String[] strArray){
		if(strArray==null){
			return "";
		}
		StringBuilder str = new StringBuilder("");
		for (int i=0;i<strArray.length;i++){
			String s = strArray[i];
			str = str.append(s);
			if(i+1<strArray.length) str.append(",");
		}
		return str.toString();
	}
	
	public String getTo() {
		return to;
	}
	public void setTo(String[] to) {
		this.to = StringArrayToString(to);
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String[] cc) {
		this.cc = StringArrayToString(cc);
	}
	public String getBcc() {
		return bcc;
	}
	public void setBcc(String[] bcc) {
		this.bcc = StringArrayToString(bcc);
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}