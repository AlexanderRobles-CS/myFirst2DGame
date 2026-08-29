package main;

import java.util.prefs.Preferences;

public class SettingsManager {

    private static final Preferences prefs = Preferences.userNodeForPackage(SettingsManager.class);
    private static final String KEY_FULLSCREEN = "fullscreen";
    private static final String KEY_VOLUME = "volume";
    private static final String KEY_SFX_VOLUME = "sfxVolume";

    public static boolean isFullscreen() {
        return prefs.getBoolean(KEY_FULLSCREEN, true);
    }

    public static void setFullscreen(boolean value) {
        prefs.putBoolean(KEY_FULLSCREEN, value);
    }

    public static int getVolume() {
        return prefs.getInt(KEY_VOLUME, 100);
    }

    public static void setVolume(int value) {
        prefs.putInt(KEY_VOLUME, value);
    }

    public static int getSfxVolume() {
        return prefs.getInt(KEY_SFX_VOLUME, 100);
    }

    public static void setSfxVolume(int value) {
        prefs.putInt(KEY_SFX_VOLUME, value);
    }
}