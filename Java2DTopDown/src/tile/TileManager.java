package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import core.GamePanel;
import core.UtilityTools;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	Tile[] tile2;
	BufferedImage tileset;
	int tileSize = 16;
	public int[][] mapTileNum; // Store map data here

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tile = new Tile[99];
		// tile2 = new Tile[256];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldCol];

		loadMap();

		getTileImage();
	}

	public void getTileImage() {
		
			setup(0, "Grass1", false); // placeholders, drawing maps is easier when numbers have same digits
			setup(1, "Grass1", false);
			setup(2, "Grass1", false);
			setup(3, "Grass1", false);
			setup(4, "Grass1", false);
			setup(5, "Grass1", false);
			setup(6, "Grass1", false);
			setup(7, "Grass1", false);
			setup(8, "Grass1", false);
			setup(9, "Grass1", false);
			
			// grass and variants, collision false
			setup(10, "Grass1", false); 
			setup(11, "Grass2", false);
			setup(12, "Grass3", false); 
			
			// wall, collision true
			setup(13, "wall1", true);   
			
			// water tiles, collision true, water alt at 44
			setup(14, "WaterTopLeft", true); 
			setup(15, "WaterTop", true);
			setup(16, "WaterTopRight", true);
			setup(17, "WaterLeft", true);
			setup(18, "Water", true);           // MAIN WATER TILE
			setup(19, "WaterRight", true);
			setup(20, "WaterBotLeft", true);
			setup(21, "WaterBot", true);
			setup(22, "WaterBotRight", true);
			// water edge tiles
			setup(23, "WaterTopLeftEdge", true);
			setup(24, "WaterTopRightEdge", true);
			setup(25, "WaterBotLeftEdge", true);
			setup(26, "WaterBotRightEdge", true);
			
			// dirt tiles
			setup(27, "DirtTopLeft", false);
			setup(28, "DirtTop", false);
			setup(29, "DirtTopRight", false);
			setup(30, "DirtLeft", false);
			setup(31, "Dirt", false); 		 // MAIN DIRT TILE
			setup(32, "DirtRight", false);
			setup(33, "DirtBotLeft", false); 
			setup(34, "DirtBot", false);
			setup(35, "DirtBotRight", false);
			
			// dirt edges erroraccio 
			setup(36, "DirtTopLeftEdge", false);
			
			//dungeon tiles
			setup(37, "DungeonTile1", false);
			setup(38, "DungeonTile2", false);
			setup(39, "DungeonTile3", false);
			
			// remaning dirt edges
			setup(40, "DirtTopRightEdge", false);
			setup(41, "DirtBotLeftEdge", false);
			setup(42, "DirtBotRightEdge", false);
			
			// tree
			setup(43, "Tree", true);
			//water alt
			setup(44, "Water2", true);
			
	}
	
	public void setup(int i, String imagePath, boolean collision) {
		
		UtilityTools uTool = new UtilityTools();
		
		try {
			tile[i] = new Tile();
			tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath + ".png"));
			tile[i].image = uTool.scaleImage(tile[i].image, gp.tileSize, gp.tileSize);
			tile[i].setCollision(collision);
		} catch( IOException e) {
			e.printStackTrace();
		}
		
	}

	// loads the map with a txt file, basically a series of numbers followed by spacebar

	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/FirstMap.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int row = 0;
			while (row < gp.maxWorldRow) {
				String line = br.readLine();
				if (line == null)
					break;

				String[] numbers = line.trim().split(" ");
				for (int col = 0; col < gp.maxWorldCol; col++) {
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
				}
				row++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cocc cos è iut stort");
		}
	}

	// with camera that " follows " the player
	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;
		// int x = 0;
		// int y = 0;

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

			int tileNum = mapTileNum[worldCol][worldRow];

			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;

			int screenX = worldX - gp.player.getWorldX() + gp.player.getScreenX(); // gets correct coordinates, offsets
																					// the difference
			int screenY = worldY - gp.player.getWorldY() + gp.player.getScreenY();

			if (worldX + (gp.tileSize) > gp.player.getWorldX() - gp.player.getScreenX()
					&& worldX - (gp.tileSize) < gp.player.getWorldX() + gp.player.getScreenX()
					&& worldY + (gp.tileSize) > gp.player.getWorldY() - gp.player.getScreenY()
					&& worldY - (gp.tileSize) < gp.player.getWorldY() + gp.player.getScreenY()) {

				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
				
			}
			
			worldCol++;
			// x += gp.tileSize;

			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				// x = 0;
				worldRow++;
				// y += gp.tileSize;
			}
		}

	}

	public void draw2(Graphics2D g2) {

		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);

		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;

			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}

	}

}
