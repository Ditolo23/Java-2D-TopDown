package items;

import java.io.IOException;

import javax.imageio.ImageIO;

import core.GamePanel;

public class OBJHeart extends SuperObj{
	
	GamePanel gp;
	
	public OBJHeart(GamePanel gp) {
		
		this.gp = gp;
		
		setName("Heart");
		
		try {
			image =  ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/HeartFull.png"));
			image2 =  ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/HeartHalf.png"));
			image3 =  ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/HeartEmpty.png"));
			
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
			image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
