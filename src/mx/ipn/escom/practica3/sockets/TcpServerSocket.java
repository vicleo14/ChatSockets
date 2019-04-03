/*
 * Author: Morales Flores Victor Leonel
 * Author: Ortiz Rivas Julio Cesar
 * ESCOM-IPN(MX)
 * Date:
 * Description:
 * 
 */

package mx.ipn.escom.practica3.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServerSocket {
	private String host;
	private int port;
	
	private ServerSocket s;
	private boolean reuseAddress;
	/*PARA RESPONDER PETICIONES*/
	
	private Socket cl;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	public TcpServerSocket()
	{
		this.host="127.0.0.1";
		this.port=1234;
		this.reuseAddress=true;
		try 
		{
			s=new ServerSocket(port);
			s.setReuseAddress(this.reuseAddress);
		}
		catch(Exception ex)
		{
			System.out.println("Error al crear socket:"+ex.toString());
		}
	}
	public TcpServerSocket(String host, int port)
	{
		this.host=host;
		this.port=port;
		this.reuseAddress=true;
		try 
		{
			s=new ServerSocket(port);
			s.setReuseAddress(this.reuseAddress);
		}
		catch(Exception ex)
		{
			System.out.println("Error al crear socket:"+ex.toString());
		}
	}
	public TcpServerSocket(String host, int port,boolean reuseAddress)
	{
		this.host=host;
		this.port=port;
		this.reuseAddress=reuseAddress;
		try 
		{
			s=new ServerSocket(port);
			s.setReuseAddress(this.reuseAddress);
		}
		catch(Exception ex)
		{
			System.out.println("Error al crear socket:"+ex.toString());
		}
	}
	public void getPetition()
	{
		try
		{
			System.out.println("Comenzando escucha:");
			cl = s.accept();
			cl.setSoLinger(true,5000);//Se habilita una vez que se invoca el metodo close()
			System.out.println("Cliente conectado desde:"+cl.getInetAddress()+":"+cl.getPort());
			ois = new ObjectInputStream(cl.getInputStream());
			oos = new ObjectOutputStream(cl.getOutputStream());
		}
		catch(Exception ex)
		{
			System.out.println("Error en server socket:"+ex.toString());
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
	public String readFile(String folder,String newFileName)
	{
		String saveLocation="";
		try
		{
			
			String name=ois.readUTF();
			
			long size=ois.readLong();
			String[] splt=name.split("\\.");
			int tam=splt.length;
			String ext="."+splt[tam-1];
			saveLocation=folder+"/"+newFileName+ext;
			DataOutputStream dos=new DataOutputStream(new FileOutputStream(saveLocation));
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
		return saveLocation;
	}
	
	
	
	public void closeClientConnection()
	{
		try
		{
			oos.close();
			ois.close();
			cl.close();
		}
		catch(Exception ex)
		{
			System.out.println("Error en server socket:"+ex.toString());
		}
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public ServerSocket getS() {
		return s;
	}
	public void setS(ServerSocket s) {
		this.s = s;
	}
	public boolean isReuseAddress() {
		return reuseAddress;
	}
	public void setReuseAddress(boolean reuseAddress) {
		this.reuseAddress = reuseAddress;
	}
	public Socket getCl() {
		return cl;
	}
	public void setCl(Socket cl) {
		this.cl = cl;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	public ObjectInputStream getOis() {
		return ois;
	}
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	

}
