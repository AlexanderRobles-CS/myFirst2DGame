package main;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	
	float opacity = 0.4f;
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height, boolean player) {
	    int type = (original.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : original.getType();
	    BufferedImage scaledImage = new BufferedImage(width, height, type);
	    Graphics2D g2 = scaledImage.createGraphics();
	    
	    if (player)
	        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	    
	    g2.drawImage(original, 0, 0, width, height, null);
	    g2.dispose();
	    
	    return scaledImage;
	}

}
