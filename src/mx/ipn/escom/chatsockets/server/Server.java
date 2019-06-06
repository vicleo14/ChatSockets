package mx.ipn.escom.chatsockets.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import mx.ipn.escom.chatsockets.constants.TcpRequestName;
import mx.ipn.escom.chatsockets.entity.Message;
import mx.ipn.escom.chatsockets.entity.User;
import mx.ipn.escom.chatsockets.sockets.GenericSocket;
import mx.ipn.escom.chatsockets.sockets.MulticastS;

public class Server {
	private GenericSocket mcrs;
	
	private MulticastS mss;
	private Message message;
	private String path = "temporal/";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
	private SimpleDateFormat sdf2=new SimpleDateFormat("yyMMddHHmmss");
	private Hashtable<User,Date> users;

	public Server() 
	{
		//Preparacion del hash para almacenar usuarios
		users=new Hashtable<User,Date>();
		//Para recibir
		mcrs=new  MulticastS("228.1.1.2",9999,true, 128);
		//Para enviar
		mss=new MulticastS("228.1.1.1",9999,true, 128);
		System.out.println("Comienza a recibir");
		beginListening();
		
		
	}
	public void beginListening()
	{
		try
		{
			for(;;)
			{
				Object receivedObject=mcrs.receiveObject();
				System.out.println("Recibe nuevo objeto");
				System.out.println("Object:"+receivedObject.getClass());
				if(receivedObject instanceof Integer)
				{
					Integer opc=(Integer)receivedObject;
					if (opc.equals(TcpRequestName.GROUP_MESSAGE)) 
					{
						message = (Message)mcrs.receiveObject();
						message.setReceiver("PUBLIC");
						mss.sendObject(message);
												
						if(message.getFile()) {
							String folder = "temporal" + sdf.format(new Date());
							createDirectory(folder);
							String imageName = message.getSender()+sdf2.format(new Date());
							
							//mss.sendFile(fts);
							/*File file = new File(folder);
							file.delete();*/
						}
						System.out.println("Todo enviado");
					}
					else if(opc.equals(TcpRequestName.PRIVATE_MESSAGE))
					{
						System.out.println("Llega mensaje privado");
						message = (Message)mcrs.receiveObject();
						mss.sendObject(message);
												
						if(message.getFile()) {
							String folder = "temporal" + sdf.format(new Date());
							createDirectory(folder);
							String imageName = message.getSender()+sdf2.format(new Date());
							
							//mss.sendFile(fts);
							/*File file = new File(folder);
							file.delete();*/
						}
					}
					else
					{
						System.out.println("Valor obtenido:"+opc);
					}
				}
				else if(receivedObject instanceof User)
				{
					User u=(User)receivedObject;
					System.out.println("New user:"+u.getNickName());
					users.put(u,new Date());
					mss.sendObject(users);
				}
				else
				{
					mss.sendObject(receivedObject);
					//mcrs.receiveFile("files_server/");
					//System.out.println("No es integer");
				}
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error en Server:"+ex.toString());
			ex.printStackTrace();
		}
	}
	
	public void createDirectory(String dirName)
	{
		File dir=new File(dirName);
		if(!dir.exists()) {
			dir.mkdir();
			System.out.println("Directorio creado: "+dirName);
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Servidor en ejecucion...");
		Server server=new Server();
	}
}
