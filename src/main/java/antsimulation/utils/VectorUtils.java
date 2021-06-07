package antsimulation.utils;

import processing.core.PVector;

public class VectorUtils {

    /**
     * Return a vector rotated towards a target vector by a certain amount.
     * @param origin The origin of the direction vector.
     * @param direction The vector to rotate.
     * @param target The vector to rotate the first vector towards.
     * @param amount The amount to rotate by (degrees).
     * @return A new vector representing the desired transformation.
     */
    public static PVector rotateTowards(PVector origin, PVector direction, PVector target, float amount) {
        final PVector directionToTarget = target.copy().sub(origin.copy());
        final float turnAmount = (float) Math.toRadians(amount);

        final float angleDiff = (float) Math.min(
                Math.abs(2 * Math.PI - (directionToTarget.heading() - direction.heading())),
                Math.abs(directionToTarget.heading() - direction.heading())
        );
        final float actualTurn = (Math.abs(angleDiff) < Math.abs(turnAmount)) ? angleDiff : turnAmount;

        return (directionToTarget.heading() > direction.heading())
                ? direction.rotate(actualTurn)
                : direction.rotate(-actualTurn);
    }
}
