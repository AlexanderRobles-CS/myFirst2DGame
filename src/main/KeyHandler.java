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
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			switch (code) {
			    case KeyEvent.VK_W:
			    	gp.ui.commandNum--;
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum < 0) {
			    		gp.ui.commandNum = 3;
			    	}
			        break;
			        
			    case KeyEvent.VK_S:
			    	gp.ui.commandNum++;
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum > 3) {
			    		gp.ui.commandNum = 0;
			    	}
			        break;
			        
			    case KeyEvent.VK_ENTER:
			    	if(gp.ui.commandNum == 0) {
						gp.playSE(Sound.CONFIRMATION_SOUND);
			    		gp.restartGame();
			    	}
			    	
			    	if(gp.ui.commandNum == 1) {
			    		// add later
			    	}
					
					// SETTINGS
			    	if(gp.ui.commandNum == 2) {
						gp.playSE(Sound.CONFIRMATION_SOUND);
						gp.ui.commandNum = 0;
			    		gp.gameState = gp.settingsState;
			    	}
					if(gp.ui.commandNum == 1) {
			    		// add later
			    	}
			    	
			    	if(gp.ui.commandNum == 3) {
			    		System.exit(0);
			    	}
			        break;
			}
		}

		// SETTINGS STATE
		else if(gp.gameState == gp.settingsState) {
			switch (code) {
			    case KeyEvent.VK_W:
			    	gp.ui.commandNum--;
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum < 2) {
			    		gp.ui.commandNum = 0;
			    	}
			        break;
			        
			    case KeyEvent.VK_S:
			    	gp.ui.commandNum++;
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum > 2) {
			    		gp.ui.commandNum = 0;
			    	}
			        break;

				case KeyEvent.VK_A:
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum == 1) {
			    		int currentVolume = SettingsManager.getVolume();
			    		int newVolume = Math.max(0, currentVolume - 1);
			    		SettingsManager.setVolume(newVolume);
			    		gp.music.setVolume(newVolume);
			    	}
			        break;
					
				case KeyEvent.VK_D:
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum == 1) {
			    		int currentVolume = SettingsManager.getVolume();
			    		int newVolume = Math.min(100, currentVolume + 1);
			    		SettingsManager.setVolume(newVolume);
			    		gp.music.setVolume(newVolume);
			    	}
			        break;
			        
			    case KeyEvent.VK_ENTER:
			    	if(gp.ui.commandNum == 0) {
						gp.playSE(Sound.CONFIRMATION_SOUND);
			    		gp.getDisplayManager().toggleFullscreen();
			    	}

					if(gp.ui.commandNum == 2) {
			    		gp.playSE(Sound.CONFIRMATION_SOUND);
						gp.ui.commandNum = 0;
						gp.gameState = gp.titleState;
			    	}
			        break;
			}
		}
		
		// DEATH STATE
		else if(gp.gameState == gp.deathState) {
			switch (code) {
			    case KeyEvent.VK_W:
			    	gp.ui.commandNum--;
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum < 0) {
			    		gp.ui.commandNum = 1;
			    	}
			        break;
			        
			    case KeyEvent.VK_S:
			    	gp.ui.commandNum++;
					gp.playSE(Sound.MENU_NAVIGATION);
			    	if(gp.ui.commandNum > 1) {
			    		gp.ui.commandNum = 0;
			    	}
			        break;
			        
			    case KeyEvent.VK_ENTER:
			    	if(gp.ui.commandNum == 0) {
			    		gp.gameState = gp.playState;
						gp.playSE(Sound.CONFIRMATION_SOUND);
			    		gp.restartGame();
			    	}
			    	
			    	if(gp.ui.commandNum == 1) {
			    		gp.gameState = gp.titleState;
						gp.ui.commandNum = 0;
			    		gp.stopMusic();
			    		gp.playMusic(Sound.EERIE_MUSIC);
			    	}
			        break;
			}
		}
		
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
			switch (code) {
			    case KeyEvent.VK_ESCAPE:
			    	gp.gameState = gp.playState;
			        break;

				case KeyEvent.VK_ENTER:
					if (gp.ui.commandNum == 0) {
						gp.gameState = gp.titleState;
						gp.playSE(Sound.CONFIRMATION_SOUND);
						gp.stopMusic();
			    		gp.playMusic(Sound.EERIE_MUSIC);
					}
			}
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
