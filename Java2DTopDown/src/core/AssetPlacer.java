package core;

import entity.NPC_Guide;
import items.OBJChest;
import items.OBJDungeonDoor;
import items.OBJHolyKey;

public class AssetPlacer {

	GamePanel gp;
	
	public AssetPlacer(GamePanel gp) {
		this.gp = gp;
		
	}
	
	
	// this will set objects on the map
	public void setObject() {
		
		// should always draw by using coordinates * tileSize
		
		
    	gp.obj[0] = new OBJChest(47 * gp.tileSize, 9*gp.tileSize);
		 
		gp.obj[2] = new OBJHolyKey(42 * gp.tileSize, 11*gp.tileSize);
		
		gp.obj[3] = new OBJDungeonDoor(48 * gp.tileSize, 22*gp.tileSize);
		
		gp.obj[4] = new OBJHolyKey(30 * gp.tileSize, 24*gp.tileSize);
		
	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_Guide(gp);
		gp.npc[0].setWorldX(52 * gp.tileSize);
		gp.npc[0].setWorldY(52 * gp.tileSize);
	}
}
