package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * A class derived from BoxBody that represents the platforms on the world
 */
public class PlatformBody extends BoxBody{

    /**
     * Shape of the platform
     */
    PolygonShape polyshape;

    /**
     * Possible states of the platform
     */
    public enum PlatformState {

        /**
         * When platform is closed
         */
        CLOSED,

        /**
         * When platform is open
         */
        OPENED,

        /**
         * While the platform is opening
         */
        OPENING,

        /**
         * While the platform is closing
         */
        CLOSING
    }

    /**
     * The state of the platform (Check DPlatformStateoorState)
     */
    public PlatformState platformState;

    /**
     * The platform's corresponding button is being pressed
     */
    boolean buttonpressed;

    /**
     * Boolean value that is true if the platform has been completly opened and false if not
     */
    boolean fullyopened;

    /**
     * Last width the user saw when opening/closing the platforms
     */
    private float oldwidth;

    /**
     * Last heigth the user saw when opening/closing the platforms
     */
    private float oldheight;

    /**
     * Platform's width size
     */
    private float width;

    /**
     * Platform's height size
     */
    private float height;

    /**
     * Platform's direction of movement
     */
    private int dir;

    /**
     * Constructor of the platform body.
     *
     * @param world the box2d world.
     * @param object the object from the tiled map that defines the box2d body
     * @param dir platform's direction of movement
     */
    public PlatformBody(World world, MapObject object, int dir){             // dir == 0 if horizontal; 1 if vertical
        super(world,object);
        this.dir=dir;
        platformState=PlatformState.CLOSED;
        buttonpressed=false;
        fullyopened=false;
        defineBody(object);
    }

    /**
     * Defines the platforms in the map
     *
     * @param object The doors in the map
     */
    public void defineBody(MapObject object){
        bdef = new BodyDef();
        fdef = new FixtureDef();
        polyshape = new PolygonShape();
        Rectangle rect = ((RectangleMapObject) object).getRectangle();

        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        setB2body(world.createBody(bdef));

        polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
        width = oldwidth = (rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER;
        height = oldheight = (rect.getHeight()/2)*GameScreen.PIXEL_TO_METER;
        fdef.shape = polyshape;
        getB2body().createFixture(fdef).setUserData(object.getName());

        fdef.isSensor = false;

    }

    /**
     * Sets the button corresponding to a certain platform as pressed
     */
    public void setButtonpressed(boolean buttonpressed){
        this.buttonpressed = buttonpressed;
    }

    /**
     * Updates the platform's body
     *
     * @param delta time in seconds since last render
     */
    public void update(float delta){
        if(this.buttonpressed) {              // button is pressed
            if(platformState!=PlatformState.OPENED)
                platformState=PlatformState.OPENING;
        } else {
            if(platformState!=PlatformState.CLOSED){        // button was released
                platformState=PlatformState.CLOSING;
            }
        }
        if(platformState == PlatformState.CLOSING || platformState == PlatformState.OPENING)
            if(this.dir==0)
        resizeHor(delta);
        else resizeVer(delta);
    }

    /**
     * Functions that changes the body's dimensions when the platform is opening/closing in the horizontal
     */
    public void resizeHor(float delta) {

        getB2body().destroyFixture(getB2body().getFixtureList().get(0));


        Vector2[] newVertices = new Vector2[4]; //It is a box
        newVertices[0] = new Vector2(-1.5f,-height);
        newVertices[1] = new Vector2(oldwidth,-height);
        newVertices[2] = new Vector2(oldwidth,height);
        newVertices[3] = new Vector2(-1.5f,height);
        polyshape.set(newVertices);
        //polyshape.setAsBox(oldwidth*0.9f,oldheight);
        if(platformState==PlatformState.OPENING) {
            oldwidth = oldwidth - 0.04f;
            if(oldwidth<-1.3f) {
                platformState=PlatformState.OPENED;
            }
        }
        else if (platformState==PlatformState.CLOSING) {
            oldwidth = oldwidth + 0.04f;
            if(oldwidth>=width) {
                platformState=PlatformState.CLOSED;
            }
        }


        fdef.shape = polyshape;
        //polyshape.dispose();

        getB2body().createFixture(fdef).setUserData(object.getName());
        //b2body.setTransform(b2body.getPosition().x,b2body.getPosition().y,0);

    }

    /**
     * Functions that changes the body's dimensions when the platform is opening/closing in the vertical
     */
    public void resizeVer(float delta) {

        getB2body().destroyFixture(getB2body().getFixtureList().get(0));


        Vector2[] newVertices = new Vector2[4]; //It is a box
        newVertices[0] = new Vector2(-width,1.5f);
        newVertices[1] = new Vector2(width,1.5f);
        newVertices[2] = new Vector2(width,-oldheight);
        newVertices[3] = new Vector2(-width,-oldheight);

        polyshape.set(newVertices);
        if(platformState==PlatformState.OPENING) {
            oldheight = oldheight - 0.04f;
            if(oldheight<-1.3f) {
                platformState=PlatformState.OPENED;
            }
        }
        else if (platformState==PlatformState.CLOSING) {
            oldheight = oldheight + 0.04f;
            if(oldheight>=height) {
                platformState=PlatformState.CLOSED;
            }
        }


        fdef.shape = polyshape;
        //polyshape.dispose();

        getB2body().createFixture(fdef).setUserData(object.getName());

    }
}