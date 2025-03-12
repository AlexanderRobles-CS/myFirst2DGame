package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Heart extends SuperObject{

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        name = "skullheart";
        collision = false;
        loadHeartImages();
    }

    private void loadHeartImages() {
        try {
            image = ImageIO.read(getClass().getResource("/objects/heart_full.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize, false);
            
            image2 = ImageIO.read(getClass().getResource("/objects/heart_half.png"));
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize, false);
            
            image3 = ImageIO.read(getClass().getResource("/objects/heart_blank.png"));
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
