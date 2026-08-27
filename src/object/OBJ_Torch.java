package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Torch extends SuperObject {

    public BufferedImage[] torchImages = new BufferedImage[4];
    private int currentFrame = 0;
    private int frameCounter = 0;
    private boolean increasing = true;

    // ---- flicker state ----
    private final Random rand = new Random();
    private double flickerOffset = 0;   // current smoothed flicker value (in pixels)
    private double flickerTarget = 0;   // where flickerOffset is heading
    private int flickerCounter = 0;

    private static final double FLICKER_RANGE = 30.0;   // max +/- pixels of radius jitter
    private static final int FLICKER_RETARGET_FRAMES = 6; // how often to pick a new target
    private static final double FLICKER_SMOOTHING = 0.50; // higher = snappier, lower = smoother

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
        // sprite animation
        frameCounter++;
        if (frameCounter >= 25) {
            if (currentFrame == 3) increasing = false;
            else if (currentFrame == 0) increasing = true;

            currentFrame += increasing ? 1 : -1;
            frameCounter = 0;
        }

        // light flicker
        flickerCounter++;
        if (flickerCounter >= FLICKER_RETARGET_FRAMES) {
            flickerTarget = (rand.nextDouble() * 2 - 1) * FLICKER_RANGE; // random value in [-RANGE, +RANGE]
            flickerCounter = 0;
        }
        // smoothly ease toward the target instead of snapping, so it looks organic
        flickerOffset += (flickerTarget - flickerOffset) * FLICKER_SMOOTHING;
    }

    // returns the current flicker offset in pixels, to be added to the light radius
    public double getLightFlicker() {
        return flickerOffset;
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        image = torchImages[currentFrame];
        super.draw(g2, gp);
    }
}