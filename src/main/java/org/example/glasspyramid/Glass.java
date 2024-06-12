package org.example.glasspyramid;

public class Glass {

    private final String id;
    private double fillAmount = 0;
    private double overflowLeft = 0;
    private double overflowRight = 0;
    private Glass aboveRightGlass;
    private Glass aboveLeftGlass;

    public Glass(String id) {
        this.id = id;
    }

    public boolean isFull() {
        return fillAmount >= 1;
    }

    public void increaseFillAmount(double timeIncrement, double fillRate) {
        fillAmount += incrementalFillAmount(timeIncrement, fillRate);
        distributeOverflow();
    }

    public void receiveOverflow() {
        if (aboveLeftGlass != null && aboveLeftGlass.getOverflowRight() > 0) {
            fillAmount += aboveLeftGlass.getOverflowRight();
            aboveLeftGlass.setOverflowRight(0);
        }
        if (aboveRightGlass != null && aboveRightGlass.getOverflowLeft() > 0) {
            fillAmount += aboveRightGlass.getOverflowLeft();
            aboveRightGlass.setOverflowLeft(0);
        }
        distributeOverflow();
    }

    private double incrementalFillAmount(double timeIncrement, double fillRate) {
        var fillRatePerSecond = 1 / fillRate;
        return fillRatePerSecond * timeIncrement;
    }

    private void distributeOverflow() {
        if (fillAmount > 1) {
            var overflow = fillAmount - 1;
            overflowLeft = overflow / 2;
            overflowRight = overflow / 2;
            fillAmount = 1;
        }
    }

    public void setAboveLeftGlass(Glass aboveLeftGlass) {
        this.aboveLeftGlass = aboveLeftGlass;
    }

    public void setAboveRightGlass(Glass aboveRightGlass) {
        this.aboveRightGlass = aboveRightGlass;
    }

    public void setOverflowLeft(double overflowLeft) {
        this.overflowLeft = overflowLeft;
    }

    public void setOverflowRight(double overflowRight) {
        this.overflowRight = overflowRight;
    }

    public double getOverflowLeft() {
        return overflowLeft;
    }

    public double getOverflowRight() {
        return overflowRight;
    }

    @Override
    public String toString() {
        return "Glass { " +
            "id: " + id +
            ", isFull: " + isFull() +
            ", amount: " + fillAmount +
            " }";
    }

}
