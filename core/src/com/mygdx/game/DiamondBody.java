package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

import java.awt.*;

public class DiamondBody{

    public World world;
    public Body body;
    protected BodyDef bdef;
    protected FixtureDef fdef;
    PolygonShape polyshape;
    boolean caught = false;

    public DiamondBody(World world,MapObject object){     //x , y meters ??
        this.world = world;
        defineDiamond(object);
    }

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
        body.createFixture(fdef).setUserData("diamond");

    }

    public void setCaught(){
        this.caught = !this.caught;
    }

}
