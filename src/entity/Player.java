package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	int afkCounter;
	float opacity;
	
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
		solidArea.width = gp.tileSize - (gp.tileSize / 3);
		solidArea.height = gp.tileSize - (gp.tileSize / 3);
		
		setDefaultValues();
		getPlayerImage();
		direction = "idle";
		
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 10;
		worldY = gp.tileSize * 10;
		speed = 6;
		afkCounter = 0;
		opacity = 0.4f;
		
	}
	
	public void getPlayerImage(){
		
		try {
			
			idle = ImageIO.read(getClass().getResourceAsStream("/player/player_idle.png"));
			up0 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_0.png"));
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/player_up_2.png"));
			down0 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_0.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/player_down_2.png"));
			left0 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_0.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
			right0 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_0.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
	
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
		
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}

}
