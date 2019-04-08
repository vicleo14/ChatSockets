package mx.ipn.escom.practica3.server;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.ipn.escom.practica3.constants.TcpRequestName;
import mx.ipn.escom.practica3.entity.Message;
import mx.ipn.escom.practica3.sockets.MulticastS;
import mx.ipn.escom.practica3.sockets.TcpServerSocket;

public class Server {
	private TcpServerSocket tcpss;
	private MulticastS mss;
	private Message message;
	private String path = "temporal/";
	private SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
	private SimpleDateFormat sdf2=new SimpleDateFormat("yyMMddHHmmss");

	public Server() {
		tcpss=new  TcpServerSocket();
		mss=new MulticastS("228.1.1.1",9999,true, 128);
		beginListening();
	}
	
	public void beginListening()
	{
		try
		{
			for(;;)
			{
				tcpss.getPetition();
				Object receivedObject=tcpss.readObject();
				if(receivedObject instanceof Integer)
				{
					Integer opc=(Integer)receivedObject;
					if (opc.equals(TcpRequestName.GROUP_MESSAGE)) 
					{
						message = (Message)tcpss.readObject();
						
						mss.sendObject(message);
												
						if(message.getFile()) {
							String folder = "temporal" + sdf.format(new Date());
							createDirectory(folder);
							String imageName = message.getSender()+sdf2.format(new Date());
							String filename = tcpss.readFile(folder,imageName);
							System.out.println("File name:"+filename);
							File fts = new File(filename);
							
							mss.sendFile(fts);
							/*File file = new File(folder);
							file.delete();*/
						}
						System.out.println("Todo enviado");
					}
					else if(opc.equals(TcpRequestName.PRIVATE_MESSAGE))
					{
						
					}
					else
					{
						System.out.println("Valor obtenido:"+opc);
					}
				}
				else
				{
					System.out.println("No es integer");
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
		System.out.println("Servidor en ejecución...");
		@SuppressWarnings("unused")
		Server server=new Server();
	}
}
