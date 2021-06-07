package antsimulation.hive.ant;

import antsimulation.Main;
import processing.core.PVector;

class DefaultWanderingStrategy implements WanderingStrategy {

    private final Ant ant;

    DefaultWanderingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public PVector getDesiredDirection() {
        return ant.getDesiredDirection().rotate(
                Main.getApp().random(-Ant.TURN_AMOUNT / Main.getApp().frameRate, Ant.TURN_AMOUNT / Main.getApp().frameRate)
        );
    }
}
