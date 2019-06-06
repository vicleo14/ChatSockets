package mx.ipn.escom.chatsockets.sockets;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import mx.ipn.escom.chatsockets.client.Client;
import mx.ipn.escom.chatsockets.entity.Message;
import mx.ipn.escom.chatsockets.entity.MessageBoard;
import mx.ipn.escom.chatsockets.entity.User;
import mx.ipn.escom.chatsockets.guis.JPrivateMessage;
import mx.ipn.escom.util.UsersModel;

public class ReceiverSocket extends MulticastS implements Runnable{
	private Client client;
	private String path;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
	private SimpleDateFormat sdf2=new SimpleDateFormat("yyMMddHHmmss");
	public ReceiverSocket(Client client)
	{
		super();
		this.client=client;		
	}
	public ReceiverSocket()
	{
		super();
	}

	public ReceiverSocket(String inetAddress, int port, boolean reuseAddress, int ttl) {
		super(inetAddress, port, reuseAddress, ttl);
		// TODO Auto-generated constructor stub
	}

	public ReceiverSocket(String inetAddress, int port, boolean reuseAddress) {
		super(inetAddress, port, reuseAddress);
		// TODO Auto-generated constructor stub
	}

	public ReceiverSocket(String inetAddress, int port) {
		super(inetAddress, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try
		{
			for(;;)
			{
				Object obj=this.receiveObject();
				if(obj instanceof Message)
				{
					Message m=(Message)obj;
					if(m.getReceiver().compareTo(client.getUser())==0 )
					{
						System.out.println("Mensaje para este usuario");
						MessageBoard mb=client.getPrivateMessages().get(m.getSender());
						mb.append("<b>"+m.getSender()+": </b>"+m.getText());
						client.getPrivateWindows().get(m.getSender()).getJepChatG().setText(mb.getMessages());
					}
					else if(m.getSender().equals("-SERVER-"))
					{
						client.getMessageBoard().append(m.getText());
						client.getJepChatG().setText(client.getMessageBoard().getMessages());
					}
					else 
					{
						if(m.getReceiver().compareTo("PUBLIC")==0)
						{
							client.getMessageBoard().append("<b>"+m.getSender()+": </b>"+m.getText());
							if(m.getFile())
							{							
								String folder=m.getSender()+"_files/";
								createDirectory(folder);
								folder+="imgs"+sdf.format(new Date());
								createDirectory(folder);
								String fileName=m.getSender()+sdf2.format(new Date());
															
								this.receiveFile(folder+fileName);
								System.out.println("File name:");
							}
							client.getJepChatG().setText(client.getMessageBoard().getMessages());
						}
						else if(m.getSender().compareTo(client.getUser())==0)
						{
							MessageBoard mb=client.getPrivateMessages().get(m.getReceiver());
							mb.append("<b>"+m.getSender()+": </b>"+m.getText());
							if(m.getFile())
							{							
								String folder=m.getSender()+"_files/";
								createDirectory(folder);
								folder+="imgs"+sdf.format(new Date());
								createDirectory(folder);
								String fileName=m.getSender()+sdf2.format(new Date());
															
								this.receiveFile(folder+fileName);
								System.out.println("File name:");
							}
							client.getPrivateWindows().get(m.getReceiver()).getJepChatG().setText(mb.getMessages());
							client.getJepChatG().setText(client.getMessageBoard().getMessages());
						}
						
						
						
					}
				}
				else if(obj instanceof User)
				{
					System.out.println("Recibe usuario");
					User u=(User)obj;
					client.getMessageBoard().append("<b>New user connected: </b>"+u.getNickName()+" <br />");//Revisar clase
					client.getUsersModel().addUser(u);
					
					client.getJepChatG().setText(client.getMessageBoard().getMessages());
				}
				else if(obj instanceof Hashtable)
				{
					//client.getJlUsers().clearSelection();
					System.out.println("Llega hashtable");
					Hashtable ht=(Hashtable)obj;
					UsersModel um=new UsersModel();
					Enumeration en=ht.keys();
					while(en.hasMoreElements())
					{
						User u=(User)en.nextElement();
						um.addUser(u);
						if(client.getPrivateMessages().containsKey(u.getNickName()))
						{
							System.out.println("Contiene a:"+u.getNickName());
						}
						else
						{
							System.out.println("No contiene a:"+u.getNickName());
							MessageBoard mb = new MessageBoard("<h1>Private chat with "+u.getNickName()+".</h1>");
							client.getPrivateMessages().put(u.getNickName(),mb);
							client.getPrivateWindows().put(u.getNickName(),new JPrivateMessage(u,mb,client));
						}
					}
					client.getJlUsers().setModel(um);
					
				}
				else
				{
					this.receiveFile(client.getUser()+"/");
				}
			}

		}
		catch(Exception ex) 
		{
			System.out.println("MulticastThread in Client error:"+ex.toString());
			ex.printStackTrace();
		}
		
	}
	public void createDirectory(String dirName)
	{
		File dir=new File(dirName);
		if(!dir.exists())
			dir.mkdir();
	}
}
