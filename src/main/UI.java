package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.OBJ_Skull;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font courier_new_25;
	
	public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		courier_new_25 = new Font("Courier New", Font.PLAIN, 25);
	    
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(courier_new_25);
		g2.setColor(Color.white);
		
		switch(gp.gameState) {
		
		case PLAY_STATE:
			break;
			
		case PAUSE_STATE:
			drawPauseScreen();
			break;
		}
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}
}
