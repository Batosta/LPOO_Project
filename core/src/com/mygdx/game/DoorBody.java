package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.*;

/**
 * A class derived from BoxBody that represents the closed door bodies on the world
 */
public class DoorBody{

    /**
     * The world of the object
     */
    public World world;

    /**
     * The body itself
     */
    public Body body;

    /**
     * A body definition that holds all the data needed to construct a rigid body
     */
    protected BodyDef bdef;

    /**
     * A fixture definition is used to create a fixture
     */
    protected FixtureDef fdef;

    /**
     * Shape of the object
     */
    PolygonShape polyshape;

    /**
     * The color of the door
     */
    int color;

    /**
     * Boolean that is 0 in case the door is not open yet and 1 in case it is
     */
    boolean dooropened;

    /**
     * Constructor of the door body.
     *
     * @param world the box2d world.
     * @param object the object from the tiled map that defines the box2d body
     * @param color it decides the door color (0 if red, 1 if blue)
     */
    public DoorBody(World world,MapObject object,int color){
        dooropened=false;
        this.world = world;
        this.color = color;
        defineBody(object);
    }

    /**
     * Defines the doors in the map
     *
     * @param object The doors in the map
     */
    public void defineBody(MapObject object){
        bdef = new BodyDef();
        fdef = new FixtureDef();
        polyshape = new PolygonShape();

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);

        body = world.createBody(bdef);

        polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        fdef.shape = polyshape;

        fdef.isSensor = true;
        body.createFixture(fdef);
        if(this.color == 1)
            body.createFixture(fdef).setUserData("bluedoor");
        else
            body.createFixture(fdef).setUserData("reddoor");

    }

    /**
     * Function that returns if the door has been opened already
     *
     * @return the value of the opened door
     */
    public boolean getDooropened(){

        return this.dooropened;
    }

    /**
     * Sets the value of the variable dooropened to the received boolean
     *
     * @param dooropened
     */
    public void setDooropened(boolean dooropened) {
        
        this.dooropened = dooropened;
    }

    /**
     * Updates the door body
     *
     * @param delta time in seconds since last render
     */
    public void update(float delta){

    }
}
