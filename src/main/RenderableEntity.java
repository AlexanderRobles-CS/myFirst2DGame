package main;

import java.awt.Graphics2D;

public interface RenderableEntity {
    int getWorldY();
    void draw(Graphics2D g);
}
