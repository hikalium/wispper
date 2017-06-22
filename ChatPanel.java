import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial") 
public class ChatPanel extends JPanel implements ActionListener { 
	WispperClient client;
	
	final MapPanel mapPanel;
	final JTextField messageField = new JTextField();
	final JButton sendButton = new JButton("Send");
	private DefaultListModel<String> messageList = new DefaultListModel<String>();
	JList<String> messageJList = new JList<String>(messageList);
	final JScrollPane scrollPane = new JScrollPane(messageJList);

	public ChatPanel(WispperClient client){
		this.client = client;
		mapPanel = new MapPanel(client);
		mapPanel.setPreferredSize(new Dimension(600, 512));
		mapPanel.setBackground(Color.WHITE);
		//
		Panel leftPane = new Panel();
		leftPane.setLayout(new BoxLayout(leftPane,BoxLayout.Y_AXIS));
		leftPane.add(messageField); 
		sendButton.setPreferredSize(new Dimension(250, 30));
		leftPane.add(sendButton);
		leftPane.add(scrollPane);
		add(leftPane, BorderLayout.LINE_START);
		add(mapPanel, BorderLayout.CENTER);
		//
		sendButton.addActionListener(this);
	}
	public void actionPerformed(ActionEvent event) {
		messageList.add(0, messageField.getText());
		client.sendMsg(messageField.getText());
	}
}
