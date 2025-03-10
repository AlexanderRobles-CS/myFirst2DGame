package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_Necromancer extends Entity{
	
	public NPC_Necromancer(GamePanel gp) {
		super(gp);
		
		direction = "down";
		speed = 1;
		
		getNPCImage();
	}
	
	public void getNPCImage(){
		
		up0 = setup("/npc/necromancer_up_0", false);
		up1 = setup("/npc/necromancer_up_1", false);
		up2 = setup("/npc/necromancer_up_2", false);
		up3 = setup("/npc/necromancer_up_3", false);
		down0 = setup("/npc/necromancer_down_0", false);
		down1 = setup("/npc/necromancer_down_1", false);
		down2 = setup("/npc/necromancer_down_2", false);
		down3 = setup("/npc/necromancer_down_3", false);
		left0 = setup("/npc/necromancer_left_0", false);
		left1 = setup("/npc/necromancer_left_1", false);
		left2 = setup("/npc/necromancer_left_2", false);
		left3 = setup("/npc/necromancer_left_3", false);
		right0 = setup("/npc/necromancer_right_0", false);
		right1 = setup("/npc/necromancer_right_1", false);
		right2 = setup("/npc/necromancer_right_2", false);
		right3 = setup("/npc/necromancer_right_3", false);
	
	}
	
	public void setAction() {
		
		actionLookCounter++;
		
		if(actionLookCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;
			
			if(i <= 25)
				direction = "up";
			
			if(i > 25 && i <= 50)
				direction = "down";
			
			if(i > 50 && i <= 75)
				direction = "left";
			
			if(i > 75 && i <= 100)
				direction = "right";
			
			actionLookCounter = 0;
		}
	}
}
