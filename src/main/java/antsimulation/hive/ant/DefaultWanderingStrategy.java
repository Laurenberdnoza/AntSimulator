package antsimulation.hive.ant;

import antsimulation.Main;
import org.mini2Dx.gdx.math.Vector2;

class DefaultWanderingStrategy implements WanderingStrategy {

    private static final float DECISION_COOLDOWN = 1.5f;

    private final Ant ant;
    private float cooldown = DECISION_COOLDOWN;

    DefaultWanderingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public Vector2 getDesiredDirection() {
        cooldown = Math.max(0, cooldown - (1 / Main.getApp().frameRate));

        if (cooldown == 0) {
            cooldown = DECISION_COOLDOWN;
            return new Vector2().setToRandomDirection();
        } else {
            return ant.getDesiredDirection();
        }
    }
}
