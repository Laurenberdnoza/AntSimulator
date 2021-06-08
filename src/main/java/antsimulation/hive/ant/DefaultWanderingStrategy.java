package antsimulation.hive.ant;

import antsimulation.Main;
import org.mini2Dx.gdx.math.Vector2;

class DefaultWanderingStrategy implements WanderingStrategy {

    private static final float MAX_DIRECTION_CHANGING_ANGLE = 60f;

    private final Ant ant;

    DefaultWanderingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public Vector2 getDesiredDirection() {
        return ant.getDesiredDirection().rotateDeg(Main.getApp().random(-MAX_DIRECTION_CHANGING_ANGLE, MAX_DIRECTION_CHANGING_ANGLE));
    }
}
