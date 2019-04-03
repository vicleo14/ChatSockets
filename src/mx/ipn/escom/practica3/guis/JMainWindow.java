package mx.ipn.escom.practica3.guis;

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
	private JEditorPane jepChatG;
	private JButton jbSend;
	private JButton jbFiles;
	private JList jlUsers;
	private JTextField jtfMessage;
	private JScrollPane jsMessages,jsUsers;
	private JLabel jlbUsers;
	private DefaultListModel model;
	private JPanel jpCenter, jpEast,jpButtons,jpSouth;
	
	
	public JMainWindow()
	{
		super("Chat");
		init();
	}
	public void init()
	{
		this.setLayout(new BorderLayout());
		jepChatG=new JEditorPane();
		jbSend=new JButton("Send");
		jbFiles=new JButton("FIles");
		jlUsers=new JList();
		jtfMessage=new JTextField();
		jsMessages=new JScrollPane();//Julio lo revisa
		
		jsUsers=new JScrollPane();//Julio lo revisa
		
		jsUsers.add(jlUsers);
		model=new DefaultListModel<String>();
		
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
		
		
		
		
	}
}
