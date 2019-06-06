package mx.ipn.escom.chatsockets.guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mx.ipn.escom.chatsockets.client.Client;
import mx.ipn.escom.chatsockets.constants.TcpRequestName;
import mx.ipn.escom.chatsockets.entity.Message;
import mx.ipn.escom.chatsockets.entity.MessageBoard;
import mx.ipn.escom.chatsockets.entity.User;
import mx.ipn.escom.util.UsersModel;

public class JPrivateMessage extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JEditorPane jepChatG;
	protected JButton jbSend;
	protected JButton jbFiles;
	protected JTextField jtfMessage;
	protected JScrollPane jsMessages;
	protected JLabel jlbUsers;
	protected DefaultListModel model;
	protected JPanel jpCenter,jpButtons,jpSouth;
	private User uprivate;
	private MessageBoard privateMessage;
	private Client c;
	
	private Message message;
	private String user;
	private boolean imageType = false, fileExists = false;
	private File messageFile = null;
	
	
	public JPrivateMessage(User u,MessageBoard messages,Client c)
	{
		super("Chat");
		this.uprivate=u;
		this.privateMessage=messages;
		this.c=c;
		init("Private messages with "+uprivate.getNickName()+"["+c.getUser()+"]");
		
	}
	public void init(String user)
	{
		super.setTitle("Chat - " + user);
		this.setLayout(new BorderLayout());
		setJepChatG(new JEditorPane());
		jbSend=new JButton("Send");
		jbFiles=new JButton("FIles");
		jtfMessage=new JTextField();
		jsMessages=new JScrollPane();
		jepChatG = new JEditorPane();
		
		
		model=new DefaultListModel<String>();
		
		jepChatG.setContentType("text/html");
		jsMessages.setViewportView(jepChatG);
		
		jlbUsers=new JLabel("Users                                  ");
		jpButtons=new JPanel(new GridLayout(1,2));
		jpButtons.add(jbFiles);
		jpButtons.add(jbSend);
		jpSouth=new JPanel(new GridLayout(2,1));
		jpSouth.add(jtfMessage);
		jpSouth.add(jpButtons);
		
		
		
		jpCenter=new JPanel(new BorderLayout());
		jpCenter.add(jsMessages,BorderLayout.CENTER);
		jpCenter.add(jpSouth,BorderLayout.SOUTH);
		
		
		this.add(jpCenter,BorderLayout.CENTER);
		jbSend.addActionListener(this);
		jbFiles.addActionListener(this);
		this.setSize(700,400);
		//this.setVisible(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		jepChatG.setText(privateMessage.getMessages());
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(jbSend)) {
			
			System.out.println("'Send' Button.");
			Message message = new Message(c.getUser(),jtfMessage.getText()+"<br/>", fileExists, imageType);
			message.setReceiver(uprivate.getNickName());
			sendMessage(message);
			jtfMessage.setText("");
			messageFile = null;
			fileExists = false;
			imageType = false;
		}
	}
	public void sendMessage(Message m)
	{	
		try
		{
			c.getMsc().sendObject(TcpRequestName.PRIVATE_MESSAGE);
			c.getMsc().sendObject(m);
			
			if(fileExists){
				c.getMsc().sendFile(messageFile);
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error al eniar comentario:"+ex.toString());
		}
	}
	/*GETTERS Y SETTERS*/
	public JEditorPane getJepChatG() {
		return jepChatG;
	}
	public void setJepChatG(JEditorPane jepChatG) {
		this.jepChatG = jepChatG;
	}
	public JButton getJbSend() {
		return jbSend;
	}
	public void setJbSend(JButton jbSend) {
		this.jbSend = jbSend;
	}
	public JButton getJbFiles() {
		return jbFiles;
	}
	public void setJbFiles(JButton jbFiles) {
		this.jbFiles = jbFiles;
	}
	
	public JTextField getJtfMessage() {
		return jtfMessage;
	}
	public void setJtfMessage(JTextField jtfMessage) {
		this.jtfMessage = jtfMessage;
	}
	public JScrollPane getJsMessages() {
		return jsMessages;
	}
	public void setJsMessages(JScrollPane jsMessages) {
		this.jsMessages = jsMessages;
	}
	
	public JLabel getJlbUsers() {
		return jlbUsers;
	}
	public void setJlbUsers(JLabel jlbUsers) {
		this.jlbUsers = jlbUsers;
	}
	public DefaultListModel getModel() {
		return model;
	}
	public void setModel(DefaultListModel model) {
		this.model = model;
	}
	public JPanel getJpCenter() {
		return jpCenter;
	}
	public void setJpCenter(JPanel jpCenter) {
		this.jpCenter = jpCenter;
	}
	
	public JPanel getJpButtons() {
		return jpButtons;
	}
	public void setJpButtons(JPanel jpButtons) {
		this.jpButtons = jpButtons;
	}
	public JPanel getJpSouth() {
		return jpSouth;
	}
	public void setJpSouth(JPanel jpSouth) {
		this.jpSouth = jpSouth;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
