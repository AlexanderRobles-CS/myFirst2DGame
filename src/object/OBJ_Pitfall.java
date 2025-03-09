package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;


public class OBJ_Pitfall extends SuperObject{

    public OBJ_Pitfall(GamePanel gp) {
    	super(gp);
    	name = "pitfall";
    	collision = false;
        
        try {
            image = ImageIO.read(getClass().getResource("/objects/pitfall.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize, false);
            
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
