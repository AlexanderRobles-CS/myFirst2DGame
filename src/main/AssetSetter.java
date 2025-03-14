package main;

import object.OBJ_Skull;
import entity.NPC_Necromancer;
import monster.MON_Skeleton;
import object.OBJ_Pitfall;
import object.OBJ_Rune_Door;
import object.OBJ_Rune_Step;
import object.OBJ_Stairs;
import object.OBJ_Torch;

public class AssetSetter {
	
	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Skull(gp);
		gp.obj[0].worldX = 25 * gp.tileSize;
		gp.obj[0].worldY = 42 * gp.tileSize;
		
		gp.obj[1] = new OBJ_Rune_Door(gp);
		gp.obj[1].worldX = 25 * gp.tileSize;
		gp.obj[1].worldY = 35 * gp.tileSize;
		
		gp.obj[2] = new OBJ_Rune_Step(gp);
		gp.obj[2].worldX = 20 * gp.tileSize;
		gp.obj[2].worldY = 39 * gp.tileSize;
		
		gp.obj[3] = new OBJ_Pitfall(gp);
		gp.obj[3].worldX = 3 * gp.tileSize;
		gp.obj[3].worldY = 3 * gp.tileSize;
		
		gp.obj[4] = new OBJ_Stairs(gp);
		gp.obj[4].worldX = 12 * gp.tileSize;
		gp.obj[4].worldY = 5 * gp.tileSize;
		
		gp.obj[5] = new OBJ_Skull(gp);
		gp.obj[5].worldX = 9 * gp.tileSize;
		gp.obj[5].worldY = 31 * gp.tileSize;
		
		gp.obj[6] = new OBJ_Skull(gp);
		gp.obj[6].worldX = 41 * gp.tileSize;
		gp.obj[6].worldY = 31 * gp.tileSize;
		
		gp.obj[7] = new OBJ_Rune_Step(gp);
		gp.obj[7].worldX = 30 * gp.tileSize;
		gp.obj[7].worldY = 39 * gp.tileSize;
		
		gp.obj[8] = new OBJ_Rune_Step(gp);
		gp.obj[8].worldX = 25 * gp.tileSize;
		gp.obj[8].worldY = 37 * gp.tileSize;
		
		gp.obj[9] = new OBJ_Torch(gp);
		gp.obj[9].worldX = 24 * gp.tileSize;
		gp.obj[9].worldY = 35 * gp.tileSize;
		
		gp.obj[10] = new OBJ_Torch(gp);
		gp.obj[10].worldX = 26 * gp.tileSize;
		gp.obj[10].worldY = 35 * gp.tileSize;

	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_Necromancer(gp);
		gp.npc[0].worldX = gp.tileSize * 25;
		gp.npc[0].worldY = gp.tileSize * 36;
		
	}
	
	public void setMonster() {
		gp.monster[0] = new MON_Skeleton(gp);
		gp.monster[0].worldX = gp.tileSize * 7;
		gp.monster[0].worldY = gp.tileSize * 33;
		
		gp.monster[1] = new MON_Skeleton(gp);
		gp.monster[1].worldX = gp.tileSize * 45;
		gp.monster[1].worldY = gp.tileSize * 33;
		
		gp.monster[2] = new MON_Skeleton(gp);
		gp.monster[2].worldX = gp.tileSize * 17;
		gp.monster[2].worldY = gp.tileSize * 43;
		
		gp.monster[3] = new MON_Skeleton(gp);
		gp.monster[3].worldX = gp.tileSize * 40;
		gp.monster[3].worldY = gp.tileSize * 33;
	}
}
