package main;

import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
    	String playerIcon = "/player/player_idle_0.png";
    	String gameTitle = "Luminspire";
    	
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle(gameTitle);

        ImageIcon icon = new ImageIcon(Main.class.getResource(playerIcon));
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        DisplayManager displayManager = new DisplayManager(window, gamePanel, icon.getImage());
        gamePanel.setDisplayManager(displayManager);
        displayManager.hideCursor();

        gamePanel.setupGame();
        displayManager.applyDisplayMode(SettingsManager.isFullscreen());

        gamePanel.startGameThread();
    }
}