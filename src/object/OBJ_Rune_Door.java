package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Rune_Door extends SuperObject{
	
	private BufferedImage[] doorImages = new BufferedImage[5];
    private int currentFrame = 0;
    private int frameCounter = 0;
    
    public boolean runeDoorActivated = false;
    public final int requiredRunes = 3;
    public int activatedRunes = 0;
    
    public boolean isAnimationComplete = false;
   
    public OBJ_Rune_Door(GamePanel gp) {
    	super(gp);
        name = "runeDoor";
        collision = true;
        loadDoorImages();
    }

    private void loadDoorImages() {
        try {
            for (int i = 0; i < 5; i++) {
            	doorImages[i] = ImageIO.read(getClass().getResource("/objects/rune_door_" + i + ".png"));
            	doorImages[i] = uTool.scaleImage(doorImages[i], gp.tileSize, gp.tileSize, false);
            }
            image = doorImages[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void activateRune()
    {
    	if (!runeDoorActivated) {
            activatedRunes++;

            if (activatedRunes >= requiredRunes) {
                runeDoorActivated = true;
            }
        }
    }
    public void update() {
    	if(runeDoorActivated && !isAnimationComplete) {
    		frameCounter++;
            if (frameCounter >= 50) {
                currentFrame = (currentFrame + 1) % 5;
                frameCounter = 0;
                
             // Check if animation reached the last frame
                if (currentFrame == 4) {
                    isAnimationComplete = true;
                    collision = false;
                }
            }
    	}
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = doorImages[currentFrame];
        super.draw(g2, gp);
    }
}
