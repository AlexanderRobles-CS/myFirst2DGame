package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font courier_new;
	
	public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOUGE_STATE = 3;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialouge = "";
	
	public UI(GamePanel gp) {
		this.gp = gp;
		courier_new = new Font("Courier New", Font.PLAIN, 25);
	    
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(courier_new);
		g2.setColor(Color.white);
		
		switch(gp.gameState) {
		
		case PLAY_STATE:
			break;
			
		case PAUSE_STATE:
			drawPauseScreen();
			break;
			
		case DIALOUGE_STATE:
			drawDialougeScreen();
			break;
		}
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialougeScreen() {
		
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);
		
		courier_new = new Font("Courier New", Font.PLAIN, 20);
		
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialouge.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 150);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		
	}
	
	public int getXForCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}
}
