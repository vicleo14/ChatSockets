package mx.ipn.escom.chatsockets.guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mx.ipn.escom.util.UsersModel;

public class JMainWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JEditorPane jepChatG;
	protected JButton jbSend;
	protected JButton jbFiles;
	protected JList jlUsers;
	protected JTextField jtfMessage;
	protected JScrollPane jsMessages,jsUsers;
	protected JLabel jlbUsers;
	protected DefaultListModel model;
	protected JPanel jpCenter, jpEast,jpButtons,jpSouth;
	
	
	
	public JMainWindow()
	{
		super("Chat");
		
	}
	public void init(String user)
	{
		super.setTitle("Chat - " + user);
		this.setLayout(new BorderLayout());
		setJepChatG(new JEditorPane());
		jbSend=new JButton("Send");
		jbFiles=new JButton("FIles");
		jlUsers=new JList();
		jtfMessage=new JTextField();
		jsMessages=new JScrollPane();
		jepChatG = new JEditorPane();
		jsUsers=new JScrollPane();
		
		jsUsers.setViewportView(jlUsers);
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
		
		jpEast=new JPanel(new BorderLayout());
		jsUsers.setMinimumSize(new Dimension(400,400));
		jpEast.add(jlbUsers,BorderLayout.NORTH);
		jpEast.add(jsUsers,BorderLayout.CENTER);
		
		jpCenter=new JPanel(new BorderLayout());
		jpCenter.add(jsMessages,BorderLayout.CENTER);
		jpCenter.add(jpSouth,BorderLayout.SOUTH);
		
		this.add(jpEast,BorderLayout.EAST);
		this.add(jpCenter,BorderLayout.CENTER);
		
		this.setSize(700,400);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
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
	public JList getJlUsers() {
		return jlUsers;
	}
	public void setJlUsers(JList jlUsers) {
		this.jlUsers = jlUsers;
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
	public JScrollPane getJsUsers() {
		return jsUsers;
	}
	public void setJsUsers(JScrollPane jsUsers) {
		this.jsUsers = jsUsers;
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
	public JPanel getJpEast() {
		return jpEast;
	}
	public void setJpEast(JPanel jpEast) {
		this.jpEast = jpEast;
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
