import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.HashMap;
import java.util.Map;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial") 
public class MapPanel extends JPanel/* implements ActionListener*/ { 
	WispperClient client;
	public class Character extends JLabel{
		private Point2D pos = new Point2D.Float();
		BufferedImage img;
		public Character(String imgName){
			super(new ImageIcon("./imgs/" + imgName));
			setLayout(null);
			setBounds(0, 0, 64, 64);
		}
		@Override public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			//
			if(img != null) g2d.drawImage(img, 0, 0, 64, 64, null);
			System.out.println("paintComponent!");
		}
		public void moveRel(int dx, int dy)
		{
			setLocation(this.getX() + dx, this.getY() + dy);
			System.out.println("(" + this.getX() + ", " + this.getY() + ")");
			revalidate();
			repaint();
		}
	}
	Character mainCharacter = new Character("black.png");
	public MapPanel(WispperClient client){
		setLayout(null);
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
				}
			}
		);
		am.put("key_Up",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					mainCharacter.moveRel(0, -10);
				}
			}
		);
		am.put("key_Down",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					mainCharacter.moveRel(0, 10);
				}
			}
		);
		am.put("key_Left",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					mainCharacter.moveRel(-10, 0);
				}
			}
		);
		am.put("key_Right",
			new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					mainCharacter.moveRel(10, 0);
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
		add(mainCharacter);
	}
}
