package main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class DisplayManager {

    private final JFrame window;
    private final GamePanel gamePanel;
    private final Image windowIcon;

    public DisplayManager(JFrame window, GamePanel gamePanel, Image windowIcon) {
        this.window = window;
        this.gamePanel = gamePanel;
        this.windowIcon = windowIcon;
    }

    public void hideCursor() {
        BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
            cursorImg, new Point(0, 0), "blank cursor");
        gamePanel.setCursor(blankCursor);
    }

    public void showDefaultCursor() {
        gamePanel.setCursor(Cursor.getDefaultCursor());
    }

    public void applyDisplayMode(boolean fullscreen) {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        window.setVisible(false);
        window.dispose();

        if (fullscreen && device.isFullScreenSupported()) {
            // TRUE FULLSCREEN: no title bar, no icon visible (nowhere to show it)
            window.setUndecorated(true);
            window.setResizable(false);
            device.setFullScreenWindow(window);
        } else {
            // WINDOWED/MAXIMIZED: decorated title bar with icon + minimize + close
            device.setFullScreenWindow(null);
            window.setUndecorated(false);
            window.setResizable(true);
            window.setMinimumSize(new Dimension(800, 600));
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        if (windowIcon != null) {
            window.setIconImage(windowIcon);
        }

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void toggleFullscreen() {
        boolean newValue = !SettingsManager.isFullscreen();
        System.out.println("Toggling fullscreen to: " + newValue);
        SettingsManager.setFullscreen(newValue);
        applyDisplayMode(newValue);
    }
}