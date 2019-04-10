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
		jsMessages=new JScrollPane();//Julio lo revisa
		jepChatG = new JEditorPane();
		jsUsers=new JScrollPane();//Julio lo revisa
		
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
}
