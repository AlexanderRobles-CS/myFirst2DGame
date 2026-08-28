package environment;

import java.util.Random;

/**
 * Reusable flicker generator for dynamic light sources (torches, campfires,
 * lanterns, magic effects, etc). Attach one instance per light-emitting
 * object, call update() every tick, and read getOffset() when computing
 * the light radius.
 */
public class LightFlicker {

    private final Random rand = new Random();

    private double offset = 0;   // current smoothed flicker value (in pixels)
    private double target = 0;   // where offset is heading
    private int counter = 0;

    private final double range;            // max +/- pixels of radius jitter
    private final int retargetFrames;      // how often to pick a new target
    private final double smoothing;        // higher = snappier, lower = smoother

    public LightFlicker() {
        this(30.0, 6, 0.50);
    }

    public LightFlicker(double range, int retargetFrames, double smoothing) {
        this.range = range;
        this.retargetFrames = retargetFrames;
        this.smoothing = smoothing;
    }

    /** Call once per game tick. */
    public void update() {
        counter++;
        if (counter >= retargetFrames) {
            target = (rand.nextDouble() * 2 - 1) * range;
            counter = 0;
        }
        offset += (target - offset) * smoothing;
    }

    /** Current flicker offset in pixels, to add to a base light radius. */
    public double getOffset() {
        return offset;
    }
}