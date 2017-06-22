import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial") 
public class Panel1 extends JPanel { 
	private class ChatCharacter{
		private Point2D pos;
		public ChatCharacter(){
			pos = new Point2D.Float();
		}
	}
	private Map<Integer, ChatCharacter> characterList;
	private WispperClient client;
	public Panel1(WispperClient client){
		this.client = client;
		this.characterList = new HashMap<>();
	}
	@Override public void paintComponent(Graphics g) { 
		super.paintComponent(g); 
		Graphics2D g2d = (Graphics2D) g; 
		//
		for(Map.Entry<Integer, ChatCharacter>e : characterList.entrySet()){
			Integer id = e.getKey();
			ChatCharacter c = e.getValue();
			int x = (int)c.pos.getX();
			int y = (int)c.pos.getY();
			try{
				BufferedImage img = ImageIO.read(
						new File("character1.png"));
			g2d.drawImage(img, x, y, 32, 32, null);
			} catch(IOException err){
				err.printStackTrace();
			}
			g2d.drawString("id:" + id, x, y); 
		}
	}
	public void appendCharacter(int id){
		this.characterList.put(id, new ChatCharacter());
	}
	public void moveCharacter(int id, double dx, double dy){
		ChatCharacter ch = characterList.get(id);
		if(ch == null) return;
		ch.pos.setLocation(
				dx + ch.pos.getX(),
				dy + ch.pos.getY());
		revalidate();
		repaint();
		if(client.clientID == id){
			try{
				client.sendMsg("move " + id + " " + 
						(int)ch.pos.getX() + " " + 
						(int)ch.pos.getY()
						);
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public void setCharacterPos(int id, double x, double y){
		ChatCharacter ch = characterList.get(id);
		if(ch == null) return;
		ch.pos.setLocation(x, y);
		revalidate();
		repaint();
	}
}
