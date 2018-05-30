package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class BoxCharacter extends BoxBody {

    /**
     * The state Moving
     */
    public enum Moving {
        RIGHT,LEFT,STAND
    }

    /**
     * The state Jump
     */
    public enum Jump {
        ASCENDING, DESCENDING, STOP
    }

    /**
     * The state of the moving of the character
     */
    protected Moving moving;

    /**
     * The state of the jump of the character
     */
    protected Jump jumpstate;

    /**
     * A boolean that represents if the character is able to jump or not
     */
    protected boolean canjump;

    /**
     * Constructor of the character b2body
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public BoxCharacter(World world, float x, float y){
        super(world, x,y);
    }

    /**
     * Update
     *
     * @param delta
     */
    public void update(float delta){
        moving=Moving.STAND;
        jumpstate=Jump.STOP;
        if(b2body.getLinearVelocity().y>0){
            jumpstate=Jump.ASCENDING;
        }else if(b2body.getLinearVelocity().y<0){
            jumpstate=Jump.DESCENDING;
        } else jumpstate=Jump.STOP;

        if(b2body.getLinearVelocity().x>0){
            moving=Moving.RIGHT;
        }

        if (b2body.getLinearVelocity().x<0){
            moving=Moving.LEFT;
        }
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
