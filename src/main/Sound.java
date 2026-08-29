package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
		Clip clip;
		URL soundURL[] = new URL[30];
		public static final int MAIN_THEME = 0;
		public static final int DUNGEON_BACKGROUND = 1;
		public static final int SKULL_PICKUP = 2;
		public static final int TILE_ACTIVATE = 3;
		public static final int DOOR_OPEN = 4;
		public static final int MONSTER_GROAN = 5;
		public static final int MONSTER_ATTACK_0 = 6;
		public static final int MONSTER_ATTACK_1 = 7;
		public static final int GAME_OVER = 8;
		public static final int EERIE_MUSIC = 9;
		public static final int CONFIRMATION_SOUND = 10;
		public static final int MENU_NAVIGATION = 11;
		
		public Sound() {
			
			soundURL[0]  = getClass().getResource("/sound/MysteriousForestMusicBy F O R N H I M M E L.wav");
			soundURL[1]  = getClass().getResource("/sound/dungeonBackground.wav");
			soundURL[2]  = getClass().getResource("/sound/skullPickup.wav");
			soundURL[3]  = getClass().getResource("/sound/tileActivate.wav");
			soundURL[4]  = getClass().getResource("/sound/doorOpen.wav");
			soundURL[5]  = getClass().getResource("/sound/monsterSound.wav");
			soundURL[6]  = getClass().getResource("/sound/daviddumaisaudio-small-monster-attack.wav");
			soundURL[7]  = getClass().getResource("/sound/pantoman-monster-attack.wav");
			soundURL[8]  = getClass().getResource("/sound/alphix-game-over.wav");
			soundURL[9]  = getClass().getResource("/sound/soundreality-wrong-place.wav");
			soundURL[10] = getClass().getResource("/sound/virtual_vibes-okay-confirmation.wav");
			soundURL[11] = getClass().getResource("/sound/freesound_community-menu-selection.wav");

		}
		
		public void setFile(int i) {
			try {
				
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
				clip = AudioSystem.getClip();
				clip.open(ais);
				
			}catch(Exception e) {
				
			}
		}
		
		public void play() {
			clip.start();
		}
		
		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		public void stop() {
			clip.stop();
		}

		public void setVolume(int volume) {
		    if (clip != null) {
		        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		        float dB = (float) (Math.log10(volume / 100.0) * 20.0);
		        gainControl.setValue(dB);
		    }
		}

		public int getVolume() {
		    if (clip != null) {
		        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		        float dB = gainControl.getValue();
		        return (int) Math.pow(10.0, dB / 20.0) * 100;
		    }
		    return 100;
		}
}
