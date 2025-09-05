package items;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GamePanel;
import core.UtilityTools;

public abstract class SuperObj {

	public BufferedImage image, image2, image3;
	private String name;
	private boolean collision = false;
	private int worldX, worldY;
	private String packageName = "items_And_Objects";

	public Rectangle solidArea = new Rectangle(0, 0, 80, 80);
	UtilityTools uTool = new UtilityTools();

	private int solidAreaDefaultX = 0;
	private int solidAreaDefaultY = 0;

	public void draw(Graphics2D g2, GamePanel gp) {

		int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
		int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

		if (worldX + (gp.tileSize) > gp.player.getWorldX() - gp.player.getScreenX()
				&& worldX - (gp.tileSize) < gp.player.getWorldX() + gp.player.getScreenX()
				&& worldY + (gp.tileSize) > gp.player.getWorldY() - gp.player.getScreenY()
				&& worldY - (gp.tileSize) < gp.player.getWorldY() + gp.player.getScreenY()) {

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

//	public void setup(BufferedImage img, String name) {
//		try {
//			
//			img =  ImageIO.read(getClass().getResourceAsStream("/" + packageName + "/" + name + ".png"));
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public int getWorldX() {
		return worldX;
	}

	public void setWorldX(int worldX) {
		this.worldX = worldX;
	}

	public int getWorldY() {
		return worldY;
	}

	public void setWorldY(int worldY) {
		this.worldY = worldY;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public int getSolidAreaDefaultX() {
		return solidAreaDefaultX;
	}

	public void setSolidAreaDefaultX(int solidAreaDefaultX) {
		this.solidAreaDefaultX = solidAreaDefaultX;
	}

	public int getSolidAreaDefaultY() {
		return solidAreaDefaultY;
	}

	public void setSolidAreaDefaultY(int solidAreaDefaultY) {
		this.solidAreaDefaultY = solidAreaDefaultY;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
