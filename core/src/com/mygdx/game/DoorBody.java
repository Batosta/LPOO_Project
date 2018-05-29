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

public class DoorBody{

    public World world;
    public Body body;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    PolygonShape polyshape;
    int color;

    float doortimer;

    boolean dooropened;
    /**
     * Constructor of the moving door body.
     *
     * @param world the box2d world.
     * @param object the object from the tiled map that defines the box2d body
     * @param color it decides the door color (0 if red, 1 if blue)
     */
    public DoorBody(World world,MapObject object,int color){     //x , y meters ??           color = 0 if red, 1 if blue
        dooropened=false;
        this.world = world;
        this.color = color;
        defineBody(object);
    }

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

    public boolean getDooropened(){
        return this.dooropened;
    }

    public void setDooropened(boolean dooropened){
        this.dooropened = dooropened;
    }

    public void update(float delta){

    }

}
