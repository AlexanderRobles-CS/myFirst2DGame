package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Rune_Door extends SuperObject{
	
	private BufferedImage[] orbImages = new BufferedImage[5];
    private int currentFrame = 0;
    private int frameCounter = 0;
    
    public boolean runeDoorActivated = false;
    public boolean isAnimationComplete = false;

    public OBJ_Rune_Door(GamePanel gp) {
    	super(gp);
        name = "runeDoor";
        collision = true;
        loadOrbImages();
    }

    private void loadOrbImages() {
        try {
            for (int i = 0; i < 5; i++) {
                orbImages[i] = ImageIO.read(getClass().getResource("/objects/rune_door_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
    	if(runeDoorActivated && !isAnimationComplete) {
    		frameCounter++;
            if (frameCounter >= 50) {
                currentFrame = (currentFrame + 1) % 5;
                frameCounter = 0;
                System.out.println("Animating door...");
                
             // Check if animation reached the last frame
                if (currentFrame == 4) {
                    isAnimationComplete = true;
                    System.out.println("Door animation complete");
                    collision = false;
                }
            }
    	}
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = orbImages[currentFrame];
        super.draw(g2, gp);
    }
}
