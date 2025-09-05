package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GamePanel;
import core.UtilityTools;

//entities are your main character, npc, enemies, bosses etc...

public abstract class Entity {
	
	GamePanel gp;

	private int worldX, worldY;
	private int speed;

	// package name for setup
	private String packageName;

	public Rectangle solidArea = new Rectangle(0, 0, 80, 80);

	private int solidAreaDefaultX, solidAreaDefaultY;

	private boolean collisionOn = false;
	
	private int actionCounter = 0;
	
	String[] dialogues = new String[20];
	String[] dialogues2 = new String[20]; //test, remove later on
	int dialogueIndex = 0;
	
	//CHARACTER STATUS (EX. HP, ATTACK, DEFENSE)
	private int maxLife;
	private int currentLife;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, faceImage;

	private String direction;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public BufferedImage setup(String imageName) {
		UtilityTools uT = new UtilityTools();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/" + packageName + "/" + imageName + ".png"));
			image = uT.scaleImage(image, gp.tileSize, gp.tileSize);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
	
	public void behaviour() {}
	
	public void speak() {


		if(dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.setCurrentDialogue(dialogues[dialogueIndex]);
		dialogueIndex++;;
		
		facePlayerDirection();
	
	
	}
	
	//PLACEHOLDER, DA RIMUOVERE
	public void speak2() {
		
		gp.ui.setCurrentDialogue(
				"La cassa era vuota, nessuna spada per Bergoglio, pensi\nche "
				+ "per un artigiano sia semplice fare tutto in poco tempo?\n"
				+ "Una demo completa richiede pazienza e tanta polvere bianca  .\n");
		facePlayerDirection();

	}
	
	public void facePlayerDirection() 
	{
		switch(gp.player.getDirection()) {
		case "up":
			setDirection("down");
			break;
		case "down":
			setDirection("up");
			break;
		case "left":
			setDirection("left");
			break;
		case "right":
			setDirection("right");
			break;
		}
	}
	
	
	public void update() {
		behaviour();
		
		setCollisionOn(false);
		gp.collisionChecker.checkTile(this);
		gp.collisionChecker.checkObject(this, false);
		gp.collisionChecker.checkNPCToPlayer(this);
		
		if(!isCollisionOn()) {
		switch(getDirection()) {
		case "up":
			setWorldY(getWorldY() - getSpeed());
			break;
		case "down":
			setWorldY(getWorldY() + getSpeed());
			break; 
		case "left":
			setWorldX(getWorldX() - getSpeed());
			break; 
		case "right":
			setWorldX(getWorldX() + getSpeed());
			break; 
		}
		}
			
		
	}
	
	
	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX();
		int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();
		
		// only draw if it's withing player's visual area
		if (worldX + (gp.tileSize) > gp.player.getWorldX() - gp.player.getScreenX()
				&& worldX - (gp.tileSize) < gp.player.getWorldX() + gp.player.getScreenX()
				&& worldY + (gp.tileSize) > gp.player.getWorldY() - gp.player.getScreenY()
				&& worldY - (gp.tileSize) < gp.player.getWorldY() + gp.player.getScreenY()) {

			switch (getDirection()) {
			case "up":
				image = up1;
				break;
			case "down":
				image = down1;
				break;
			case "left":
				image = left1;
				break;
			case "right":
				image = right1;
				break;
			}

		}

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}

	
	
	
	
	
	
	
	
	public boolean isCollisionOn() {
		return collisionOn;
	}

	public void setCollisionOn(boolean collisionOn) {
		this.collisionOn = collisionOn;
	}

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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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

	public int getActionCounter() {
		return actionCounter;
	}

	public void setActionCounter(int actionCounter) {
		this.actionCounter = actionCounter;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}

	public int getCurrentLife() {
		return currentLife;
	}

	public void setCurrentLife(int currentLife) {
		this.currentLife = currentLife;
	}

	
}
