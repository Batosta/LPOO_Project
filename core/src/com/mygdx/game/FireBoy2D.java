package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.*;

public class FireBoy2D extends BoxCharacter{

    public FireBoy2D(World world,float x, float y){
        super(world,x,y);
        this.world = world;
        defineFireboy();
    }

    public void defineFireboy(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x*GameScreen.PIXEL_TO_METER,y*GameScreen.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(15*GameScreen.PIXEL_TO_METER,20*GameScreen.PIXEL_TO_METER);

        fdef.shape = shape;
        fdef.friction = 1f;
        b2body.createFixture(fdef);

    }




}
