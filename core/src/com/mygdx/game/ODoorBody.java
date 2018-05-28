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

public class ODoorBody extends BoxBody{

    PolygonShape polyshape;
    int color;

    float doortimer;

    boolean dooropened;

    public ODoorBody(World world,MapObject object){
        super(world,object);
        dooropened=false;
        defineBody(object);
    }

    public void defineBody(MapObject object){
        bdef = new BodyDef();
        fdef = new FixtureDef();
        polyshape = new PolygonShape();
        System.out.println("dentro");
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);

        b2body = world.createBody(bdef);

        polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        fdef.shape = polyshape;
        b2body.createFixture(fdef);

        fdef.isSensor = false;

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