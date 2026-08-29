package environment;

import entity.Entity;
import entity.NPC_Necromancer;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.CompositeContext;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;
import main.GamePanel;
import object.SuperObject;

public class Lighting {

    GamePanel gp;
    volatile BufferedImage darknessFilter;
    private BufferedImage backBuffer;
    int playerLightSize;

    private static final float[] FRACTIONS = {0f,0.4f,0.5f,0.6f,0.65f,0.7f,0.75f,0.8f,0.85f,0.9f,0.95f,1f};
    private static final Color[] COLORS = {
        new Color(0,0,0,0.10f), new Color(0,0,0,0.42f), new Color(0,0,0,0.52f),
        new Color(0,0,0,0.61f), new Color(0,0,0,0.69f), new Color(0,0,0,0.76f),
        new Color(0,0,0,0.82f), new Color(0,0,0,0.87f), new Color(0,0,0,0.91f),
        new Color(0,0,0,0.94f), new Color(0,0,0,0.97f), new Color(0,0,0,1.0f)
    };
    private static class LightSource {
        int x, y, size;
        LightSource(int x, int y, int size) { this.x = x; this.y = y; this.size = size; }
    }

    public Lighting(GamePanel gp, int playerLightSize) {
        this.gp = gp;
        this.playerLightSize = playerLightSize;
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        backBuffer = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public void update() {

        Graphics2D g2 = backBuffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // clear reused buffer
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // fill whole screen with base (maximum) darkness first
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(COLORS[COLORS.length - 1]);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // gather all light sources this frame
        List<LightSource> lights = new ArrayList<>();

        int px = gp.player.screenX + gp.tileSize / 2;
        int py = gp.player.screenY + gp.tileSize / 2;
        lights.add(new LightSource(px, py, playerLightSize));

        for (SuperObject obj : gp.obj) {
            if (obj == null) continue;
            if (obj instanceof object.OBJ_Torch torch) {

                int cx = obj.worldX - gp.player.worldX + gp.player.screenX + gp.tileSize / 2;
                int cy = obj.worldY - gp.player.worldY + gp.player.screenY + gp.tileSize / 2;

                int baseLightSize = gp.tileSize * 4;
                int torchLightSize = (int) (baseLightSize + torch.getLightFlicker());

                lights.add(new LightSource(cx, cy, torchLightSize));
            }
        }

        for (Entity npc : gp.npc) {
            if (npc != null && npc instanceof NPC_Necromancer) {
                NPC_Necromancer necro = (NPC_Necromancer) npc;

                int cx = npc.worldX - gp.player.worldX + gp.player.screenX + gp.tileSize / 2;
                int cy = npc.worldY - gp.player.worldY + gp.player.screenY + gp.tileSize / 2;

                int baseLightSize = gp.tileSize * 3;
                int necroLightSize = (int) (baseLightSize + necro.getLightFlicker());

                lights.add(new LightSource(cx, cy, necroLightSize));
            }
        }

        // draw each light using MIN-alpha compositing so overlaps take
        // whichever light is brighter (less dark), instead of stacking darkness.
        // Clip to each light's bounding box so the slow per-pixel composite loop
        // only runs over that light's area, not the whole screen, every time.
        g2.setComposite(MinAlphaComposite.INSTANCE);
        for (LightSource ls : lights) {
            Rectangle bounds = new Rectangle(
                (int) (ls.x - ls.size / 2.0),
                (int) (ls.y - ls.size / 2.0),
                ls.size, ls.size
            );

            g2.setClip(bounds);

            RadialGradientPaint gPaint = new RadialGradientPaint(
                ls.x, ls.y, ls.size / 2f, FRACTIONS, COLORS);
            g2.setPaint(gPaint);
            g2.fill(new Ellipse2D.Double(
                ls.x - ls.size / 2.0, ls.y - ls.size / 2.0, ls.size, ls.size));
        }
        g2.setClip(null);

        g2.dispose();

        // swap buffers: darknessFilter becomes what backBuffer was, and vice versa.
        // draw() only ever sees a fully complete frame, never a half-drawn one.
        BufferedImage temp = darknessFilter;
        darknessFilter = backBuffer;
        backBuffer = temp;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }

    private static class MinAlphaComposite implements Composite {

        static final MinAlphaComposite INSTANCE = new MinAlphaComposite();

        @Override
        public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
            return new CompositeContext() {
                @Override
                public void dispose() {}

                @Override
                public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
                    int w = Math.min(src.getWidth(), dstIn.getWidth());
                    int h = Math.min(src.getHeight(), dstIn.getHeight());

                    int[] srcPixel = new int[4];
                    int[] dstPixel = new int[4];
                    int[] outPixel = new int[4];

                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            src.getPixel(x, y, srcPixel);
                            dstIn.getPixel(x, y, dstPixel);

                            int srcA = srcPixel[3];
                            int dstA = dstPixel[3];

                            if (srcA < dstA) {
                                outPixel[0] = srcPixel[0];
                                outPixel[1] = srcPixel[1];
                                outPixel[2] = srcPixel[2];
                                outPixel[3] = srcA;
                            } else {
                                outPixel[0] = dstPixel[0];
                                outPixel[1] = dstPixel[1];
                                outPixel[2] = dstPixel[2];
                                outPixel[3] = dstA;
                            }

                            dstOut.setPixel(x, y, outPixel);
                        }
                    }
                }
            };
        }
    }
}