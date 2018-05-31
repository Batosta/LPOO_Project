package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;

/**
 * A class derived from BoxBody that represents the Fire Boy on the world
 */
public class FireBoy2D extends BoxCharacter{

    /**
     * Construct of the box for the world's Fire Boy
     *
     * @param world The world itself
     * @param x The X coordinate of the Fire Boy's box
     * @param y The Y coordinate of the Fire Boy's box
     */
    public FireBoy2D(World world,float x, float y){     //x , y meters
        super(world,x,y);
        this.world = world;
        defineFireboy();
    }

    /**
     * Defines the Fire Boy in the map
     */
    public void defineFireboy(){
        bdef = new BodyDef();
        bdef.position.set(x,y);
        bdef.type = BodyDef.BodyType.DynamicBody;
        setB2body(world.createBody(bdef));

        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10*GameScreen.PIXEL_TO_METER,20*GameScreen.PIXEL_TO_METER);

        fdef.shape = shape;
        fdef.friction = 1f;
        getB2body().createFixture(fdef).setUserData("fireboy");

    }
}
