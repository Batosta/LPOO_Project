package com.mygdx.game;

public class Lever extends Body {

    /**
     * The boolean that defines if the lever is either on or off.
     */
    private boolean on;

    /**
     * constructur of the lever.
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public Lever(float x, float y){

        super(x, y);
        on = false;
    }
}
