package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Rune_Door extends SuperObject{
	
	private BufferedImage[] orbImages = new BufferedImage[3];
    private int currentFrame = 0;
    private int frameCounter = 0;

    public OBJ_Rune_Door() {
        name = "runeDoor";
        this.collision = true;
        loadOrbImages();
    }

    private void loadOrbImages() {
        try {
            for (int i = 0; i < 3; i++) {
                orbImages[i] = ImageIO.read(getClass().getResource("/objects/rune_door_" + i + ".png"));
                System.out.println("DOOR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        frameCounter++;
        if (frameCounter >= 10) {
            currentFrame = (currentFrame + 1) % 3;
            frameCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = orbImages[currentFrame];
        super.draw(g2, gp);
    }
}
