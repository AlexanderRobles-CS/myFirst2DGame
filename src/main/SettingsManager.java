package main;

import java.util.prefs.Preferences;

public class SettingsManager {

    private static final Preferences prefs = Preferences.userNodeForPackage(SettingsManager.class);
    private static final String KEY_FULLSCREEN = "fullscreen";

    public static boolean isFullscreen() {
        return prefs.getBoolean(KEY_FULLSCREEN, true);
    }

    public static void setFullscreen(boolean value) {
        prefs.putBoolean(KEY_FULLSCREEN, value);
    }
}