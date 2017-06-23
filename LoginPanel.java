import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial") 
public class LoginPanel extends JPanel implements ActionListener { 
	WispperClient client;
	final JTextField userNameField = new JTextField();
	final JTextField serverNameField = new JTextField("localhost");
	final JLabel statusLabel = new JLabel("");
	final JButton loginButton = new JButton("Login");
	final String[] iconList = {
					"black.png", 
					"blue.png",
					"green.png",
					"orange.png",
					"pink.png"};
	final JComboBox iconBox = new JComboBox<String>(iconList);
	public LoginPanel(WispperClient client){
		this.client = client;
		//
		setLayout(new GridLayout(4,2));
		//
		add(new JLabel("Server Name:", SwingConstants.LEFT));
		add(serverNameField);
		//
		add(new JLabel("User Name:", SwingConstants.LEFT));
		add(userNameField);
		//
		add(new JLabel("Icon:", SwingConstants.LEFT));
		//JComboBox iconBox = new JComboBox<String>(iconList);
		add(iconBox);
		//
		loginButton.addActionListener(this);
		//
		add(statusLabel);
		add(loginButton);
	}
	public void actionPerformed(ActionEvent event) {
		setStatus("connecting...", false);
		client.connect(serverNameField.getText(), userNameField.getText(), (String)iconBox.getSelectedItem());
	}
	public void setStatus(String str, boolean acceptLogin)
	{
		statusLabel.setText(str);
		loginButton.setEnabled(acceptLogin);
	}
}
