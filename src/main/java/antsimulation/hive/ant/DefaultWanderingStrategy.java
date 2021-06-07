package antsimulation.hive.ant;

import antsimulation.Main;

class DefaultWanderingStrategy implements WanderingStrategy {

    private final Ant ant;

    DefaultWanderingStrategy(Ant ant) {
        this.ant = ant;
    }

    @Override
    public float getRotation() {
        return Main.getApp().random(-Ant.TURN_AMOUNT / Main.getApp().frameRate, Ant.TURN_AMOUNT / Main.getApp().frameRate);
    }
}
