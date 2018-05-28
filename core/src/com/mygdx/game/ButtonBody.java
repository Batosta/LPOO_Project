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

public class ButtonBody extends BoxBody{

    PolygonShape polyshape;
    boolean caught = false;
    int color;

    public ButtonBody(World world,MapObject object){     //x , y meters
        super(world,object);
        defineButton(object);
    }

    public void defineButton(MapObject object){
        polyshape = new PolygonShape();
        bdef = new BodyDef();
        fdef = new FixtureDef();

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);

        b2body = world.createBody(bdef);

        polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        fdef.shape = polyshape;

        b2body.createFixture(fdef).setUserData("button");

    }

    public boolean getCaught(){ return this.caught;}
    public void setCaught(){
        this.caught = !this.caught;
    }

}
