package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
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
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		setDefaultValues();
		getPlayerImage();
		direction = "idle";
	}
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
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
				y -= speed;
			}
			
			else if (keyH.downPressed == true) {
				direction = "down";
				y += speed;
			}
			
			else if (keyH.leftPressed == true) {
				direction = "left";
				x -= speed;
			}
			
			else if (keyH.rightPressed == true) {
				direction = "right";
				x += speed;
			}
			
			spriteCounter++;
			afkCounter++;
			
			if (spriteCounter > 12) {
				
				if(spriteNum == 0) {
					spriteNum = 1;
				}
				
				else if(spriteNum == 1) {
					spriteNum = 2;
				}
				
				else if(spriteNum == 2) {
					spriteNum = 0;
				}
				
				spriteCounter = 0;
			}
				
		}
		
		if (keyH.afk) {
			afkCounter++;
		}
		
		else {
			afkCounter = 0;
		}
		
		if (afkCounter > 180) {
			direction = "idle";
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch(direction) {
		
		case "up":
			if (spriteNum == 0) {
				image = up0;
			}
			
			if (spriteNum == 1) {
				image = up1;
			}
			
			if (spriteNum == 2) {
				image = up2;	
			}
			break;
			
		case "down":
			if (spriteNum == 0) {
				image = down0;
			}
			
			if (spriteNum == 1) {
				image = down1;
			}
			
			if (spriteNum == 2) {
				image = down2;	
			}
			break;
			
		case "left":
			if (spriteNum == 0) {
				image = left0;
			}
			
			if (spriteNum == 1) {
				image = left1;
			}
			
			if (spriteNum == 2) {
				image = left2;	
			}
			break;
			
		case "right":
			if (spriteNum == 0) {
				image = right0;
			}
			
			if (spriteNum == 1) {
				image = right1;
			}
			
			if (spriteNum == 2) {
				image = right2;	
			}
			break;
			
		case "idle":
			image = idle;
			break;
		
		}
		
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		
		g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
		
	}

}
