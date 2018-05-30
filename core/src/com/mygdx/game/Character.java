package com.mygdx.game;

import static com.mygdx.game.Character.Jump.ASCENDING;
import static com.mygdx.game.Character.Jump.DESCENDING;
import static com.mygdx.game.Character.Jump.STOP;
import static com.mygdx.game.Character.Moving.LEFT;
import static com.mygdx.game.Character.Moving.RIGHT;

/**
 * A model representing a Character.
 */
public class Character extends Body {

    public enum Moving {
        RIGHT,LEFT,STAND
    }

    Moving moving;

    private boolean grounded;

    private static final float MOVE_SPEED = 10f;
    private float JUMP_SPEED = 10f;

    public enum Jump {
        ASCENDING, DESCENDING, STOP
    }

    Jump jumpstate = STOP;

    /**
     * Constructor of the character
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public Character(float x, float y){

        super(x,y);
        grounded=true;
    }

    /**
     * Function that returns the moving of the character
     *
     * @return the moving
     */
    public Moving getMoving(){
        return this.moving;
    }

    /**
     * Function that returns if the character is on the floor or not
     *
     * @return a boolean that is true if the character is on floor and false if not
     */
    public boolean getGrounded(){
        return this.grounded;
    }

    /**
     * Function that sets if the character is on the floor or not
     *
     * @param grounded a boolean that is true if the character is on floor and false if not
     */
    public void setGrounded(boolean grounded){
        this.grounded = grounded;
    }

    /**
     * Moves the character to the right
     *
     * @param delta ?
     */
    public void moveRight(float delta) {

        this.setXY(this.getX() + delta * MOVE_SPEED, this.getY());
        moving=RIGHT;
    }

    /**
     * Moves the character to the left
     *
     * @param delta ?
     */
    public void moveLeft(float delta) {
        this.setXY(this.getX() - delta * MOVE_SPEED, this.getY());
        moving=LEFT;
    }

    /**
     * Makes the character jump
     *
     * @param delta ?
     */
    public void jump(float delta){
        if(getGrounded()) {
            jumpstate = ASCENDING;
            setGrounded(false);
        }
        if(jumpstate == ASCENDING) {       //está a subir
            this.setXY(this.getX(), this.getY() + delta * JUMP_SPEED);
            JUMP_SPEED-=0.5;
            if(JUMP_SPEED<=0){
                jumpstate=DESCENDING;
                JUMP_SPEED=0;
            }
        } else
        if(jumpstate == DESCENDING){       //está a descer
            this.setXY(this.getX(), this.getY() - delta * JUMP_SPEED);
            JUMP_SPEED+=0.5;
            if(JUMP_SPEED > 10f){  //TODO      A condiçao de paragem vai ter a ver com as colisões e outras cenas. nao é esta
                setGrounded(true);
                jumpstate=STOP;
            }
        }
    }
}