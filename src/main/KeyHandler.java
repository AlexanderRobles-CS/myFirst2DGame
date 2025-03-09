package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
	// CONTROLS
	public boolean upPressed, downPressed, leftPressed, rightPressed, afk;
	
	// DEBUG
	boolean checkDrawTime = false;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		switch (code) {
		    case KeyEvent.VK_W:
		    	upPressed = true;
		    	afk = false;
		        break;
		        
		    case KeyEvent.VK_S:
		        downPressed = true;
		        afk = false;
		        break;
		        
		    case KeyEvent.VK_A:
		    	leftPressed = true;
		    	afk = false;
		        break;
		        
		    case KeyEvent.VK_D:
		    	rightPressed = true;
		    	afk = false;
		        break;
		        
		    case KeyEvent.VK_ESCAPE:
		    	if(gp.gameState == gp.playState) {
		    		gp.gameState = gp.pauseState;
		    	}
		    	
		    	else if(gp.gameState == gp.pauseState) {
		    		gp.gameState = gp.playState;
		    	}
		    	
		        break;
		    
		    // DEBUG
		    case KeyEvent.VK_T:
		    	
		    	if(!checkDrawTime) {
		    		checkDrawTime = true;
		    	}
		    	
		    	else if(checkDrawTime) {
		    		checkDrawTime = false;
		    	}
		        break;
	
		    default:
		    	System.out.println("Invalid Key");
		    	
			}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		switch (code) {
		
	    case KeyEvent.VK_W:
	    	upPressed = false;
	    	afk = true;
	        break;
	        
	    case KeyEvent.VK_S:
	        downPressed = false;
	        afk = true;
	        break;
	        
	    case KeyEvent.VK_A:
	    	leftPressed = false;
	    	afk = true;
	        break;
	        
	    case KeyEvent.VK_D:
	    	rightPressed = false;
	    	afk = true;
	        break;

	    default:
	    	System.out.println("Invalid Key");
	    	
		}
		
	}
	
}
