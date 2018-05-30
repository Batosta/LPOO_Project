package com.mygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

public class LakeBody {

        public World world;
        public Body b2body;
        protected BodyDef bdef;
        protected FixtureDef fdef;
        PolygonShape polyshape;
        int color;

        public LakeBody(World world, MapObject object, int color){     //x , y meters ??           color = 0 if red, 1 if blue, 2 if green

            this.world = world;
            this.color = color;
            defineLake(object);
        }

        public void defineLake(MapObject object){

            polyshape = new PolygonShape();
            bdef = new BodyDef();
            fdef = new FixtureDef();

            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2) * GameScreen.PIXEL_TO_METER);

            b2body = world.createBody(bdef);

            polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
            fdef.shape = polyshape;

            if(this.color == 0)
                b2body.createFixture(fdef).setUserData("redlake");
            else if(this.color == 1)
                b2body.createFixture(fdef).setUserData("bluelake");
            else
                b2body.createFixture(fdef).setUserData("greenlake");
        }
    }
