package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A class derived from BoxBody that represents the lake bodies on the world
 */
public class LakeBody {

    /**
     * The world of the object
     */
    public World world;

    /**
     * The body itself
     */
    public Body b2body;

    /**
     * A body definition that holds all the data needed to construct a rigid body
     */
    protected BodyDef bdef;

    /**
     * A fixture definition is used to create a fixture
     */
    protected FixtureDef fdef;

    /**
     * Shape of the object
     */
    PolygonShape polyshape;

    /**
     * The color of the door
     */
    int color;

    /**
     * Constructor of the lake body.
     *
     * @param world the box2d world.
     * @param object the object from the tiled map that defines the box2d body
     * @param color it decides the door color (0 if red, 1 if blue)
     */
    public LakeBody(World world, MapObject object, int color) {

        this.world = world;
        this.color = color;
        defineLake(object);
    }

    /**
     * Defines the lakes in the map
     *
     * @param object The lakes in the map
     */
    public void defineLake(MapObject object) {

        polyshape = new PolygonShape();
        bdef = new BodyDef();
        fdef = new FixtureDef();

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2) * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2) * GameScreen.PIXEL_TO_METER);

        b2body = world.createBody(bdef);

        polyshape.setAsBox((rect.getWidth() / 2) * GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2) * GameScreen.PIXEL_TO_METER);
        fdef.shape = polyshape;

        if (this.color == 0)
            b2body.createFixture(fdef).setUserData("redlake");
        else if (this.color == 1)
            b2body.createFixture(fdef).setUserData("bluelake");
        else
            b2body.createFixture(fdef).setUserData("greenlake");
    }
}
