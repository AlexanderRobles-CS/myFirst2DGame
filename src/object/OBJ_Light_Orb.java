package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Light_Orb extends SuperObject {
    
    private BufferedImage[] orbImages = new BufferedImage[3];
    private int currentFrame = 0;
    private int frameCounter = 0;
    private boolean increasing = true;

    public OBJ_Light_Orb(GamePanel gp) {
    	super(gp);
        name = "lightOrb";
        collision = false;
        loadOrbImages();
    }

    private void loadOrbImages() {
        try {
            for (int i = 0; i < 3; i++) {
                orbImages[i] = ImageIO.read(getClass().getResource("/objects/light_orb_" + i + ".png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        frameCounter++;
        if (frameCounter >= 25) {
            if (currentFrame == 2) {
                increasing = false; // Change direction when reaching the max value
            } else if (currentFrame == 0) {
                increasing = true; // Change direction when reaching the min value
            }

            if (increasing) {
                currentFrame++;  // Increase frame
            } else {
                currentFrame--;  // Decrease frame
            }

            frameCounter = 0;
        }
    }


    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = orbImages[currentFrame];
        super.draw(g2, gp);
    }
}
