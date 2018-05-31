package com.mygdx.game;

/**
 * A model representing any body.
 */
public class Body {

    /**
     * X coordinate of the object.
     */
    private float x;

    /**
     * Y coordinate of the object.
     */
    private float y;

    /**
     * Constructor of a body
     *
     * @param x The X coordinate of the body
     * @param y The Y coordinate of the body
     */
    public Body(float x, float y){

        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of the object.
     *
     * @return the x coordinate.
     */
    public float getX(){

        return x;
    }

    /**
     * Returns the y coordinate of the object.
     *
     * @return the y coordinate.
     */
    public float getY(){

        return y;
    }

    /**
     * Sets both coordinates of the object.
     *
     * @param newX the new x coordinate.
     * @param newY the new y coordinate.
     */
    public void setXY(float newX, float newY) {

        this.x = newX;
        this.y = newY;
    }
}