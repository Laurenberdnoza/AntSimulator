package antsimulation.util;

import com.github.ryanp102694.geometry.RectangleObject;

public class MockRectangleObject implements RectangleObject {

    private String id;
    private String type;
    private double x;
    private double y;
    private double width;
    private double height;

    public MockRectangleObject(double xPos, double yPos, double width, double height) {
        this.x = xPos;
        this.y = yPos;
        this.width = width;
        this.height = height;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String s) {
        id = s;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String s) {
        type = s;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public void setX(Double aDouble) {
        x = aDouble;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public void setY(Double aDouble) {
        y = aDouble;
    }

    @Override
    public Double getH() {
        return height;
    }

    @Override
    public void setH(Double aDouble) {
        height = aDouble;
    }

    @Override
    public Double getW() {
        return width;
    }

    @Override
    public void setW(Double aDouble) {
        width = aDouble;
    }
}
