package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up0, up1, up2, up3, down0, down1, down2, down3, left0, left1, left2, left3, right0, right1, right2, right3, idle;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public int actionLookCounter = 0;
	
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	String dialouges[] = new String[20];
	int dialougeIndex = 0;
	
	
	public Entity (GamePanel gp) {
		this.gp = gp;
		this.solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
	}
	
	public BufferedImage setup(String imagePath, boolean player) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize, player);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void setAction() {}
	public void speak() {
		
		if(dialouges[dialougeIndex] == null) {
			dialougeIndex = 0;
		}
		gp.ui.currentDialouge = dialouges[dialougeIndex];
		dialougeIndex++;
		
		switch(gp.player.direction) {
			case "up":
				direction = "down";
				break;
				
			case "down":
				direction = "up";
				break;
				
			case "left":
				direction = "right";
				break;
				
			case "right":
				direction = "left";
				break;
		}
	}
	
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkPlayer(this);
		
		
		// IF COLLISION IS FALSE, PLAYER CAN MOVE
		if(collisionOn == false) {
			switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
					
			spriteCounter++;
					
		if(spriteCounter > 12) {
			spriteNum = (spriteNum + 1) % 4;
			spriteCounter = 0;
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX 
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX 
					&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY 
						&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			// Update the image based on the direction
			switch (direction) {
		    case "up":
		        image = (spriteNum == 0) ? up0 : (spriteNum == 1) ? up1 : (spriteNum == 2) ? up2 : up3;
		        break;

		    case "down":
		        image = (spriteNum == 0) ? down0 : (spriteNum == 1) ? down1 : (spriteNum == 2) ? down2 : down3;
		        break;

		    case "left":
		        image = (spriteNum == 0) ? left0 : (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : left3;
		        break;

		    case "right":
		        image = (spriteNum == 0) ? right0 : (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : right3;
		        break;

		    case "idle":
		        image = idle;
		        break;

		    default:
		        break;

		    }
			
			g2.drawImage(image, screenX, screenY, null);
		
		}
			
	}
	
}
