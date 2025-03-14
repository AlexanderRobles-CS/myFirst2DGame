package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_Skeleton extends Entity{

	public MON_Skeleton(GamePanel gp) {
		super(gp);
		name = "skeleton";
		
		type = 2;
		direction = "down";
		speed = 3;
		maxLife = 4;
		life = maxLife;
		
		solidArea.x = 10;
		solidArea.y = 3;
		solidArea.width = 38;
		solidArea.height = 52;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		getSkeletonImages();
	}

	public void getSkeletonImages(){
		
		up0 = setup("/monster/skeleton_left_0", false);
		up1 = setup("/monster/skeleton_left_1", false);
		up2 = setup("/monster/skeleton_left_2", false);
		up3 = setup("/monster/skeleton_left_3", false);
		down0 = setup("/monster/skeleton_right_0", false);
		down1 = setup("/monster/skeleton_right_1", false);
		down2 = setup("/monster/skeleton_right_2", false);
		down3 = setup("/monster/skeleton_right_3", false);
		left0 = setup("/monster/skeleton_left_0", false);
		left1 = setup("/monster/skeleton_left_1", false);
		left2 = setup("/monster/skeleton_left_2", false);
		left3 = setup("/monster/skeleton_left_3", false);
		right0 = setup("/monster/skeleton_right_0", false);
		right1 = setup("/monster/skeleton_right_1", false);
		right2 = setup("/monster/skeleton_right_2", false);
		right3 = setup("/monster/skeleton_right_3", false);
	
	}
	
	public void setAction() {
		
		actionLookCounter++;
		
		if(actionLookCounter == 60) {
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
