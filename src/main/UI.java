package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Light_Orb;

public class UI {
	
	GamePanel gp;
	Font courier_new_25;
	BufferedImage lightOrbImage;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		courier_new_25 = new Font("Courier New", Font.PLAIN, 25);
		
		OBJ_Light_Orb lightOrb = new OBJ_Light_Orb(gp);
	    lightOrbImage = lightOrb.image;
	    
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		
		g2.setFont(courier_new_25);
		g2.setColor(Color.white);
		
		int orbSize = gp.tileSize * 3 / 4; // Scale down to 75% of tile size
		int orbX = gp.tileSize / 2 + (gp.tileSize - orbSize) / 2; // Center it
		int orbY = gp.tileSize / 2 + (gp.tileSize - orbSize) / 2;

		g2.drawImage(lightOrbImage, orbX, orbY, orbSize, orbSize, null);
		g2.drawString("x " + gp.player.orbCount, 75, 50);
		
		// MESSAGE
		if(messageOn) {
			g2.setFont(g2.getFont().deriveFont(20f));
			g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
			
			messageCounter++;
			
			if(messageCounter > 120) {
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
}
