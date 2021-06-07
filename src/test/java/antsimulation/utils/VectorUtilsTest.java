package antsimulation.utils;

import org.junit.jupiter.api.Test;
import processing.core.PVector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorUtilsTest {

    private static final float DELTA = 0.05f;

    @Test
    void shouldCorrectlyRotateVectorsTowardsTargetVectorSimple1() {
        PVector origin = new PVector(1, 1);
        PVector direction = new PVector(0, 1);
        PVector target = new PVector(2, 1);

        PVector result = VectorUtils.rotateTowards(origin, direction, target, 90);
        assertEquals(1, result.x, DELTA);
        assertEquals(0, result.y, DELTA);
    }

    @Test
    void shouldCorrectlyRotateVectorsTowardsTargetVectorSimple2() {
        PVector origin = new PVector(0, 0);
        PVector direction = new PVector(1, 0);
        PVector target = new PVector(0, -1);

        PVector result = VectorUtils.rotateTowards(origin, direction, target, 90);
        assertEquals(0, result.x, DELTA);
        assertEquals(-1, result.y, DELTA);
    }
}
