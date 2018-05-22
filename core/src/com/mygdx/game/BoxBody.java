package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

public class BoxBody {

    public World world;
    public Body b2body;
    protected float x;
    protected float y;

    public BoxBody(World world,float x,float y){
        this.world = world;
        this.x=x;
        this.y=y;
    }

    /**
     * Returns the x coordinate of the body.
     *
     * @return the x coordinate.
     */
    public float getX(){

        return x;
    }

    /**
     * Returns the y coordinate of the body.
     *
     * @return the y coordinate.
     */
    public float getY(){

        return y;
    }


}
