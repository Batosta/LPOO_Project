package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WaterGirl2D extends BoxCharacter{

    public WaterGirl2D(World world, float x, float y){     //x , y meters
        super(world,x,y);
        this.world = world;
        defineWaterGirl();
    }

    public void defineWaterGirl(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
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
