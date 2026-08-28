package main;

import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import object.OBJ_Rune_Door;
import object.OBJ_Rune_Step;
import object.OBJ_Skull;
import object.OBJ_Torch;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS 
	final int originalTileSize = 16;    // 16x16 tile
	final int scale = 3;
	private DisplayManager displayManager;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 22;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow;
	
	// FPS
	int FPS = 60;
	
	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	EnvironmentManager eManager = new EnvironmentManager(this);
	Thread gameThread;
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[20];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	
	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialougeState = 3;
	public final int deathState = 4;
	public final int settingsState = 5;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
	public void setupGame() {
		playMusic(Sound.MAIN_THEME);
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		eManager.setup();
		
		gameState = titleState;
	}
	
	public void restartGame() {
	    for(int i = 0; i < obj.length; i++) { obj[i] = null; }
	    for(int i = 0; i < npc.length; i++) { npc[i] = null; }
	    for(int i = 0; i < monster.length; i++) { monster[i] = null; }

	    player.setDefaultValues();
	    
	    aSetter.setObject();
	    aSetter.setNPC();
	    aSetter.setMonster();
	    
	    stopMusic();
	    playMusic(Sound.DUNGEON_BACKGROUND);
	    
	    gameState = playState;
	}


	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void update() {
		
		// PLAY STATE
		if(gameState == playState) {
			// PLAYER
			player.update();
			
			// NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			
			// MONSTER
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					monster[i].update();
				}
			}
			
			for (int i = 0; i < obj.length; i++) {
		        if (obj[i] != null) {
		            if (obj[i] instanceof OBJ_Skull) {
		                ((OBJ_Skull) obj[i]).update();
		            }
		            
		            if (obj[i] instanceof OBJ_Torch) {
		                ((OBJ_Torch) obj[i]).update();  
		            }
		            
		            if (obj[i] instanceof OBJ_Rune_Step) {
		            	if (((OBJ_Rune_Step) obj[i]).runeStepActivated == true) {
		            		((OBJ_Rune_Step) obj[i]).update();
		            	}
		            }
		            
		            if (obj[i] instanceof OBJ_Rune_Door) {
		            	if (((OBJ_Rune_Door) obj[i]).runeDoorActivated == true) {
		            		((OBJ_Rune_Door) obj[i]).update();
		            	}
		            }
		        }
		    }

			eManager.update();
		}
		
		// PAUSE STATE
	    if(gameState == pauseState) {
	    	// nothing
	    }
	}

	public void setDisplayManager(DisplayManager displayManager) {
    	this.displayManager = displayManager;
	}

	public DisplayManager getDisplayManager() {
		return displayManager;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		double scaler = (double) getHeight() / screenHeight;

		// Calculate scaled dimensions
		int scaledWidth = (int) (screenWidth * scaler);
		int scaledHeight = (int) (screenHeight * scaler);

		// Center horizontally and vertically
		int x = (getWidth() - scaledWidth) / 2;
		int y = (getHeight() - scaledHeight) / 2;

		g2.translate(x, y);
		g2.scale(scaler, scaler);

		g2.clipRect(0, 0, screenWidth, screenHeight);

		// DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime) {
			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		if(gameState == titleState) {
			ui.draw(g2);
		}

		else {
			// TILE
			tileM.draw(g2);

			// OBJECT
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}

			// NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].draw(g2);
				}
			}

			// MONSTER
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					monster[i].draw(g2);
				}
			}

			// PLAYER
			player.draw(g2);

			// ENVIRONMENT
			eManager.draw(g2);

			// UI
			ui.draw(g2);
		}

		// DEBUG
		if(keyH.checkDrawTime) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;

			g2.setColor(Color.white);
			g2.drawString("Draw Time: " + passed, 10, 400);
		}

		g2.dispose();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta > 1) {
				update();
				repaint();
				delta--;
				drawCount++;
				
			}
			
			if (timer > 1000000000) {
                System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
				
			}
			
		}
		
	}

}
