package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * A class derived from BoxBody that represents the Water Girl on the world
 */
public class WaterGirl2D extends BoxCharacter{

    /**
     * Construct of the box for the world's Water Girl
     *
     * @param world The world itself
     * @param x The X coordinate of the Water Girl's box
     * @param y The Y coordinate of the Water Girl's box
     */
    public WaterGirl2D(World world, float x, float y){     //x , y meters
        super(world,x,y);
        this.world = world;
        defineWaterGirl();
    }

    /**
     * Defines the Water Girl in the map
     */
    public void defineWaterGirl(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        setB2body(world.createBody(bdef));

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10*GameScreen.PIXEL_TO_METER,20*GameScreen.PIXEL_TO_METER);

        fdef.shape = shape;
        fdef.friction = 1f;
        getB2body().createFixture(fdef).setUserData("watergirl");

    }

}
