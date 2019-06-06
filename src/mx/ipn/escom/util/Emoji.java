package mx.ipn.escom.util;

import java.util.Enumeration;
import java.util.Hashtable;

import mx.ipn.escom.chatsockets.entity.MessageBoard;

public class Emoji {
	private Hashtable<String,String> emojiLocator;
	private String path="/home/victor/eclipse-workspace/chat/ChatSockets/emojisImages/";
	public Emoji()
	{
		emojiLocator=new Hashtable<String,String>();
		emojiLocator.put(">:(", path+"angry.png");
		emojiLocator.put(">:-(", path+"angry-1.png");
		emojiLocator.put(":-O", path+"bored.png");
		emojiLocator.put(":O", path+"bored-1.png");
		emojiLocator.put(":o", path+"bored-2.png");
		emojiLocator.put(":|", path+"confused.png");
		emojiLocator.put(":-|", path+"confused-1.png");
		emojiLocator.put(":'(", path+"crying.png");
		emojiLocator.put(":'-(", path+"crying-1.png");
		emojiLocator.put("^-^", path+"embarrassed.png");
		emojiLocator.put(":[", path+"emoticons.png");
		emojiLocator.put(":)", path+"happy.png");
		emojiLocator.put(":-)", path+"happy-1.png");
		emojiLocator.put(":))", path+"happy-2.png");
		emojiLocator.put("=)", path+"happy-3.png");
		emojiLocator.put("=-)", path+"happy-4.png");
		emojiLocator.put(":8", path+"ill.png");
		emojiLocator.put("u()u", path+"in-love.png");
		emojiLocator.put(":-*", path+"kissing.png");
		emojiLocator.put(">=(", path+"mad.png");
		emojiLocator.put("8)", path+"nerd.png");
		emojiLocator.put("(:)", path+"ninja.png");
		emojiLocator.put("=X", path+"quiet.png");
		emojiLocator.put(":(", path+"sad.png");
		emojiLocator.put(":!|", path+"secret.png");
		emojiLocator.put(">:)", path+"smart.png");
		emojiLocator.put(":D", path+"smile.png");
		emojiLocator.put(":-D", path+"smiling.png");
		emojiLocator.put("=o", path+"surprised.png");
		emojiLocator.put("=-o", path+"surprised-1.png");
		emojiLocator.put("}=)", path+"suspicious.png");
		emojiLocator.put("}:)", path+"suspicious-1.png");
		emojiLocator.put(":')", path+"tongue-out.png");
		emojiLocator.put("=')", path+"tongue-out-1.png");
		emojiLocator.put(":((", path+"unhappy.png");
	}
	public String setEmojis(String originalMessage)
	{
		String modifiedMessage=originalMessage;
		Enumeration a=emojiLocator.keys();
		while(a.hasMoreElements())
		{
			String code=(String)a.nextElement();
			//System.out.println(code);
			String newString ="<img src='"+(String)emojiLocator.get(code)+"' />";
			modifiedMessage=modifiedMessage.replace(code, newString);
			System.out.println(modifiedMessage);
		}
		return modifiedMessage;
	}
}
