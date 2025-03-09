package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Rune_Step extends SuperObject{
	
	private BufferedImage[] runeStepImages = new BufferedImage[3];
    private int currentFrame = 0;
    private int frameCounter = 0;
    
    public boolean runeStepActivated = false;
    private boolean isAnimationComplete = false;

    public OBJ_Rune_Step(GamePanel gp) {
    	super(gp);
        name = "runeStep";
        collision = false;
        loadOrbImages();
    }

    private void loadOrbImages() {
        try {
            for (int i = 0; i < 3; i++) {
            	runeStepImages[i] = ImageIO.read(getClass().getResource("/objects/rune_tile_" + i + ".png"));
            	runeStepImages[i] = uTool.scaleImage(runeStepImages[i], gp.tileSize, gp.tileSize, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
    	if(runeStepActivated && !isAnimationComplete) {
    		frameCounter++;
            if (frameCounter >= 50) {
                currentFrame = (currentFrame + 1) % 3;
                frameCounter = 0;
                
                if (currentFrame == 2) {
                    isAnimationComplete = true;
                }
            }
    	}
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = runeStepImages[currentFrame];
        super.draw(g2, gp);
    }
}
