package mx.ipn.escom.chatsockets.client;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import mx.ipn.escom.chatsockets.client.ClientThread;
import mx.ipn.escom.chatsockets.constants.TcpRequestName;
import mx.ipn.escom.chatsockets.entity.Message;
import mx.ipn.escom.chatsockets.entity.MessageBoard;
import mx.ipn.escom.chatsockets.guis.JMainWindow;
import mx.ipn.escom.chatsockets.sockets.MulticastS;
import mx.ipn.escom.chatsockets.sockets.TcpClientSocket;

public class Client extends JMainWindow implements ActionListener,ListSelectionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MulticastS msc;
	private Runnable ct;
	private MessageBoard messageBoard;
	private Message message;
	private String user;
	private boolean imageType = false, fileExists = false;
	private File messageFile = null;
	
	public Client()
	{
		super();
		user();
		init(user);
		loadBoard();
		setListeners();
		msc=new MulticastS("228.1.1.1",9999,true);
		ct=new ClientThread(msc,this);
		new Thread(ct).start();
	}
	
	public void user() {
		user = JOptionPane.showInputDialog("Ingrese un nombre de usuario.");
	}
	
	public void loadBoard() {
		messageBoard = new MessageBoard("<h1>Welcome to the Chat Group.</h1>");
		jepChatG.setText(messageBoard.getMessages());
	}

	private void setListeners() {
		jbSend.addActionListener(this);
		jbFiles.addActionListener(this);
	}

	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Client c = new Client();
	}

	public void valueChanged(ListSelectionEvent lse) {
		
	}

	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(jbSend)) {
			System.out.println("'Send' Button.");
			message = new Message(user,jtfMessage.getText()+"<br/>", fileExists, imageType);
			sendMessage();
			jtfMessage.setText("");
			messageFile = null;
			fileExists = false;
			imageType = false;
		} 
		
		else if (ae.getSource().equals(jbFiles)) {
			JFileChooser jfc=new JFileChooser();
			int r=jfc.showOpenDialog(this);
			if(r==JFileChooser.APPROVE_OPTION)
			{
				fileExists = true;
				messageFile=jfc.getSelectedFile();
				isImage();
			}
		}
	}
	
	public void isImage() {
		try {
		    Image image = ImageIO.read(messageFile);
		    if (image == null) {
		    	imageType = false;
		        System.out.println("The file could not be opened , it is not an image");
		    } else {
		    	imageType = true;
		    }
		} catch(IOException ex) {
			imageType = false;
		    System.out.println("The file could not be opened , an error occurred.");
		}
	}
	
	public void sendMessage()
	{	
		try
		{
			TcpClientSocket tcpcs=new TcpClientSocket("127.0.0.1",1234);
			tcpcs.sendObject(TcpRequestName.GROUP_MESSAGE);
			tcpcs.sendObject(message);
			
			if(fileExists){
				tcpcs.sendFile(messageFile);
			}
			
			tcpcs.closeConection();
		}
		catch(Exception ex)
		{
			System.out.println("Error al eniar comentario:"+ex.toString());
		}
	}

	public MessageBoard getMessageBoard() {
		return messageBoard;
	}

	public void setMessageBoard(MessageBoard messageBoard) {
		this.messageBoard = messageBoard;
	}
	
	public String getUser() {
		return user;
	}

}
