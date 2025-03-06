package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, afk;

	@Override
	public void keyTyped(KeyEvent e) {
		return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		afk = false;
		int code = e.getKeyCode();
		
		switch (code) {
	    case KeyEvent.VK_W:
	    	upPressed = true;
	        break;
	        
	    case KeyEvent.VK_S:
	        downPressed = true;
	        break;
	        
	    case KeyEvent.VK_A:
	    	leftPressed = true;
	        break;
	        
	    case KeyEvent.VK_D:
	    	rightPressed = true;
	        break;

	    default:
	    	System.out.println("Invalid Key");
	    	
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		afk = true;
		int code = e.getKeyCode();
		
		switch (code) {
		
	    case KeyEvent.VK_W:
	    	upPressed = false;
	        break;
	        
	    case KeyEvent.VK_S:
	        downPressed = false;
	        break;
	        
	    case KeyEvent.VK_A:
	    	leftPressed = false;
	        break;
	        
	    case KeyEvent.VK_D:
	    	rightPressed = false;
	        break;

	    default:
	    	System.out.println("Invalid Key");
	    	
		}
		
	}
	
}
