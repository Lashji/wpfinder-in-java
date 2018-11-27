package utils;

public class StageBounds {

    private double x;
    private double y;
    private double width;
    private double height;

    public StageBounds() {
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {

        return x;
    }

    @Override
    public String toString() {
        return "StageBounds{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
