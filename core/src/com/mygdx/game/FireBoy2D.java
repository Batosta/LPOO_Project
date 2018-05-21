package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

public class FireBoy2D extends Sprite {


    public World world;
    public Body b2body;

    public FireBoy2D(World world){
        this.world = world;
        defineFireboy();
    }

    public void defineFireboy(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(50*GameScreen.PIXEL_TO_METER,50*GameScreen.PIXEL_TO_METER);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5*GameScreen.PIXEL_TO_METER);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
