package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Torch extends SuperObject {
    
    public BufferedImage[] torchImages = new BufferedImage[4];
    private int currentFrame = 0;
    private int frameCounter = 0;
    private boolean increasing = true;

    public OBJ_Torch(GamePanel gp) {
        super(gp);
        name = "torch";
        collision = false;
        loadTorchImages();
    }

    private void loadTorchImages() {
        try {
            for (int i = 1; i < 5; i++) {
            	torchImages[i - 1] = ImageIO.read(getClass().getResource("/objects/torch_" + i + ".png"));
            	torchImages[i - 1] = uTool.scaleImage(torchImages[i - 1], gp.tileSize, gp.tileSize, false);
            }
            image = torchImages[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        frameCounter++;
        if (frameCounter >= 25) {
            if (currentFrame == 3) increasing = false; // Corrected boundary
            else if (currentFrame == 0) increasing = true;

            currentFrame += increasing ? 1 : -1;
            frameCounter = 0;
        }
    }


    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = torchImages[currentFrame];
        super.draw(g2, gp);
    }
}
