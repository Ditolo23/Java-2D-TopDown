package items;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJDungeonDoor extends SuperObj {
	

	public OBJDungeonDoor(int x, int y) {
		
		setWorldX(x);
		setWorldY(y);
		
		setName("DungeonDoor");
	
		try {
			image =  ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/DungeonDoor1.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setCollision(true);
	}
}
