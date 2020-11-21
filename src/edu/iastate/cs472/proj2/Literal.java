package edu.iastate.cs472.proj2;

import java.util.LinkedList;

/**
 * @author Abhilash Tripathy
 */
public class Literal {

    private String literalValue;
    private boolean positive;

    public Literal (String literalValue, boolean positive) {
        this.literalValue = literalValue;
        this.positive = positive;
    }

    public String getLiteralValue() {
        return literalValue;
    }

    public void setLiteralValue(String literalValue) {
        this.literalValue = literalValue;
    }

    public boolean isPositive() {
        return positive;
    }

    public void setLiteralPositivity(boolean positive) {
        this.positive = positive;
    }

    @Override
    public String toString() {
        if(positive) return literalValue;
        else return "~"+literalValue;
    }
}
