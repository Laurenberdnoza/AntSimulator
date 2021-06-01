package antsimulation.world.grid;

import antsimulation.world.Locatable;

public class Grid {

    private final Node[][] nodes;

    public Grid(int width, int height) {
        this.nodes = new Node[height][width];
    }

    public Node[][] getNodes() {
        return nodes;
    }
}
