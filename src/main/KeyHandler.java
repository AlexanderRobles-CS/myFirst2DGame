package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	
	// CONTROLS
	public boolean upPressed, downPressed, leftPressed, rightPressed, afk, enterPressed;
	
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
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
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
			    	gp.gameState = gp.pauseState;
			        break;
			        
			    case KeyEvent.VK_ENTER:
			    	enterPressed = true;
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
			}
		}
		
		// PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			if(code == KeyEvent.VK_ESCAPE)
				gp.gameState = gp.playState;
			}
		
		// DIALOUGE STATE
		else if(gp.gameState == gp.dialougeState) {
			if(code == KeyEvent.VK_ENTER)
				gp.gameState = gp.playState;
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
	    	
		}
		
	}
	
}
