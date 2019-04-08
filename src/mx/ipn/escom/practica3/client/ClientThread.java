/*
 * Author: Morales Flores Victor Leonel
 * Author: Ortiz Rivas Julio Cesar
 * ESCOM-IPN(MX)
 * Date:
 * Description:
 * 
 */

package mx.ipn.escom.practica3.client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import mx.ipn.escom.practica3.entity.Message;
import mx.ipn.escom.practica3.sockets.MulticastS;

public class ClientThread implements Runnable {
	private MulticastS mtcs;
	private Client client;
	private String path;
	private SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
	private SimpleDateFormat sdf2=new SimpleDateFormat("yyMMddHHmmss");

	public ClientThread()
	{
		mtcs=new MulticastS();
	}
	public ClientThread(MulticastS mtcs)
	{
		this.mtcs=mtcs;
	}
	public ClientThread(MulticastS mtcs,Client client)
	{
		this.mtcs=mtcs;
		this.client=client;
		
	}
	@Override
	public void run()
	{
		try
		{
			
			for(;;)
			{
				Object obj=mtcs.receiveObject();
				if(obj instanceof Message)
				{
					Message m=(Message)obj;
					
					if(m.getSender().equals("-SERVER-")) {
						client.getMessageBoard().append(m.getText());
						client.getJepChatG().setText(client.getMessageBoard().getMessages());
					} else {
						client.getMessageBoard().append("<b>"+m.getSender()+": </b>"+m.getText());
						
						if(m.getFile())
						{							
							String folder=m.getSender()+"_files/";
							createDirectory(folder);
							folder+="imgs"+sdf.format(new Date());
							createDirectory(folder);
							String fileName=m.getSender()+sdf2.format(new Date());
														
							String file = mtcs.readFile(folder,fileName);
							System.out.println("File name:"+file);
						}
						
						client.getJepChatG().setText(client.getMessageBoard().getMessages());
					}
					System.out.println("Lleg� mensaje.");
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
