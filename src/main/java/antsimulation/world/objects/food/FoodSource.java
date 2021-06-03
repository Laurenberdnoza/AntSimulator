package antsimulation.world.objects.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.grid.Node;

import java.util.Optional;

public class FoodSource implements Displayable {

   private static final int STARTING_CHUNKS = 0;
   private static final int MAX_CHUNKS = 10;

   private final Node parent;

   private int remainingChunks = STARTING_CHUNKS;

   public FoodSource(Node parent) {
      this.parent = parent;
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
         final float x = parent.getLocation().x;
         final float y = parent.getLocation().y;
         Main.getApp().fill(0, 220, 0);
         Main.getApp().circle(x, y, (float) parent.getWidth());
      }
   }
}
