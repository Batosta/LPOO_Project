package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

public class BoxCharacter extends BoxBody {

    public enum Moving {                // PARA METER NO CHARACTER. NAO VALE A PENA REPETIR para as duas personagens
        RIGHT,LEFT,STAND
    }

    public enum Jump {
        ASCENDING, DESCENDING, STOP
    }

    protected Moving moving;
    protected Jump jumpstate;
    protected boolean canjump;

    /**
     * constructor of the character b2body
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public BoxCharacter(World world, float x, float y){
        super(world, x,y);
    }

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

    public Moving getMoving(){
        return this.moving;
    }

    public Jump getJumpstate(){
        return this.jumpstate;
    }
}
