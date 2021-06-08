package antsimulation.hive;

import antsimulation.Main;
import antsimulation.hive.ant.Ant;
import antsimulation.world.Displayable;
import org.mini2Dx.gdx.math.Vector2;
import processing.core.PConstants;
import processing.core.PImage;

public class Hive implements Displayable {

    private static final PImage HIVE_TEXTURE = Main.getApp().loadImage("anthill.png");
    private static final int CIRCLE_RESOLUTION = 360;
    private static final float RADIUS = 16;

    private final Vector2 location;

    public Hive(Vector2 location, int initialAntAmount) {
        this.location = location;
        spawnAnts(initialAntAmount);
    }

    public void spawnAnts(int amount) {
        for (int i = 0; i < amount; i++) spawnAnt();
    }

    private void spawnAnt() {
        Ant newAnt = new Ant(location.cpy().add(new Vector2().setToRandomDirection().setLength(Main.getApp().random(0, RADIUS))));
        Main.getWorld().addEntity(newAnt);
    }

    @Override
    public void display() {
        Main.getApp().beginShape(PConstants.ELLIPSE);
        Main.getApp().texture(HIVE_TEXTURE);

        final float circleResolutionIncrement = 1f / CIRCLE_RESOLUTION;
        for (int i = 0; i < CIRCLE_RESOLUTION; i++) {
            float angle = i * circleResolutionIncrement;
            float x = (float) Math.cos(angle);
            float y = (float) Math.sin(angle);
            Main.getApp().vertex(x * RADIUS, y * RADIUS, x * RADIUS, y * RADIUS);
        }

        Main.getApp().endShape(Main.CLOSE);
    }
}
