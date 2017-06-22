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
	public final JLabel statusLabel = new JLabel("");
	public final JButton loginButton = new JButton("Login");
	public LoginPanel(WispperClient client){
		this.client = client;
		//
		setLayout(new GridLayout(3,2));
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
	}
	public void actionPerformed(ActionEvent event) {
		statusLabel.setText("connecting...");
		client.connect(serverNameField.getText(), userNameField.getText());
	}
}
