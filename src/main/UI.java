package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.OBJ_Heart;
import object.SuperObject;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font purisaB;
	BufferedImage heart_full, heart_half, heart_blank;
	
	public static final int TITLE_STATE = 0;
	public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 2;
    public static final int DIALOUGE_STATE = 3;
    public static final int DEATH_STATE = 4;
	
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	public String currentDialouge = "";

	private String fontName = "/font/Purisa Bold.ttf";
	
	public int commandNum = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream(fontName);
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE HUD OBJECT
		SuperObject heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
	}
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		g2.setFont(purisaB);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		switch(gp.gameState) {
		
			case TITLE_STATE:
				drawTitleScreen();
				break;
		
			case PLAY_STATE:
				drawPlayerLife();
				break;
				
			case PAUSE_STATE:
				drawPlayerLife();
				drawPauseScreen();
				break;
				
			case DIALOUGE_STATE:
				drawPlayerLife();
				drawDialougeScreen();
				break;
				
			case DEATH_STATE:
				drawDeathScreen();
				break;
		}
	}
	
	public void drawPlayerLife() {
		
		int x = gp.tileSize / 2;
		int y = gp.tileSize / 2;
		int i = 0;
		
		// DRAW BLANK HEART
		while(i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		// RESET
		x = gp.tileSize / 2;
		y = gp.tileSize / 2;
		i = 0;
		
		// DRAW CURRENT LIFE
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		
		
	}
	
	public void drawTitleScreen() {
		
		// BACKGROUND COLOR
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,96F));
		String text = "Luminspire";
		int x = getXForCenteredText(text);
		int y = gp.tileSize * 3;
		
		// SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x + 5, y + 5);
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// MAIN CHARACTER IMAGE
		x = gp.screenWidth / 2 - ( 2 *(gp.tileSize / 2)); 
		y += gp.tileSize * 2;
		g2.drawImage(gp.player.down0, x, y , gp.tileSize * 2, gp.tileSize * 2, null);
		
		// MENU
		text = "NEW GAME";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		x = getXForCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		
		// can change to draw image if you want!! TODO:
		if(commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		// MENU
		text = "LOAD GAME";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		
		if(commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		// MENU
		text = "QUIT GAME";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);		
		if(commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	
	}
	
	public void drawPauseScreen() {
		String text = "PAUSED";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2 - gp.tileSize;
		
		g2.drawString(text, x, y);
	}
	
	public void drawDialougeScreen() {
		
		// WINDOW
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));
		
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialouge.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
	
	public void drawDeathScreen() {
		// BACKGROUND COLOR
		g2.setColor(new Color(0, 0, 0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		String text = "Your light goes out.";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2 - gp.tileSize;
		
		// SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x + 2, y + 2);
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// Restart Game
		text = "Resurrect";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		x = getXForCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		
		if(commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		// Main Menu
		text = "Main Menu";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,32F));
		x = getXForCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		
		if(commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
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
