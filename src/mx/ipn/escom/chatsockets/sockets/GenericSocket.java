package mx.ipn.escom.chatsockets.sockets;

import java.io.File;

public interface GenericSocket {
	public void sendObject(Object obj);
	public Object receiveObject() throws ClassNotFoundException;
	public void sendFile(File file);
	public void receiveFile(String pathToSave);
}