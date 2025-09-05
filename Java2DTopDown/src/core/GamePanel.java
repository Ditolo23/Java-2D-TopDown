package core;

import java.awt.*;

import tile.TileManager;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import items.OBJChest;
import items.SuperObj;

public class GamePanel extends JPanel implements Runnable {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public final int originalTileSize = 16; // 16 x 16
	public final int scale = 5;

	public final int tileSize = originalTileSize * scale; // 80 x 80
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	// WORLD SETTINGS
	public final int maxWorldCol = 65;
	public final int maxWorldRow = 65;

//	public final int worldWidth = tileSize * maxScreenCol;
//	public final int worldHeight = tileSize * maxScreenRow;

	// FPS, check at VOID RUN METHOD
	int FPS = 60;

	public KeyHandler keyH = new KeyHandler(this);
	TileManager tileM = new TileManager(this);

	// COLLISION
	public CollisionChecker collisionChecker = new CollisionChecker(this);

	// SOUND and MUSIC
	Sound sound = new Sound();
	Sound music = new Sound();

	// *nota, vedere a cosa servono i threads 
	Thread gameThread;

	// OBJECTS

	// Player
	public Player player = new Player(this, keyH);

	// it means we are preparing some slots for objects and can replace them during game
	
	public SuperObj[] obj = new SuperObj[10];
	public Entity[] npc = new Entity[10]; 

	// Asset Placer
	AssetPlacer AssetPlacer = new AssetPlacer(this);

	// UI
	public UI ui = new UI(this);

	// Game State
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	
	
	// temporary
	public final int creditState = 4;
	
	// costruttore
	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

	}

	// setupGame
	public void setupOBJ() {
		AssetPlacer.setObject();
		AssetPlacer.setNPC();
		//playMusic(3);
		
//		gameState = pauseState;
    	gameState = titleState;
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	//should delete afterwards, just for testing purpouses
	public void npcUpdate() {
		for(int i = 0; i<npc.length; i++) {
			if(npc[i] != null)
				npc[i].update();
		}
	}

	public void update() {

		if (gameState == playState) {
			player.update();
			
			
			npcUpdate();
		}

		if (gameState == pauseState) {

		}

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//title state
		if(gameState == titleState) {
			ui.draw(g2);
		} else if(gameState == creditState) {
			ui.drawCreditScreen(g2);
		} else {
		// Tiles
		tileM.draw(g2);

		// Items/OBJ
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				obj[i].draw(g2, this);
			}
			
		}
		
		// npc
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				npc[i].draw(g2);
			}
		}

		// Player
		player.draw(g2);

		// UI
		ui.draw(g2);

		g2.dispose(); // dispose of this graphics context and release any system resources that it is
						// using
		}
	}

	// MUSIC SECTION
	public void playMusic(int i) {

		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void resumeMusic() {
		music.resume();
	}
	
	public void stopMusic() {
		music.stop2();
	}
	
	public void stop() {
		music.stop();
	}
	
	public void playPauseMusic() {
		
		music.playPauseMusic();
		music.loopPauseMusic();;
	}
	
	public void stopPauseMusic() {
		music.stopPauseMusic();
	}
	
	// THIS ONE TAKES CARE OF SOUND EFFECTS
	public void SoundEff(int i) {
		sound.setFile(i);
		sound.play();
	}
	
	@Override

	// delta method
	public void run() {

		final int FPS = 60;

		final double timePerFrame = 1_000_000_000.0 / FPS;

		long previousTime = System.nanoTime();
		double delta = 0;
		long currentTime = 0;
		long lastTime = System.nanoTime();
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			long elapsedTime = currentTime - previousTime;
			previousTime = currentTime;

			delta += elapsedTime / timePerFrame;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				// System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
