package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.*;

public class ODoorBody extends BoxBody{

    PolygonShape polyshape;
    int color;

    float doortimer;

    public enum DoorState {                // PARA METER NO CHARACTER. NAO VALE A PENA REPETIR para as duas personagens
        CLOSED,OPENED,OPENING,CLOSING
    }

    public DoorState doorstate;

    boolean buttonpressed;

    boolean fullyopened;

    private float oldwidth;

    private float width;

    private float height;

    public ODoorBody(World world,MapObject object){
        super(world,object);
        doorstate=DoorState.CLOSED;
        buttonpressed=false;
        fullyopened=false;
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
        width = oldwidth = (rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER;
        height = (rect.getHeight()/2)*GameScreen.PIXEL_TO_METER;
        fdef.shape = polyshape;
        b2body.createFixture(fdef).setUserData(object.getName());

        fdef.isSensor = false;

    }

    public boolean getButtonpressed(){
        return this.buttonpressed;
    }

    public void setButtonpressed(boolean buttonpressed){
        this.buttonpressed = buttonpressed;
    }

    public void update(float delta){
        if(this.buttonpressed) {              // button is pressed
            if(doorstate!=DoorState.OPENED)
            doorstate=DoorState.OPENING;
        } else {
            if(doorstate!=DoorState.CLOSED){        // button was released
                doorstate=DoorState.CLOSING;
            }
        }
        if(doorstate == DoorState.CLOSING || doorstate == DoorState.OPENING)
        resize(delta);
    }

    /*                ESTE ERA PA O BODY DA PORTA IR DIMINUINDO D TAMANHO
     */
    public void resize(float delta) {

        b2body.destroyFixture(b2body.getFixtureList().get(0));


        Vector2[] newVertices = new Vector2[4]; //It is a box
        newVertices[0] = new Vector2(-1.5f,-height);
        newVertices[1] = new Vector2(oldwidth,-height);
        newVertices[2] = new Vector2(oldwidth,height);
        newVertices[3] = new Vector2(-1.5f,height);
        polyshape.set(newVertices);
        //polyshape.setAsBox(oldwidth*0.9f,oldheight);
        if(doorstate==DoorState.OPENING) {
            oldwidth = oldwidth - 0.04f;
            if(oldwidth<-1.3f) {
                doorstate=DoorState.OPENED;
            }
        }
        else if (doorstate==DoorState.CLOSING) {
            oldwidth = oldwidth + 0.04f;
            if(oldwidth>=width) {
                doorstate=DoorState.CLOSED;
            }
        }


        fdef.shape = polyshape;
        //polyshape.dispose();

        b2body.createFixture(fdef).setUserData(object.getName());
        //b2body.setTransform(b2body.getPosition().x,b2body.getPosition().y,0);

    }

}