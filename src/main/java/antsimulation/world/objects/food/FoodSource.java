package antsimulation.world.objects.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.grid.Node;
import org.mini2Dx.gdx.math.Vector2;

import java.util.Optional;

public class FoodSource implements Displayable {

   private static final int STARTING_CHUNKS = 0;
   private static final int MAX_CHUNKS = 8;

   private final Vector2 position;
   private final float radius;

   private int remainingChunks = STARTING_CHUNKS;

   public FoodSource(Node parent) {
      this.radius = (float) parent.getWidth();
      this.position = new Vector2(parent.getLocation().x, parent.getLocation().y);
   }

   public Optional<FoodChunk> takeChunk() {
      if (remainingChunks >= 1) {
         remainingChunks--;
         return Optional.of(new FoodChunk());
      }
      return Optional.empty();
   }

   public void replenish() {
      remainingChunks = MAX_CHUNKS;
   }

   private boolean isEmpty() {
      return (remainingChunks == 0);
   }

   @Override
   public void display() {
      if (!isEmpty()) {
         Main.getApp().fill(30, 120, 5);
         Main.getApp().circle(position.x, position.y, radius);
      }
   }
}
