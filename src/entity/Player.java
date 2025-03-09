package entity;


import object.OBJ_Rune_Door;
import object.OBJ_Rune_Step;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	int afkCounter;
	public int orbCount = 0;
	boolean doorActivated = false;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = gp.tileSize / 6;
		solidArea.y = gp.tileSize / 3;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = gp.tileSize - (gp.tileSize / 3);
		solidArea.height = gp.tileSize - (gp.tileSize / 3);
		
		setDefaultValues();
		getPlayerImage();
		direction = "idle";
		
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 25;
		worldY = gp.tileSize * 25;
		speed = 6;
		afkCounter = 0;
		
	}
	
	public void getPlayerImage(){
		
		idle = setup("player_idle");
		up0 = setup("player_up_0");
		up1 = setup("player_up_1");
		up2 = setup("player_up_2");
		down0 = setup("player_down_0");
		down1 = setup("player_down_1");
		down2 = setup("player_down_2");
		left0 = setup("player_left_0");
		left1 = setup("player_left_1");
		left2 = setup("player_left_2");
		right0 = setup("player_right_0");
		right1 = setup("player_right_1");
		right2 = setup("player_right_2");
	
	}
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName +".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize, true);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if (keyH.upPressed == true) {
				direction = "up";
			}
			
			else if (keyH.downPressed == true) {
				direction = "down";
			}
			
			else if (keyH.leftPressed == true) {
				direction = "left";
			}
			
			else if (keyH.rightPressed == true) {
				direction = "right";
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				switch(direction) {
					case "up": worldY -= speed; break;
					case "down": worldY += speed; break;
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
			}
			
			afkCounter = 0;
			spriteCounter++;
			
			if (spriteCounter > 12) {
	            spriteNum = (spriteNum + 1) % 3; // Cycle spriteNum between 0, 1, 2
	            spriteCounter = 0;
	        }
				
		}
		
		else {
			afkCounter++;
			
			if (afkCounter > 20) {
				direction = "idle";
			}
		}
		
	}
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "lightOrb":
				gp.playSE(1);
				orbCount++;
				gp.obj[i] = null;
				break;
				
			case "runeDoor":
				break;
				
			case "runeStep":
			    if(orbCount > 0 && !((OBJ_Rune_Step) gp.obj[i]).runeStepActivated) {
			        orbCount--;
			        ((OBJ_Rune_Step) gp.obj[i]).runeStepActivated = true;
			        gp.playSE(2);

			        // Find the nearest rune door and activate a rune on it
			        for (int j = 0; j < gp.obj.length; j++) {
			            if (gp.obj[j] instanceof OBJ_Rune_Door) {
			                OBJ_Rune_Door door = (OBJ_Rune_Door) gp.obj[j];

			                // Check if the door is nearby
			                if (Math.abs(door.worldX - worldX) < gp.tileSize * 10 &&
			                    Math.abs(door.worldY - worldY) < gp.tileSize * 10) {
			                    door.activateRune();
			                    
			                    // check if door nearby is activated
				                if(door.runeDoorActivated) {
				                	gp.playSE(3);
				                }
			                    break;
			                }
			            }
			        }
			    }
			    break;

				
			case "pitfall":
				System.out.println("Pitfall!");
				
				// TODO pitfall action
				break;
				
			case "stairs":
				System.out.println("Stairs!");
				
				// TODO pitfall action
				// load new map
				// reset world
				break;	
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		// Update the image based on the direction
	    switch (direction) {
	        case "up":
	            image = (spriteNum == 0) ? up0 : (spriteNum == 1) ? up1 : up2;
	            break;

	        case "down":
	            image = (spriteNum == 0) ? down0 : (spriteNum == 1) ? down1 : down2;
	            break;

	        case "left":
	            image = (spriteNum == 0) ? left0 : (spriteNum == 1) ? left1 : left2;
	            break;

	        case "right":
	            image = (spriteNum == 0) ? right0 : (spriteNum == 1) ? right1 : right2;
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
