package antsimulation.world.grid;

import antsimulation.world.Displayable;
import antsimulation.world.Updatable;
import antsimulation.world.World;
import processing.core.PVector;

public class Grid implements Updatable, Displayable {

    private final World parent;
    private final Node[][] nodes;
    private final int width;
    private final int height;
    private final double cellWidth;
    private final double cellHeight;

    public Grid(World parent, int width, int height) {
        this.parent = parent;
        this.nodes = new Node[height][width];
        this.width = width;
        this.height = height;
        this.cellHeight = parent.getHeight() / (double) height;
        this.cellWidth = parent.getWidth() / (double) width;

        populateGridWithNodes();
    }

    private void populateGridWithNodes() {
        for (int j = 0; j < nodes.length; j++) {
            for (int i = 0; i < nodes[0].length; i++) {
                nodes[j][i] = new Node(
                        new PVector((float) (i * cellWidth), (float) (j * cellHeight)), cellWidth, cellHeight
                );
            }
        }
    }

    public Node getNodeAt(PVector location) {
        final int y = Math.min((int) (location.y / cellHeight), height - 1);
        final int x = Math.min((int) (location.x / cellWidth), width - 1);
        if (!parent.inBounds(new PVector(x, y))) {
            throw new RuntimeException(String.format("Queried position (%d, %d) not in world bounds!", x, y));
        }

        return nodes[y][x];
    }

    @Override
    public void display() {
        for (Node[] nodeRow : nodes)
            for (Node node : nodeRow)
                node.display();
    }

    @Override
    public void update() {
        for (Node[] nodeRow : nodes)
            for (Node node : nodeRow)
                node.update();
    }
}
