package antsimulation.hive.ant;

import antsimulation.Main;
import processing.core.PVector;

class DefaultWanderingStrategy implements WanderingStrategy {

    private static final float DECISION_COOLDOWN = 1f;

    private final Ant ant;
    private float cooldown = DECISION_COOLDOWN;

    DefaultWanderingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public PVector getDesiredDirection() {
        cooldown = Math.max(0, cooldown - (1 / Main.getApp().frameRate));

        if (cooldown == 0) {
            cooldown = DECISION_COOLDOWN;
            return PVector.random2D().setMag(ant.getMovementSpeed() / Main.getApp().frameRate);
        } else {
            return ant.getDesiredDirection();
        }
    }
}
