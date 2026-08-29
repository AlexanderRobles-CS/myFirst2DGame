package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

		Clip clip;
		URL soundURL[] = new URL[30];
		private boolean isSfx;
		private int volume = 100; // cached, effective (post-master) value

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
			this(false);
		}

		public Sound(boolean isSfx) {
			this.isSfx = isSfx;

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

			refreshVolume(); // pull current settings on construction
		}

		public void setFile(int i) {
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
				clip = AudioSystem.getClip();
				clip.open(ais);

				FloatControl gc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				System.out.println("Gain range: " + gc.getMinimum() + " to " + gc.getMaximum());

				applyVolumeToClip();

			} catch(Exception e) {
				e.printStackTrace();
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

		/**
		 * Recomputes this channel's effective volume from SettingsManager.
		 * Music = master volume directly.
		 * SFX   = sfx slider scaled by master volume (so master=0 mutes SFX too).
		 */
		public void refreshVolume() {
		    int master = SettingsManager.getVolume();

		    if (isSfx) {
		        int sfxSetting = SettingsManager.getSfxVolume();
		        this.volume = Math.round(sfxSetting * (master / 100.0f));
		    } else {
		        this.volume = master;
		    }

		    applyVolumeToClip();
		}

		private void applyVolumeToClip() {
			if (clip != null) {
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				float min = gainControl.getMinimum(); // -80.0
				float max = 0.0f; // cap at unity gain, ignore the +6dB boost headroom

				float dB;
				if (volume <= 1) {
					dB = min;
				} else {
					// steeper curve: 40*log10 instead of 20*log10 drops faster at low %
					dB = (float) (Math.log10(volume / 100.0) * 40.0);
					dB = Math.max(min, Math.min(max, dB));
				}

				gainControl.setValue(dB);
			}
		}
		public int getVolume() {
		    return volume; // effective, post-master value
		}
}