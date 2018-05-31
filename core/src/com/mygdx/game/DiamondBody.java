package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A class derived from BoxBody that represents the diamonds bodies on the world
 */
public class DiamondBody{

    /**
     * The world of the object
     */
    public World world;

    /**
     * The body itself
     */
    public Body body;

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
     * Boolean value that determines if a certain diamond has already been caught by the corresponding character or not
     */
    boolean caught = false;

    /**
     * Color of the diamond (red or blue)
     */
    int color;

    /**
     * Construct of the box for the world's diamonds
     *
     * @param world The world of the body
     * @param object The diamond itself
     * @param color The color of this diamond
     */
    public DiamondBody(World world,MapObject object, int color){     //x , y meters ??           color = 0 if red, 1 if blue

        this.world = world;
        this.color = color;
        defineDiamond(object);
    }

    /**
     * Defines the button in the map
     *
     * @param object The diamonds in the map
     */
    public void defineDiamond(MapObject object){
        polyshape = new PolygonShape();
        bdef = new BodyDef();
        fdef = new FixtureDef();

        Polygon poly = ((PolygonMapObject) object).getPolygon();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set(poly.getX()*GameScreen.PIXEL_TO_METER,poly.getY()*GameScreen.PIXEL_TO_METER);
        float[] vertices = poly.getVertices();
        float[] newVertices = new float[vertices.length];
        for (int i = 0; i < vertices.length; ++i) {
            newVertices[i] = vertices[i]*GameScreen.PIXEL_TO_METER;
        }

        body = world.createBody(bdef);
        polyshape.set(newVertices);
        fdef.shape = polyshape;
        fdef.isSensor = true;
        if(this.color == 1)
            body.createFixture(fdef).setUserData("bluediamond");
        else
            body.createFixture(fdef).setUserData("reddiamond");

    }
}
