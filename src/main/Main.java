package main;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
    	String playerIcon = "/player/player_idle_0.png";
    	String gameTitle = "Luminspire";
    	
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(gameTitle);

        ImageIcon icon = new ImageIcon(Main.class.getResource(playerIcon));
        window.setIconImage(icon.getImage());

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
