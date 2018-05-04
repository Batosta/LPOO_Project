package com.mygdx.game;

/**
 * A model representing a Character.
 */
public class Character extends Body {

    public enum Moving {
        RIGHT,LEFT,STAND,JUMP,JUMPLEFT
    }

    Moving moving;

    /**
     * constructur of the character
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public Character(float x, float y){

        super(x,y);
    }

    public Moving getMoving(){
        return this.moving;
    }
}
