package com.mygdx.game;

/**
 * A model representing a Character.
 */
public class Character extends Body {

    public enum Moving {
        RIGHT,LEFT,STAND
    }

    Moving moving;

    private boolean grounded;

    /**
     * constructur of the character
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public Character(float x, float y){

        super(x,y);
        grounded=true;
    }

    public Moving getMoving(){
        return this.moving;
    }

    public boolean getGrounded(){
        return this.grounded;
    }

    public void setGrounded(boolean grounded){
        this.grounded = grounded;
    }
}
