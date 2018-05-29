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

    boolean opendoor;

    private float oldwidth;

    private float oldheight;

    public ODoorBody(World world,MapObject object){
        super(world,object);
        opendoor=false;
        defineBody(object);
    }

    public void defineBody(MapObject object){
        bdef = new BodyDef();
        fdef = new FixtureDef();
        polyshape = new PolygonShape();
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        b2body = world.createBody(bdef);

        polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        oldwidth= (rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER;
        oldheight = (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER;
        fdef.shape = polyshape;
        b2body.createFixture(fdef).setUserData(object.getName());

        fdef.isSensor = false;

    }

    public boolean getOpendoor(){
        return this.opendoor;
    }

    public void setOpendoor(boolean opendoor){
        this.opendoor = opendoor;
    }

    public void update(float delta){
        System.out.println("fora");
        if(this.opendoor==true){
            System.out.println("dentro");
            b2body.getFixtureList().get(0).setSensor(true);
        } else b2body.getFixtureList().get(0).setSensor(false);
    }

    /*                ESTE ERA PA O BODY DA PORTA IR DIMINUINDO D TAMANHO

    public void resize(float delta) {
        b2body.destroyFixture(b2body.getFixtureList().get(0));

        polyshape.setAsBox(oldwidth*0.9f,oldheight);
        oldwidth=oldwidth*0.9f;
        fdef.shape = polyshape;
        //polyshape.dispose();

        b2body.createFixture(fdef).setUserData(object.getName());
        b2body.setTransform(b2body.getPosition().x-0.01f,b2body.getPosition().y-0.01f,0);

    }*/

}