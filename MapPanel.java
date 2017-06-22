import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("serial") 
public class MapPanel extends JPanel/* implements ActionListener*/ { 
	WispperClient client;
	public MapPanel(WispperClient client){
		setFocusable(true);
		// set key events
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		Map<Integer, String> imList = new HashMap<>();
		imList.put(java.awt.event.KeyEvent.VK_ENTER, "key_Enter");
		imList.put(java.awt.event.KeyEvent.VK_UP, "key_Up");
		imList.put(java.awt.event.KeyEvent.VK_DOWN, "key_Down");
		imList.put(java.awt.event.KeyEvent.VK_LEFT, "key_Left");
		imList.put(java.awt.event.KeyEvent.VK_RIGHT, "key_Right");
		for(Map.Entry<Integer, String>e: imList.entrySet()){
			im.put(KeyStroke.getKeyStroke(e.getKey(), 0), e.getValue());
		}
		am.put("key_Enter",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					//panel1.moveCharacter(client.clientID, 0, 10);
					System.out.println("0, 10");
				}
			}
		);
		am.put("key_Up",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					//panel1.moveCharacter(client.clientID, 0, -10);
					System.out.println("0, -10");
				}
			}
		);
		am.put("key_Down",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					//panel1.moveCharacter(client.clientID, 0, 10);
					System.out.println("0, 10");
				}
			}
		);
		am.put("key_Left",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					//panel1.moveCharacter(client.clientID, -10, 0);
					System.out.println("-10, 0");
				}
			}
		);
		am.put("key_Right",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					//panel1.moveCharacter(client.clientID, 10, 0);
					System.out.println("10, 0");
				}
			}
		);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			.put(KeyStroke.getKeyStroke(
						java.awt.event.KeyEvent.VK_SPACE, 0), "key_Space");
		getActionMap().put("key_Space",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Space が押されました");
				}
			}
		);
		MapPanel pane = this;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pane.requestFocus(true);
			}
		});
	}
}
