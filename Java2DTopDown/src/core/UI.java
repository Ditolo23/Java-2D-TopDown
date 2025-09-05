package core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import items.OBJHeart;
import items.OBJHolyKey;
import items.SuperObj;
/*
 * new Font("type", font style, font size));
	do not put in draw, it's going to draw 60 times per sec, so declare it outside and put it in constructor
 	
 	g2.setFont(g2.getFont().deriveFont(Font ex: Font.PLAIN, values ex: 80F));  // fontSetter
 */

// takes care of onscreen UI ( item icons, text messages etc...)
public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica;
	BufferedImage heartFull, heartHalf, heartEmpty;

	int msgTimer = 0;

	private String currentDialogue = "";

	private int commandNum = 0;
	private int commandNumPause = 0;

	// ------------------------------------------------------

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		OBJHolyKey k = new OBJHolyKey(0, 0);
//		keyImage = k.image;

		// heart obj
		SuperObj heart = new OBJHeart(gp);
		heartFull = heart.image;
		heartHalf = heart.image2;
		heartEmpty = heart.image3;
	}


	public void draw(Graphics2D g2) {

		this.g2 = g2;
		g2.setFont(maruMonica);
		g2.setColor(Color.white);

		if (gp.gameState == gp.titleState) {
			drawTitle();
		}
		if (gp.gameState == gp.playState) {
			drawPlayerHealthBar();
		}

		if (gp.gameState == gp.pauseState) {
			drawPlayerHealthBar();
			drawPause();
		}

		if (gp.gameState == gp.dialogueState) {
			drawPlayerHealthBar();
			drawDialogue();
		}
	}

	// temporary
	public void drawCreditScreen(Graphics2D g2) {

		this.g2 = g2;

		g2.setColor(new Color(120, 120, 120));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// TITLE
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
		String text = "Credits/Info";
		int x = getCenteredTextX(text);
		int y = gp.tileSize;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		x = gp.tileSize/2;
		y += 40;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
		text = "Player";
		g2.drawString(text, x, y);

		y += 30;
		text = "Foto di Papa Bergoglio preso da internet";
		g2.drawString(text, x, y);

		y += 40;
		text = "OST";
		g2.drawString(text, x, y);

		y += 30;
		text = "Sleepy Frieren from Frieren: Beyond Journey's End composed by Evan Call";
		g2.drawString(text, x, y);

		y += 30;
		text = "Bensound - Elevator Bossa Nova";
		g2.drawString(text, x, y);

		y += 40;
		text = "NPC";
		g2.drawString(text, x, y);

		y += 30;
		text = "foto prese da internet digitando shiba/java coffe";
		g2.drawString(text, x, y);

		y += 40;
		text = "Terreno e oggetti";
		g2.drawString(text, x, y);

		y += 30;
		text = "grass + 2 variazioni di grass prese da itch.io (di Cainos Assets)";
		g2.drawString(text, x, y);

		y += 30;
		text = "il restante è casereccio ( fatto da me ) ";
		g2.drawString(text, x, y);

		y += 40;
		text = "Materiale usato per apprendere";
		g2.drawString(text, x, y);

		y += 30;
		text = "Guida di RyiSnow e varie nozioni da internet";
		g2.drawString(text, x, y);
		
		y += 40;
		text = "Per muoversi usare WASD";
		g2.drawString(text, x, y);
		
		y += 40;
		text = "Per interagire con lo shiba premere ENTER";
		g2.drawString(text, x, y);
		
		y += 40;
		text = "Casse e porte si aprono automaticamente se hai una chiave ed entri in collisione";
		g2.drawString(text, x, y);
		
		y += 80;
		text = "Premere ESC per uscire da questa sezione";
		g2.drawString(text, x, y);
		

	}

	// temporary
	public void ChestOpenMessage() {

		gp.gameState = gp.dialogueState;

		int x = gp.tileSize / 2;
		int y = gp.tileSize * 7 + (gp.tileSize / 2);
		int width = gp.screenWidth - (gp.tileSize);
		int heigth = gp.tileSize * 4;
		drawWindow(x, y, width, heigth);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
		x += gp.tileSize;
		y += gp.tileSize;

		String text = "Hai ottenuto la spada sacra(non è vero, prova a chiedere\n"
				+ " informazioni al cane che hai visto a inizio gioco)";
		setCurrentDialogue(text);
		g2.drawString(text, x, y);

		if (gp.keyH.enterPressed == true)
			gp.gameState = gp.playState;
	}

	public void drawPlayerHealthBar() {

		// setting up coordinates
		int x = 0;
		int y = 0;
		int i = 0;

		// empty hearts
		while (i < gp.player.getMaxLife() / 2) {
			g2.drawImage(heartEmpty, x, y, null);
			i++;
			x += gp.tileSize;
		}

		// resetting coordinates and i
		x = 0;
		y = 0;
		i = 0;

		// CURRENT HEALTH BAR
		while (i < gp.player.getCurrentLife()) {
			g2.drawImage(heartHalf, x, y, null);
			i++;
			if (i < gp.player.getCurrentLife()) {
				g2.drawImage(heartFull, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}

	// Title Screen
	public void drawTitle() {

		// BG COLOR
		g2.setColor(new Color(120, 120, 120));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

		// small memo
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
		String text = "*Demo incompleta, potrebbero esserci bug e non tutti i (bottoni) funzionano";
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// TITLE
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 130F));
		text = "Odissea di Bergoglio";
		x = getCenteredTextX(text);
		y = gp.tileSize * 3;

		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);

		// DRAW TITLE AFTER SHADOW
		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// faccia di Bergoglio
		x = gp.screenWidth / 2;
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.faceImage, x, y, gp.tileSize * 6, gp.tileSize * 6, null);

		// MENU
		x = getCenteredTextX(text);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 65F));
		text = "New Game";
		y = gp.tileSize * 6;

		g2.drawString(text, x, y);

		// commands
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Load";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Credits/Info";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}

		text = "Quit";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 3) {
			g2.drawString(">", x - gp.tileSize, y);
		}

	}

	// PAUSE SCREEN
	public void drawPause() {

		Color color = new Color(0, 0, 0, 120);

		g2.setColor(color);
		g2.fillRoundRect(gp.tileSize * 2, gp.tileSize * 3, (gp.screenWidth / 2) + (gp.screenWidth / 4),
				gp.screenHeight / 2, 35, 35);

		String text = "Tutti quanti abbiamo bisogno di una pausa...";
		int x = gp.tileSize;
		int y = gp.tileSize * 2;

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 72F));
		// SHADOW
		g2.setColor(Color.black);
		g2.drawString(text, x + 5, y + 5);

		g2.setColor(Color.white);
		g2.drawString(text, x, y);

		// RESUME
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
		text = "Resume";
		x = gp.tileSize * 3;
		y += (gp.tileSize * 2);
		g2.drawString(text, x, y);
		if (commandNumPause == 0) {
			g2.drawString(">", x - (gp.tileSize / 2), y);
		}

		// SOMETHING?
		text = "placeholder";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNumPause == 1) {
			g2.drawString(">", x - (gp.tileSize / 2), y);
		}

		// SOUND OPTIONS
		text = "placeholder(SO)";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNumPause == 2) {
			g2.drawString(">", x - (gp.tileSize / 2), y);
		}

		// SOMETHING?
		text = "placeholder";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNumPause == 3) {
			g2.drawString(">", x - (gp.tileSize / 2), y);
		}

		// TITLE SCREEN
		text = "Title Screen";
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNumPause == 4) {
			g2.drawString(">", x - (gp.tileSize / 2), y);
		}

		// java Coffee img
		BufferedImage img = setupImgForUI("npc", "javaCoffee");
		x += (gp.tileSize * 6);
		y -= (gp.tileSize * 4);
		g2.drawImage(img, x, y, gp.tileSize * 4, gp.tileSize * 4, null);

	}

	// DIALOGUE SCREEN
	public void drawDialogue() {

		// window
		int x = gp.tileSize / 2;
		int y = gp.tileSize * 7 + (gp.tileSize / 2);
		int width = gp.screenWidth - (gp.tileSize);
		int heigth = gp.tileSize * 4;
		drawWindow(x, y, width, heigth);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);

			y += 40;

		}
	}

	// parameters are coordinates, width and heigth of the window
	public void drawWindow(int x, int y, int width, int heigth) {

		Color color = new Color(0, 0, 0, 100);
		g2.setColor(color); // or Color.black
		g2.fillRoundRect(x, y, width, heigth, 35, 35);

		// white
		color = new Color(255, 255, 255);
		g2.setColor(color);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, heigth - 10, 25, 25);

	}

	// CENTER TEXT FOR X axis
	public int getCenteredTextX(String text) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;

	}

	// setup method ( from entity )
	public BufferedImage setupImgForUI(String packageName, String imageName) {
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

	public String getCurrentDialogue() {
		return currentDialogue;
	}

	public void setCurrentDialogue(String currentDialogue) {
		this.currentDialogue = currentDialogue;
	}

	public int getCommandNum() {
		return commandNum;
	}

	public void setCommandNum(int commandNum) {
		this.commandNum = commandNum;
	}

	public int getCommandNumPause() {
		return commandNumPause;
	}

	public void setCommandNumPause(int commandNumPause) {
		this.commandNumPause = commandNumPause;
	}

}
