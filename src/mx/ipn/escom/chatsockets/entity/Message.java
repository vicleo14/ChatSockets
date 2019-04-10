package mx.ipn.escom.chatsockets.entity;

import java.io.Serializable;

public class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sender, receiver, text;
	private boolean file;
	private boolean image;
	
	public Message() {
		
	}
	
	public Message(String sender, String receiver, String text, boolean file, boolean image) {
		this.sender = sender;
		this.receiver = receiver;
		this.text = text;
		this.file = file;
		this.setImage(image);
	}
	
	public Message(String sender, String text, boolean file, boolean image) {
		this.sender = sender;
		this.text = text;
		this.file = file;
		this.setImage(image);
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean getFile() {
		return file;
	}

	public void setFile(boolean file) {
		this.file = file;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}
	
	
}
