package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

/**
 * A class derived from BoxBody that represents the button bodies on the world
 */
public class ButtonBody extends BoxBody{

    /**
     * Shape of the object
     */
    PolygonShape polyshape;

    /**
     * Construct of the box for the world's buttons
     *
     * @param world The world of the body
     * @param object The button itself
     */
    public ButtonBody(World world,MapObject object){     //x , y meters
        super(world,object);
        defineButton(object);
        this.object=object;
    }

    /**
     * Defines the button in the map
     *
     * @param object The buttons in the map
     */
    public void defineButton(MapObject object){

        polyshape = new PolygonShape();
        bdef = new BodyDef();
        fdef = new FixtureDef();

        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);

        b2body = world.createBody(bdef);

        polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        fdef.shape = polyshape;

        b2body.createFixture(fdef).setUserData(this);
    }
}
