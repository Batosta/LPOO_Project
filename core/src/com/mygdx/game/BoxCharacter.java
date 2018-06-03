package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

/**
 * A class derived from BoxBody that represents the characters bodies on the world
 */
public class BoxCharacter extends BoxBody {

    public int getJumptimer() {
        return jumptimer;
    }

    public void setJumptimer(int jumptimer) {
        this.jumptimer = jumptimer;
    }

    /**
     * Possible states of Moving
     */
    public enum Moving {

        /**
         * Right direction
         */
        RIGHT,

        /**
         * Left direction
         */
        LEFT,

        /**
         * Standing
         */
        STAND
    }

    /**
     * Possible states while Jumping
     */
    public enum Jump {

        /**
         * While going up
         */
        ASCENDING,

        /**
         * While going down
         */
        DESCENDING,

        /**
         * Not jumping
         */
        STOP
    }

    /**
     * The state of the moving of the character
     */
    protected Moving moving;

    /**
     * The state of the jump of the character
     */
    protected Jump jumpstate;

    private int jumptimer;

    /**
     * A boolean that represents if the character is able to jump or not
     */
    protected boolean canjump;

    /**
     * If character is still alive or net
     */
    private boolean alive;

    /**
     * Constructor of the character b2body
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public BoxCharacter(World world, float x, float y){
        super(world, x,y);
        setJumptimer(0);
        alive=true;
    }

    /**
     * Update the box of the character
     *
     * @param delta time in seconds since last render
     */
    public void update(float delta){
        setJumptimer(getJumptimer() + 1);
        moving=Moving.STAND;

        jumpstate=Jump.STOP;
        if(getB2body().getLinearVelocity().y>0){
            jumpstate=Jump.ASCENDING;
        }else if(getB2body().getLinearVelocity().y<0){
            jumpstate=Jump.DESCENDING;
        } else jumpstate = Jump.STOP;

        if(getB2body().getLinearVelocity().x>0){
            moving=Moving.RIGHT;
        }

        if (getB2body().getLinearVelocity().x<0){
            moving=Moving.LEFT;
        }
    }

    /**
     * Function that returns if the character is still alive
     *
     * @return alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Function that sets alive for character
     *
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Function that returns the moving of the box of a character
     *
     * @return the moving
     */
    public Moving getMoving(){

        return this.moving;
    }

    /**
     * Function that returns the state of the jump of the box of a character
     *
     * @return the jumpstate
     */
    public Jump getJumpstate(){

        return this.jumpstate;
    }
}
