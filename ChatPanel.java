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
	GridBagLayout gbl = new GridBagLayout();

	//private setGridConstraints()

	public ChatPanel(WispperClient client){
		mapPanel = new MapPanel(client);
		//
		setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		//
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbl.setConstraints(messageField, gbc);
		add(messageField);
		//
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbl.setConstraints(sendButton, gbc);
		add(sendButton);
		//
		sendButton.addActionListener(this);
		//
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 4;
		gbl.setConstraints(scrollPane, gbc);
		add(scrollPane);
		//
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 6;
		gbc.gridwidth = 5;
		gbl.setConstraints(mapPanel, gbc);
		add(mapPanel);
		/*
		//
		add(new JLabel("Server Name:", SwingConstants.LEFT));
		add(serverNameField);
		//
		add(new JLabel("User Name:", SwingConstants.LEFT));
		add(userNameField);
		//
		loginButton.addActionListener(this);
		//
		add(statusLabel);
		add(loginButton);
		*/
	}
	public void actionPerformed(ActionEvent event) {
		messageList.add(0, messageField.getText());
	}
	/*
	public void actionPerformed(ActionEvent event) {
		setStatus("connecting...", false);
		client.connect(serverNameField.getText(), userNameField.getText());
	}
	public void setStatus(String str, boolean acceptLogin)
	{
		statusLabel.setText(str);
		loginButton.setEnabled(acceptLogin);
	}
	*/
}
