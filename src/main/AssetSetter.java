package main;

import object.OBJ_Light_Orb;
import object.OBJ_Pitfall;
import object.OBJ_Rune_Door;
import object.OBJ_Rune_Step;
import object.OBJ_Stairs;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Light_Orb(gp);
		gp.obj[0].worldX = 9 * gp.tileSize;
		gp.obj[0].worldY = 5 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Rune_Door(gp);
		gp.obj[1].worldX = 7 * gp.tileSize;
		gp.obj[1].worldY = 7 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Rune_Step(gp);
		gp.obj[2].worldX = 9 * gp.tileSize;
		gp.obj[2].worldY = 9 * gp.tileSize;
		
		gp.obj[3] = new OBJ_Pitfall(gp);
		gp.obj[3].worldX = 3 * gp.tileSize;
		gp.obj[3].worldY = 3 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Stairs(gp);
		gp.obj[4].worldX = 12 * gp.tileSize;
		gp.obj[4].worldY = 5 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Light_Orb(gp);
		gp.obj[5].worldX = 6 * gp.tileSize;
		gp.obj[5].worldY = 9 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Light_Orb(gp);
		gp.obj[6].worldX = 6 * gp.tileSize;
		gp.obj[6].worldY = 3 * gp.tileSize;
		
		gp.obj[7] = new OBJ_Rune_Step(gp);
		gp.obj[7].worldX = 13 * gp.tileSize;
		gp.obj[7].worldY = 10 * gp.tileSize;
		
		gp.obj[8] = new OBJ_Rune_Step(gp);
		gp.obj[8].worldX = 11 * gp.tileSize;
		gp.obj[8].worldY = 15 * gp.tileSize;

	}
}
