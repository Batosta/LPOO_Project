package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener {

        @Override
        public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if(fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "diamond"){
            System.out.println("CONTACTO FIREBOY DIAMOND");

        }
    }

        @Override
        public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
    }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {
    }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
