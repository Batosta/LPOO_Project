package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Represents all the bodies in the world
 */
public class BoxBody {

    /**
     * The world of the object
     */
    public World world;

    /**
     * The body itself
     */
    private Body b2body;

    /**
     * A body definition that holds all the data needed to construct a rigid body
     */
    protected BodyDef bdef;

    /**
     * A fixture definition is used to create a fixture
     */
    protected FixtureDef fdef;

    /**
     * The object itself on the map
     */
    protected MapObject object;

    /**
     * The X position of the object
     */
    protected float x;

    /**
     * The Y position of the object
     */
    protected float y;

    /**
     * Constructor of BoxBody
     *
     * @param world the whole world of the object
     * @param object the object itself
     */
    public BoxBody(World world,MapObject object){     //x , y meters
        this.world = world;
        this.object=object;
    }

    /**
     * Another constructor of BoxBody
     *
     * @param world the whole world of the object
     * @param x the X position of the object
     * @param y the Y position of the object
     */
    public BoxBody(World world,float x,float y){
        this.world = world;
        this.x=x;
        this.y=y;
    }

    /**
     * Function that returns the Y position of the character
     *
     * @return the Y position of the character
     */
    public float getY(){

        return this.y;
    }

    /**
     * Function that returns the X position of the character
     *
     * @return the X position of the character
     */
    public float getX(){

        return this.x;
    }

    /**
     * Function used to return the object in the world
     *
     * @return the object in the map
     */
    public MapObject getMapObject(){

        return this.object;
    }

    /**
     * Function that returns the body itself
     *
     * @return The body itself
     */
    public Body getB2body() {
        return b2body;
    }

    /**
     * Function that sets the body itself
     *
     * @param b2body The new body itself
     */
    public void setB2body(Body b2body) {
        this.b2body = b2body;
    }
}
