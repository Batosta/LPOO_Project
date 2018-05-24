package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

public class WorldContactListener implements ContactListener {

    GameScreen gamescreen;

    public WorldContactListener(GameScreen gamescreen){       //game model?
        this.gamescreen=gamescreen;
    }

        @Override
        public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

            if(fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "reddiamond") {
                Body bodyB = fixtureB.getBody();
                TiledMapTileLayer layer = (TiledMapTileLayer) gamescreen.tiledmap.getLayers().get(3);
                layer.getCell((int) (bodyB.getPosition().x / GameScreen.PIXEL_TO_METER / 32), (int) (bodyB.getPosition().y / GameScreen.PIXEL_TO_METER) / 32).setTile(null);
            }

            if(fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "bluediamond") {
                Body bodyB = fixtureB.getBody();
                TiledMapTileLayer layer = (TiledMapTileLayer) gamescreen.tiledmap.getLayers().get(3);
                layer.getCell((int) (bodyB.getPosition().x / GameScreen.PIXEL_TO_METER / 32), (int) (bodyB.getPosition().y / GameScreen.PIXEL_TO_METER) / 32).setTile(null);
            }

    }

        @Override
        public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
    }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
    }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}