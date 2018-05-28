package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.*;

public class FireBoy2D extends BoxCharacter{

    public FireBoy2D(World world,float x, float y){     //x , y meters
        super(world,x,y);
        this.world = world;
        defineFireboy();
    }

    public void defineFireboy(){
        bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10*GameScreen.PIXEL_TO_METER,20*GameScreen.PIXEL_TO_METER);

        fdef.shape = shape;
        fdef.friction = 1f;
        b2body.createFixture(fdef).setUserData("fireboy");

    }




}
