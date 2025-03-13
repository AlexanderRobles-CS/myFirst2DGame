package main;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = gp.tileSize / 2 - 2;
			eventRect[col][row].y = gp.tileSize / 2 - 2;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
	}
	
	public void checkEvent() {
		
		// check if the player character is more than 1 tile away from the last event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			if(hit(3, 3, "any") == true) {pitfall(3, 3, gp.dialougeState);}
			if(hit(1, 1, "any") == true) {healingPool(1, 1, gp.dialougeState);}
			if(hit(2, 2, "any") == true) {teleport(2, 2, gp.dialougeState);}
		}
	}
	
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row])) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any") && eventRect[col][row].eventDone == false) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}

	public void pitfall(int col, int row, int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialouge = "You fell into a pit!";
		gp.player.life -= 1;
		//eventRect[col][row].eventDone = true;   // one time event
		canTouchEvent = false;                    // one time event but can touch again after getting away at least one tile
	}
	
	public void healingPool(int col, int row, int gameState) {
		if(gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.ui.currentDialouge = "You gain health :D";
			gp.player.life += 1;
		}
	}
	
	public void teleport(int col, int row, int gameState) {
			gp.gameState = gameState;
			gp.ui.currentDialouge = "Teleport";
			gp.player.worldX = gp.tileSize * 10;
			gp.player.worldY = gp.tileSize * 10;
	}
}
