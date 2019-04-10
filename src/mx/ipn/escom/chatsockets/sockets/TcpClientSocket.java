/*
 * Author: Morales Flores Victor Leonel
 * Author: Ortiz Rivas Julio Cesar
 * ESCOM-IPN(MX)
 * Date:
 * Description:
 * 
 */

package mx.ipn.escom.chatsockets.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class TcpClientSocket {
	private String host;
	private int port;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	Socket cl;
	public TcpClientSocket()
	{
		host="127.0.0.1";
		port=1234;
		try 
		{
			cl=new Socket(host,port);
			oos=new ObjectOutputStream(cl.getOutputStream());
			ois=new ObjectInputStream(cl.getInputStream());
		}
		catch(Exception ex)
		{
			System.out.println("Error al crear socket:"+ex.toString());
		}
	}
	public TcpClientSocket(String host,int port)
	{
		this.host=host;
		this.port=port;
		try 
		{
			cl=new Socket(host,port);
			oos=new ObjectOutputStream(cl.getOutputStream());
			ois=new ObjectInputStream(cl.getInputStream());
		}
		catch(Exception ex)
		{
			System.out.println("Error al crear socket:"+ex.toString());
		}		
	}
	
	public void sendObject(Object obj)
	{
		try
		{
			oos.writeObject(obj);
			oos.flush();
		}catch(IOException ioe)
		{
			System.out.println("Error al enviar objeto:"+ioe.toString());
		}
	}
	public Object readObject() throws ClassNotFoundException
	{
		Object obj=null;
		try
		{
			obj=ois.readObject();
		}catch(IOException ioe)
		{
			System.out.println("Error al enviar objeto:"+ioe.toString());
		}
		return obj;
	}
	
	public void sendFile(File file)
	{
		try
		{
			String name=file.getName();
			long size=file.length();
			String path=file.getAbsolutePath();
			oos.writeUTF(name);
			oos.flush();
			oos.writeLong(size);
			oos.flush();
			
			DataInputStream dis=new DataInputStream(new FileInputStream(path));
			byte[] b=new byte[2000];
			long sent=0;
			int percent=0,n=0;
			while(sent<size)
			{
				n=dis.read(b);
				oos.write(b,0,n);
				oos.flush();
				sent+=n;
				percent=(int)((sent*100)/size);
				System.out.println("\rTransmitiendo el:"+percent+"%");
			}
			dis.close();
			System.out.println("\nArchivo enviado.");
			
			
		}catch(IOException ioe)
		{
			System.out.println("Error al enviar archivo:"+ioe.toString());
		}
	}
	
	public void readFile()
	{
		try
		{
			String name=ois.readUTF();
			long size=ois.readLong();
			
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(name));
			byte[] b=new byte[2000];
			long received=0;
			int percent=0,n=0;
			while(received<size)
			{
				n=ois.read(b);
				dos.write(b,0,n);
				dos.flush();
				received+=n;
				percent=(int)((received*100)/size);		
				System.out.println("\rRecibido:"+percent+"%");
			}
			dos.close();			
		}
		catch(Exception ex) 
		{
			System.out.println("Error al leer archivo:"+ex.toString());
		}
	}
	
	public void closeConection() throws IOException
	{
		oos.close();
		ois.close();
		cl.close();
	}
	
	
	
	public int getPort() {
		return port;
	}
	public Socket getCl() {
		return cl;
	}
	public String getHost() {
		return host;
	}
}
