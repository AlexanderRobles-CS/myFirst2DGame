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
            
       } catch (IOException e) {
           e.printStackTrace();
       }
    }
}
