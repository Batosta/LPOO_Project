package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.*;

/**
 * A class derived from BoxBody that represents the diamonds bodies on the world
 */
public class DiamondBody{

    public World world;
    public Body body;
    protected BodyDef bdef;
    protected FixtureDef fdef;

    /**
     * Shape of the object
     */
    PolygonShape polyshape;
    boolean caught = false;
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

    /**
     * Function that returns if the diamond has been yet caught
     *
     * @return if the diamond has already been caught
     */
    public boolean getCaught(){

        return this.caught;}

    /**
     * Function that sets the oposite of the value of the caught variable
     */
    public void setCaught(){

        this.caught = !this.caught;
    }

}
