package antsimulation.utils;

import processing.core.PVector;

public class VectorUtils {

    /**
     * Rotate a given vector towards a target vector by a certain amount.
     * @param mutable The vector to rotate (in-place).
     * @param target The vector to rotate the first vector towards.
     * @param amount The amount to rotate by (degrees).
     */
    public static void rotateTowards(PVector mutable, PVector target, float amount) {
        double radians = Math.toRadians(amount);
        PVector crossProduct = mutable.cross(target);

        mutable.mult((float) Math.cos(radians)).add((mutable.cross(crossProduct.setMag(1f))).mult((float) Math.sin(radians)));
    }
}
