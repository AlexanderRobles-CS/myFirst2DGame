package entity;


import object.OBJ_Rune_Door;
import object.OBJ_Rune_Step;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import main.Sound;
import monster.MON_Skeleton;
import java.util.Random;

public class Player extends Entity{

	KeyHandler keyH;
	int afkCounter;
	public int skullCount = 0;
	boolean doorActivated = false;
	public String lastMovementDirection = "down";

	private MON_Skeleton lastSkeleton = null;
	private Random random = new Random();

	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = gp.tileSize / 6;
		solidArea.y = gp.tileSize / 3;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = gp.tileSize - (gp.tileSize / 3);
		solidArea.height = gp.tileSize - (gp.tileSize / 3);
		
		setDefaultPositions();
		setDefaultValues();
		getPlayerImage();
		direction = "idle";
		
	}
	
    public void setDefaultPositions() {
        worldX = gp.tileSize * 23; 
        worldY = gp.tileSize * 21; 
        direction = "down";
    }
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 25;
		worldY = gp.tileSize * 47;
		speed = 6;
		skullCount = 0;
		afkCounter = 0;
		
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
		
	}
	
	public void getPlayerImage(){
		
		idleUp0 = setup("/player/player_up_idle_0", true);
		idleUp1 = setup("/player/player_up_idle_1", true);
		idleDown0 = setup("/player/player_idle_0", true);
		idleDown1 = setup("/player/player_idle_1", true);
		idleLeft0 = setup("/player/player_left_idle_0", true);
		idleLeft1 = setup("/player/player_left_idle_1", true);
		idleRight0 = setup("/player/player_right_idle_0", true);
		idleRight1 = setup("/player/player_right_idle_1", true);
		up0 = setup("/player/player_up_0", true);
		up1 = setup("/player/player_up_1", true);
		up2 = setup("/player/player_up_2", true);
		down0 = setup("/player/player_down_0", true);
		down1 = setup("/player/player_down_1", true);
		down2 = setup("/player/player_down_2", true);
		left0 = setup("/player/player_left_0", true);
		left1 = setup("/player/player_left_1", true);
		left2 = setup("/player/player_left_2", true);
		right0 = setup("/player/player_right_0", true);
		right1 = setup("/player/player_right_1", true);
		right2 = setup("/player/player_right_2", true);
	
	}
	
	public void update() {
		
		if (keyH.upPressed == true || keyH.downPressed == true || 
				keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
			
			if (keyH.upPressed) {
		        direction = "up";
		        lastMovementDirection = "up";
		    }

		    if (keyH.downPressed) {
		        direction = "down";
		        lastMovementDirection = "down";
		    }

		    if (keyH.leftPressed) {
		        direction = "left";
		        lastMovementDirection = "left";
		    }

		    if (keyH.rightPressed) {
		        direction = "right";
		        lastMovementDirection = "right";
		    }
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);
			
			// CHECK EVENT
			gp.eHandler.checkEvent();
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false && 	gp.keyH.enterPressed == false) {
				switch(direction) {
					case "up": worldY -= speed; break;
					case "down": worldY += speed; break;
					case "left": worldX -= speed; break;
					case "right": worldX += speed; break;
				}
			}
			
			gp.keyH.enterPressed = false;
			
			afkCounter = 0;
			spriteCounter++;
				
		}
		
		else {
		    afkCounter++;
		    
		    if (afkCounter > 120) {
		        direction = "idle";
		        spriteCounter++;
		    }
		}

		if (spriteCounter > 12) {
			if (direction.equals("idle")) {
			    spriteNum = (spriteNum + 1) % 2; // Cycle 0, 1 for idle
			} else {
			    spriteNum = (spriteNum + 1) % 3; // Cycle 0, 1, 2 for movement
			}

            spriteCounter = 0;
        }
		
		if(invincible == true) {
			invincibleCounter++;
			
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		// Check for nearby skeleton
		MON_Skeleton nearestSkeleton = null;

		for (int i = 0; i < gp.monster.length; i++) {
		    if (gp.monster[i] instanceof MON_Skeleton) {
		        MON_Skeleton skeleton = (MON_Skeleton) gp.monster[i];

		        if (Math.abs(skeleton.worldX - worldX) < gp.tileSize * 5 &&
		            Math.abs(skeleton.worldY - worldY) < gp.tileSize * 5) {
		                
		            nearestSkeleton = skeleton;
		            break;
		        }
		    }
		}

		// Random chance to play SE when returning to a skeleton
		if (nearestSkeleton != null && nearestSkeleton != lastSkeleton) {
		    if (random.nextInt(100) < 25) { // 40% chance to play the sound
		        gp.playSE(Sound.MONSTER_GROAN);
		    }
		    lastSkeleton = nearestSkeleton;
		} else if (nearestSkeleton == null) {
		    lastSkeleton = null; // Reset flag when no skeleton is nearby
		}


	}
	
	public void pickUpObject(int i) {
		
		if(i != 999) {
			
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "skull":
				gp.playSE(Sound.SKULL_PICKUP);
				skullCount++;
				gp.obj[i] = null;
				break;
				
			case "runeDoor":
				break;
				
			case "runeStep":
			    if(skullCount > 0 && !((OBJ_Rune_Step) gp.obj[i]).runeStepActivated) {
			        skullCount--;
			        ((OBJ_Rune_Step) gp.obj[i]).runeStepActivated = true;
			        gp.playSE(Sound.TILE_ACTIVATE);

			        // Find the nearest rune door and activate a rune on it
			        for (int j = 0; j < gp.obj.length; j++) {
			            if (gp.obj[j] instanceof OBJ_Rune_Door) {
			                OBJ_Rune_Door door = (OBJ_Rune_Door) gp.obj[j];

			                // Check if the door is nearby
			                if (Math.abs(door.worldX - worldX) < gp.tileSize * 10 &&
			                    Math.abs(door.worldY - worldY) < gp.tileSize * 10) {
			                    door.activateRune();
			                    
			                    // check if door nearby is activated
				                if(door.runeDoorActivated) {
				                	gp.playSE(Sound.DOOR_OPEN);
				                }
			                    break;
			                }
			            }
			        }
			    }
			    break;

				
			case "pitfall":
				System.out.println("Pitfall!");
				
				// TODO pitfall action
				break;
				
			case "stairs":
				System.out.println("Stairs!");
				
				// load new map
				// reset world
				break;	
			}
		}
	}
	
	public void interactNPC(int i) {
		if(i != 999) {
			if(gp.keyH.enterPressed) {
				gp.gameState = gp.dialougeState;
				gp.npc[i].speak();
			}
		}
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			if (life <= 0) {
				gp.gameState = gp.deathState;
			}
						
			if(invincible == false) {
				life -= 1;
				int choice = random.nextInt(2); 
        
				if (choice == 0) {
					gp.playSE(Sound.MONSTER_ATTACK_0);
				} else {
					gp.playSE(Sound.MONSTER_ATTACK_1);
				}
				
				invincible = true;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		// Update the image based on the direction
	    switch (direction) {
	        case "up":
	            image = (spriteNum == 0) ? up0 : (spriteNum == 1) ? up1 : up2;
	            break;

	        case "down":
	            image = (spriteNum == 0) ? down0 : (spriteNum == 1) ? down1 : down2;
	            break;

	        case "left":
	            image = (spriteNum == 0) ? left0 : (spriteNum == 1) ? left1 : left2;
	            break;

	        case "right":
	            image = (spriteNum == 0) ? right0 : (spriteNum == 1) ? right1 : right2;
	            break;

	        case "idle":
	            switch (lastMovementDirection) {
	                case "up":
	                    image = (spriteNum == 0) ? idleUp0 : idleUp1;
	                    break;

	                case "down":
	                    image = (spriteNum == 0) ? idleDown0 : idleDown1;
	                    break;

	                case "left":
	                    image = (spriteNum == 0) ? idleLeft0 : idleLeft1;
	                    break;

	                case "right":
	                    image = (spriteNum == 0) ? idleRight0 : idleRight1;
	                    break;
	            }
	            break;

	        default:
	            break;
	    }
		
		g2.drawImage(image, screenX, screenY, null);
		
	}

}
