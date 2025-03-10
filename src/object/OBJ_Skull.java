package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Skull extends SuperObject {
    
    public BufferedImage[] orbImages = new BufferedImage[4];
    private int currentFrame = 0;
    private int frameCounter = 0;
    private boolean increasing = true;

    public OBJ_Skull(GamePanel gp) {
        super(gp);
        name = "skull";
        collision = false;
        loadOrbImages();
    }

    private void loadOrbImages() {
        try {
            for (int i = 0; i < 4; i++) {
            	orbImages[i] = ImageIO.read(getClass().getResource("/objects/skull_" + i + ".png"));
                orbImages[i] = uTool.scaleImage(orbImages[i], gp.tileSize, gp.tileSize, false);
            }
            image = orbImages[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        frameCounter++;
        if (frameCounter >= 25) {
            if (currentFrame == 3) increasing = false;
            else if (currentFrame == 0) increasing = true;

            currentFrame += increasing ? 1 : -1;
            frameCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = orbImages[currentFrame];
        super.draw(g2, gp);
    }
}
