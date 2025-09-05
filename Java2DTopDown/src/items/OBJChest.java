package items;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJChest extends SuperObj{
	
	public OBJChest(int x, int y) {
		
		setWorldX(x);
		setWorldY(y);
		
		setName("Chest");
	
		try {
			image =  ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/Chest.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setCollision(true);
	}

}
