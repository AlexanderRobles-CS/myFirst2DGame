package main;

import object.OBJ_Light_Orb;
import object.OBJ_Rune_Door;
import object.OBJ_Rune_Step;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Light_Orb();
		gp.obj[0].worldX = 5 * gp.tileSize;
		gp.obj[0].worldY = 5 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Rune_Door();
		gp.obj[1].worldX = 7 * gp.tileSize;
		gp.obj[1].worldY = 7 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Rune_Step();
		gp.obj[2].worldX = 9 * gp.tileSize;
		gp.obj[2].worldY = 9 * gp.tileSize;

	}
}
