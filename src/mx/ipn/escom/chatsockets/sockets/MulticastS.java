/*
 * Author: Morales Flores Victor Leonel
 * Author: Ortiz Rivas Julio Cesar
 * ESCOM-IPN(MX)
 * Date:
 * Description:
 * 
 */


package mx.ipn.escom.chatsockets.sockets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class MulticastS {
	protected InetAddress group;
	protected int port;
	protected MulticastSocket multicastSocket;
	protected String inetAddress;
	protected boolean reuseAddress;
	protected int ttl;
	protected int maxSize=65507;
	public MulticastS()
	{
		try
		{
			this.inetAddress="228.1.1.1";
			this.ttl=128;
			this.port =9999;
			this.reuseAddress=true;
			
			this.multicastSocket.setTimeToLive(ttl);
			this.multicastSocket=new MulticastSocket(port);
			this.multicastSocket.setReuseAddress(reuseAddress);
			this.group=InetAddress.getByName(inetAddress);
			this.multicastSocket.joinGroup(group);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public MulticastS(String inetAddress,int port)
	{
		try
		{
			this.inetAddress=inetAddress;
			this.port =port;
			this.reuseAddress=true;
			this.multicastSocket=new MulticastSocket(port);
			this.multicastSocket.setReuseAddress(reuseAddress);
			this.group=InetAddress.getByName(inetAddress);
			this.multicastSocket.joinGroup(group);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public MulticastS(String inetAddress,int port,boolean reuseAddress)
	{
		try
		{
			this.inetAddress=inetAddress;
			this.port =port;
			this.reuseAddress=reuseAddress;
			this.multicastSocket=new MulticastSocket(port);
			this.multicastSocket.setReuseAddress(reuseAddress);
			this.group=InetAddress.getByName(inetAddress);
			this.multicastSocket.joinGroup(group);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public MulticastS(String inetAddress,int port,boolean reuseAddress, int ttl)
	{
		try
		{
			this.inetAddress=inetAddress;
			this.ttl=ttl;
			this.port =port;
			this.reuseAddress=reuseAddress;
			
			this.multicastSocket=new MulticastSocket(port);
			this.multicastSocket.setTimeToLive(ttl);
			this.multicastSocket.setReuseAddress(reuseAddress);
			this.group=InetAddress.getByName(inetAddress);
			this.multicastSocket.joinGroup(group);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	/*
	 * 
	 * 
	 */
	public void sendString(String msj) throws IOException
	{
		byte[] b=msj.getBytes();
		DatagramPacket p = new DatagramPacket(b,b.length,group,port);
		multicastSocket.send(p);
		System.out.println("Enviando mensaje "+msj+ " con un TTL= "+ multicastSocket.getTimeToLive());
	}
	
	public String receiveString() 
			throws IOException
	{
		DatagramPacket p = new DatagramPacket(new byte[maxSize],maxSize);
		multicastSocket.receive(p);
		String msj = new String(p.getData());
		return msj;
		
	}
	
	public void sendLong(Long num) throws IOException
	{
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.putLong(num);
		byte[] b= buffer.array();
		DatagramPacket p = new DatagramPacket(b,b.length,group,port);
		multicastSocket.send(p);
		System.out.println("Enviando long "+num+ " con un TTL= "+ multicastSocket.getTimeToLive());
	}
	
	public Long receiveLong() 
			throws IOException
	{
		DatagramPacket p = new DatagramPacket(new byte[maxSize],maxSize);
		multicastSocket.receive(p);
		
	    Long num = ByteBuffer.wrap(p.getData()).getLong();
		return num;
		
	}
	
	public void sendObject(Object obj) throws IOException
	{		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream oos=new ObjectOutputStream(baos);
		oos.writeObject(obj);
		oos.flush();
		
		byte[]b2=baos.toByteArray();
		DatagramPacket p=new DatagramPacket(b2,b2.length,group,port);
		multicastSocket.send(p);
	}
	
	public Object receiveObject() 
			throws IOException, ClassNotFoundException
	{
		//System.out.println("A espera de objetos del servidor");
		DatagramPacket p = new DatagramPacket(new byte[maxSize],maxSize);
		multicastSocket.receive(p);//Bloqueante
		byte[] b=p.getData();
		ByteArrayInputStream bais=new ByteArrayInputStream(b);
		ObjectInputStream ois=new ObjectInputStream(bais);
		Object obj=ois.readObject();
		return obj;
	}
	
	public void sendFile(File file) throws IOException {
		long size=file.length();
		sendString(file.getName()+";");
		sendLong(size);
				
		String path=file.getAbsolutePath();
		DataInputStream dis=new DataInputStream(new FileInputStream(path));
		byte[] b=new byte[maxSize];
		long sent=0;
		int percent=0,n=0;
		while(sent<size) {
			n=dis.read(b);
			System.out.println("Tama�o: "+n);
			DatagramPacket p=new DatagramPacket(b, n,group,port);
			multicastSocket.send(p);
			sent+=n;
			percent=(int)((sent*100)/size);
			System.out.println("\rTransmitiendo el:"+percent+"%");
		}
		dis.close();
		System.out.println("\nArchivo enviado.");
	}
	
	public String readFile(String folder, String newFileName) throws IOException {
		String name = receiveString();
		System.out.println(name);
		Long size = receiveLong();
		
		String saveLocation;
		String[] splt1=name.split(";");
		int tam=splt1.length;
		String fn=splt1[0];
		System.out.println(fn);
		String[] splt=fn.split("\\.");
		tam=splt.length;
		String ext="."+splt[tam-1];
		saveLocation=folder+"/"+newFileName+ext;
		System.out.println(saveLocation);
		DataOutputStream dos=new DataOutputStream(new FileOutputStream(saveLocation));
		byte[] b=new byte[maxSize];
		long received=0;
		int percent=0,n=0;
		while(received<size)
		{
			DatagramPacket p;
			if((received+maxSize)>size) {
				p = new DatagramPacket(new byte[(int) (size-received)],(int) (size-received));
			}else {
				p = new DatagramPacket(new byte[maxSize],maxSize);
			}
			
			multicastSocket.receive(p);//Bloqueante
			b=p.getData();
			dos.write(b,0,p.getData().length);
			dos.flush();
			received+=p.getData().length;
			percent=(int)((received*100)/size);		
			System.out.println("\rRecibido:"+percent+"%");
		}
		dos.close();
		
		return saveLocation;
	}
	/*
	 * 
	 */
	public InetAddress getGroup() {
		return group;
	}
	public void setGroup(InetAddress group) {
		this.group = group;
	}
	public int getPort() {
		return port;
	}
/*	public void setPort(int port) {
		this.port = port;
	}*/
	public MulticastSocket getMulticastSocket() {
		return multicastSocket;
	}
	public void setMulticastSocket(MulticastSocket multicastSocket) {
		this.multicastSocket = multicastSocket;
	}
	public String getInetAddress() {
		return inetAddress;
	}
	public void setInetAddress(String inetAddress) {
		try 
		{
			this.inetAddress = inetAddress;
			this.group=InetAddress.getByName(inetAddress);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
	public boolean isReuseAddress() {
		return reuseAddress;
	}
	public void setReuseAddress(boolean reuseAddress) {
		try
		{
			this.reuseAddress = reuseAddress;
			this.multicastSocket.setReuseAddress(reuseAddress);
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
		
	}
}
