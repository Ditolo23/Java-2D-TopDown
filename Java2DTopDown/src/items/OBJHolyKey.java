package items;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJHolyKey extends SuperObj{

	public OBJHolyKey(int x, int y) {
		
		setWorldX(x);
		setWorldY(y);
		
		setName("HolyKey");
	
		try {
			image =  ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/HolyKey.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setCollision(false);
	}

}	
