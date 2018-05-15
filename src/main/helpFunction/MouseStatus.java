package main.helpFunction;

public class MouseStatus {

    private double x;
    private double y;
    private boolean primaryButtonDown;
    private boolean secondaryButtonDown;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isPrimaryButtonDown() {
        return primaryButtonDown;
    }

    public void setPrimaryButtonDown(boolean primaryButtonDown) {
        this.primaryButtonDown = primaryButtonDown;
    }

    public boolean isSecondaryButtonDown() {
        return secondaryButtonDown;
    }

    public void setSecondaryButtonDown(boolean secondaryButtonDown) {
        this.secondaryButtonDown = secondaryButtonDown;
    }
}


