package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Stairs extends SuperObject{

    public OBJ_Stairs(GamePanel gp) {
    	super(gp);
    	name = "stairs";
    	collision = false;
        
        try {
            image = ImageIO.read(getClass().getResource("/objects/stairs.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize, false);
            
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
