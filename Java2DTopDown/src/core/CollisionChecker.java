package core;

import java.awt.Rectangle;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity e) {

		// e is for ENTITY

		int eLeftWorldX = e.getWorldX() + e.solidArea.x;

		int eRightWorldX = e.getWorldX() + e.solidArea.x + e.solidArea.width;
		int eTopWorldY = e.getWorldY() + e.solidArea.y;
		int eBotWorldY = e.getWorldY() + e.solidArea.y + e.solidArea.height;

		int eLeftCol = eLeftWorldX / gp.tileSize;
		int eRightCol = eRightWorldX / gp.tileSize;
		int eTopRow = eTopWorldY / gp.tileSize;
		int eBotRow = eBotWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		// direction is a string
		switch (e.getDirection()) {

		case "up":
			eTopRow = (eTopWorldY - e.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[eLeftCol][eTopRow];
			tileNum2 = gp.tileM.mapTileNum[eRightCol][eTopRow];
			if (gp.tileM.tile[tileNum1].isCollision() == true || gp.tileM.tile[tileNum2].isCollision() == true)
				e.setCollisionOn(true);
			break;
		case "down":
			eBotRow = (eBotWorldY + e.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[eLeftCol][eBotRow];
			tileNum2 = gp.tileM.mapTileNum[eRightCol][eBotRow];
			if (gp.tileM.tile[tileNum1].isCollision() == true || gp.tileM.tile[tileNum2].isCollision() == true)
				e.setCollisionOn(true);
			break;
		case "left":
			eLeftCol = (eLeftWorldX - e.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[eLeftCol][eTopRow];
			tileNum2 = gp.tileM.mapTileNum[eLeftCol][eBotRow];
			if (gp.tileM.tile[tileNum1].isCollision() == true || gp.tileM.tile[tileNum2].isCollision() == true)
				e.setCollisionOn(true);
			break;
		case "right":
			eRightCol = (eRightWorldX + e.getSpeed()) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[eRightCol][eTopRow];
			tileNum2 = gp.tileM.mapTileNum[eRightCol][eBotRow];
			if (gp.tileM.tile[tileNum1].isCollision() == true || gp.tileM.tile[tileNum2].isCollision() == true)
				e.setCollisionOn(true);
			break;

		}
	}
	
	//check player with entities(npc, mobs etc...)
	public int checkEntity(Entity e, Entity[] target) {

		int index = 999;

		for (int i = 0; i < target.length; i++) {

			if (target[i] != null) {

				Rectangle entityArea = new Rectangle(e.getWorldX() + e.getSolidAreaDefaultX(),
						e.getWorldY() + e.getSolidAreaDefaultY(), e.solidArea.width, e.solidArea.height);

				Rectangle objectArea = new Rectangle(target[i].getWorldX() + target[i].getSolidAreaDefaultX(),
						target[i].getWorldY() + target[i].getSolidAreaDefaultY(), target[i].solidArea.width,
						target[i].solidArea.height);

				switch (e.getDirection()) {
				case "up":
					entityArea.y -= e.getSpeed();
					if (entityArea.intersects(objectArea)) {

// no need to check collision as npc's/mobs are solid
// no need to check player			
//						if (target[i].isCollisionOn()) {
//							e.setCollisionOn(true);
//						}
//						if (player) {
//							index = i;
//						}
						e.setCollisionOn(true);
						index = i;
					}
					break;
				case "down":
					entityArea.y += e.getSpeed();
					if (entityArea.intersects(objectArea)) {
						e.setCollisionOn(true);
						index = i;
					}
					break;
				case "left":
					entityArea.x -= e.getSpeed();
					if (entityArea.intersects(objectArea)) {
						e.setCollisionOn(true);
						index = i;
					}
					break;
				case "right":
					entityArea.x += e.getSpeed();
					if (entityArea.intersects(objectArea)) {
						e.setCollisionOn(true);
						index = i;
					}
					break;
				}

				// reset values so it doesn't keep adding, solidAreaDefaultX helps with this
				// objective
				e.solidArea.x = e.getSolidAreaDefaultX();
				e.solidArea.y = e.getSolidAreaDefaultY();

				target[i].solidArea.x = target[i].getSolidAreaDefaultX();
				target[i].solidArea.y = target[i].getSolidAreaDefaultY();

			}
		}
		return index;
	}
	
	public void checkNPCToPlayer(Entity e) {
		
		Rectangle entityArea = new Rectangle(e.getWorldX() + e.getSolidAreaDefaultX(),
				e.getWorldY() + e.getSolidAreaDefaultY(), e.solidArea.width, e.solidArea.height);

		Rectangle objectArea = new Rectangle(gp.player.getWorldX() + gp.player.getSolidAreaDefaultX(),
				gp.player.getWorldY() + gp.player.getSolidAreaDefaultY(), gp.player.solidArea.width,
				gp.player.solidArea.height);

		switch (e.getDirection()) {
		case "up":
			entityArea.y -= e.getSpeed();
			if (entityArea.intersects(objectArea)) {
					e.setCollisionOn(true);
			}
			break;
		case "down":
			entityArea.y += e.getSpeed();
			if (entityArea.intersects(objectArea)) {
				e.setCollisionOn(true);
			}
			break;
		case "left":
			entityArea.x -= e.getSpeed();
			if (entityArea.intersects(objectArea)) {
				e.setCollisionOn(true);
			}
			break;
		case "right":
			entityArea.x += e.getSpeed();
			if (entityArea.intersects(objectArea)) {
				e.setCollisionOn(true);
			}
			break;
		}

		// reset values so it doesn't keep adding, solidAreaDefaultX helps with this
		// objective
		e.solidArea.x = e.getSolidAreaDefaultX();
		e.solidArea.y = e.getSolidAreaDefaultY();

		gp.player.solidArea.x = gp.player.getSolidAreaDefaultX();
		gp.player.solidArea.y = gp.player.getSolidAreaDefaultY();

	}
	

	// ** NOTE, could have used intersect with tile collision aswell, however it
	// would have been inefficient as
	// we only need to check the surrounding tiles, with checkObject we only need to
	// scan a limited amount of items
	// so it won't be " heavy "
	// returns the type of the object/item the player touches ( has collision with )
	public int checkObject(Entity e, boolean player) {

		int index = 999;

		for (int i = 0; i < gp.obj.length; i++) {

			if (gp.obj[i] != null) {

//				 get entity's solid area position
				// Create temporary rectangles for collision detection
				Rectangle entityArea = new Rectangle(e.getWorldX() + e.getSolidAreaDefaultX(),
						e.getWorldY() + e.getSolidAreaDefaultY(), e.solidArea.width, e.solidArea.height);
//				e.solidArea.x = e.getWorldX() + e.solidArea.x;
//				e.solidArea.y = e.getWorldY() + e.solidArea.y;

				// get object's solid area position
				Rectangle objectArea = new Rectangle(gp.obj[i].getWorldX() + gp.obj[i].getSolidAreaDefaultX(),
						gp.obj[i].getWorldY() + gp.obj[i].getSolidAreaDefaultY(), gp.obj[i].solidArea.width,
						gp.obj[i].solidArea.height);
//				gp.obj[i].solidArea.x = gp.obj[i].getWorldX() + gp.obj[i].solidArea.x;
//				gp.obj[i].solidArea.y = gp.obj[i].getWorldY() + gp.obj[i].solidArea.y;

				switch (e.getDirection()) {
				case "up":
					entityArea.y -= e.getSpeed();
					if (entityArea.intersects(objectArea)) {
						if (gp.obj[i].isCollision()) {
							e.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}

					}
					break;
				case "down":
					entityArea.y += e.getSpeed();
					if (entityArea.intersects(objectArea)) {
						if (gp.obj[i].isCollision()) {
							e.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "left":
					entityArea.x -= e.getSpeed();
					if (entityArea.intersects(objectArea)) {
						if (gp.obj[i].isCollision()) {
							e.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}
					}
					break;
				case "right":
					entityArea.x += e.getSpeed();
					if (entityArea.intersects(objectArea)) {

						if (gp.obj[i].isCollision()) {
							e.setCollisionOn(true);
						}
						if (player) {
							index = i;
						}
					}
					break;
				}

				// reset values so it doesn't keep adding, solidAreaDefaultX helps with this
				// objective
				e.solidArea.x = e.getSolidAreaDefaultX();
				e.solidArea.y = e.getSolidAreaDefaultY();

				gp.obj[i].solidArea.x = gp.obj[i].getSolidAreaDefaultX();
				gp.obj[i].solidArea.y = gp.obj[i].getSolidAreaDefaultY();

			}
		}
		return index;
	}
}
