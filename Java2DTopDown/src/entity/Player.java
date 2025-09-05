package entity;

// import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import core.GamePanel;
import core.KeyHandler;

public class Player extends Entity {
	
	//temporary VARIABLE
	public boolean checkIfChestOpen = false;
	
	KeyHandler keyH;

	final int screenX;
	final int screenY;

	private int keyAmount = 0;

	// constructor
	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		setPackageName("player");

		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		// SOLID AREA FOR COLLISION
		solidArea = new Rectangle(13, 26, 54, 54);

		setSolidAreaDefaultX(solidArea.x);
		setSolidAreaDefaultY(solidArea.y);

		setDefVal();

	}

	// default values for main character, it will spawn facing down
	public void setDefVal() {

		setWorldX(50 * gp.tileSize);
		setWorldY(50 * gp.tileSize);
		setSpeed(6);
		
		setDirection("down");
		getPlayerImage();
		
		//player stats
		setMaxLife(6);
		setCurrentLife(getMaxLife());

	}

	// updates the direction of the main character
	public void update() {

		int newX = getWorldX();
		int newY = getWorldY();

		if (keyH.upPressed) {
			setDirection("up");
			newY -= getSpeed();
		} else if (keyH.downPressed) {
			setDirection("down");
			newY += getSpeed();
		} else if (keyH.leftPressed) {
			setDirection("left");
			newX -= getSpeed();
		} else if (keyH.rightPressed) {
			setDirection("right");
			newX += getSpeed();
		}

		// check collision
		setCollisionOn(false);
		gp.collisionChecker.checkTile(this);

		// check obj collision, this method is from CollisionChecker
		int objIndex = gp.collisionChecker.checkObject(this, true);
		pickUpObject(objIndex);

		// check collision with npc, this method is from CollisionChecker
		int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
		CollisionNPC(npcIndex);

		// if collision is false, player move
		if (!isCollisionOn()) {
			setWorldX(newX);
			setWorldY(newY);

//			switch(getDirection()) {
//			case "up":
//				setWorldY(getWorldY() - getSpeed());
//				break;
//			case "down":
//				setWorldY(getWorldY() + getSpeed());
//				break;
//			case "left":
//				setWorldX(getWorldX() - getSpeed());
//				break;
//			case "right":
//				setWorldX(getWorldX() + getSpeed());
//				break;	
//			}
		}

	}

	// note to myself, should change name later one
	public void pickUpObject(int i) {

		// if i reaches 999 it means there was a collision with an item/object
		if (i != 999 && gp.obj[i] != null) {

			String objName = gp.obj[i].getName();

			switch (objName) {
			case "HolyKey":
				keyAmount++;
				gp.obj[i] = null;
//				System.out.println("Holy key + 1");
				break;
			case "DungeonDoor":
				if (keyAmount > 0) {
					keyAmount--;
					gp.obj[i] = null;
//				System.out.println("Holy key used");
					gp.SoundEff(1);
				}
				break;
			case "Chest":
				
				if (keyAmount > 0) {
					keyAmount--;
					try {
						
						checkIfChestOpen = true;
						gp.obj[i].setName("ChestOpen");
						gp.obj[i].image = ImageIO.read(getClass().getResourceAsStream("/items_And_Objects/ChestOpen.png"));
						gp.ui.ChestOpenMessage();
						

					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}

	
	// also HAS NPC DIALOGUE
	public void CollisionNPC(int i) {

		if (i != 999) {

			if (gp.keyH.enterPressed) {
				
				gp.gameState = gp.dialogueState;
				gp.ui.setCurrentDialogue(dialogues[dialogueIndex]);
				
				if(!checkIfChestOpen)
				gp.npc[i].speak(); 
				else if(checkIfChestOpen)
				gp.npc[i].speak2();
				
			}

		}

			gp.keyH.enterPressed = false;
	}
	
	
	// " draws " the character, not sure how to explain it
	public void draw(Graphics2D g2) {
		/*
		 * g2.setColor(Color.white);
		 * 
		 * g2.fillRect(getX(), getY(), gp.tileSize, gp.tileSize);
		 */
		BufferedImage image = null;

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
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		// getWorldX(), getWorldY()
	}

	// each direction gets assigned to a sprite
	public void getPlayerImage() {
		try {

			up1 = setup("B");
			down1 = setup("B");
			left1 = setup("B");
			right1 = setup("B");
			
			// volto di bergoglio, " copertina " 
			faceImage = ImageIO.read(getClass().getResourceAsStream("/player/popeFrancis.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getScreenX() {
		return screenX;
	}

	public int getScreenY() {
		return screenY;
	}

	public int getKeyAmount() {
		return keyAmount;
	}

	public void setKeyAmount(int keyA) {
		keyAmount = keyA;
	}

}
