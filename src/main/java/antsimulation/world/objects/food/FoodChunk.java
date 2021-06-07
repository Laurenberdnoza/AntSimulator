package antsimulation.world.objects.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import org.mini2Dx.gdx.math.Vector2;

public class FoodChunk implements Locatable, Displayable {

    private static final float RADIUS = 2f;

    private Vector2 position;

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void display() {
        Main.getApp().noStroke();
        Main.getApp().fill(0, 200, 0);
        Main.getApp().circle(position.x, position.y, 2 * RADIUS);
    }

    @Override
    public Vector2 getLocation() {
        return position;
    }
}
