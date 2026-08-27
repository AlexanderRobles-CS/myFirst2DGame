package environment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnvironmentManager {
	
	GamePanel gp;
	Lighting lighting;
	int playerLightRadius = 325; // Adjust this value to change the player's light radius
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		lighting = new Lighting(gp, playerLightRadius);
	}
	
	public void draw(Graphics2D g2) {
		lighting.draw(g2);
	}

	public void update() {
		lighting.update();
	}
}
