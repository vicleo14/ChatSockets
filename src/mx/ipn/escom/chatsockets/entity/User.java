package mx.ipn.escom.chatsockets.entity;

import java.io.Serializable;

public class User implements Serializable{
	private String nickName;
	public User()
	{
		
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
