package mx.ipn.escom.chatsockets.entity;

import java.io.Serializable;

public class MessageBoard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messages;
	
	public MessageBoard() {
		
	}
	
	public MessageBoard(String message) {
		this.messages = message;
	}

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}
	
	public void append(String messages) {
		this.messages += messages;
	}
	@Override
	public String toString()
	{
		return messages;
	}
}
