package com.mygdx.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A World Contact Listener that checks colisions
 */
public class WorldContactListener implements ContactListener {

    GameScreen gamescreen;

    public WorldContactListener(GameScreen gamescreen) {       //game model?

        this.gamescreen = gamescreen;
    }

    /**
     * The contact between 2 objects
     */
    Contact contact;

    /**
     * Fixture of the first object
     */
    Fixture fixtureA;

    /**
     * Fixture of the second object
     */
    Fixture fixtureB;

    /**
     * Checks the contact between every pair of objects that needs to be checked
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {

        this.contact = contact;
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();

        contactDiamonds();
        contactDoors();
        contactButtons();
        contactRamps();
        contactLake();
    }

    /**
     * Checks both characters contact with all the diamonds on the game
     */
    private void contactDiamonds(){

        if (fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "reddiamond") {
            Body bodyB = fixtureB.getBody();
            gamescreen.todestroydiamonds.addLast(bodyB);
            TiledMapTileLayer layer = (TiledMapTileLayer) gamescreen.tiledmap.getLayers().get(3);
            layer.getCell((int) (bodyB.getPosition().x / GameScreen.PIXEL_TO_METER / 32), (int) (bodyB.getPosition().y / GameScreen.PIXEL_TO_METER) / 32).setTile(null);

        }

        if (fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "bluediamond") {
            Body bodyB = fixtureB.getBody();
            gamescreen.todestroydiamonds.addLast(bodyB);
            TiledMapTileLayer layer = (TiledMapTileLayer) gamescreen.tiledmap.getLayers().get(3);
            layer.getCell((int) (bodyB.getPosition().x / GameScreen.PIXEL_TO_METER / 32), (int) (bodyB.getPosition().y / GameScreen.PIXEL_TO_METER) / 32).setTile(null);
        }
    }

    /**
     * Checks both characters contact with all the buttons on the game
     */
    private void contactDoors(){

        if (fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "reddoor") {
            gamescreen.reddoorbody.setDooropened(true);
            gamescreen.checkLevelStatus();
        }

        if (fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "bluedoor") {
            gamescreen.bluedoorbody.setDooropened(true);
            gamescreen.checkLevelStatus();
        }
    }

    /**
     * Checks both characters contact with all the buttons on the game
     */
    private void contactButtons(){

        if (fixtureA.getUserData() == "fireboy" && (fixtureB.getUserData() instanceof ButtonBody)) {
            gamescreen.ODoors.get(((ButtonBody) fixtureB.getUserData()).getMapObject().getName()).setButtonpressed(true);
        }

        if (fixtureA.getUserData() == "watergirl" && (fixtureB.getUserData() instanceof ButtonBody)) {
            gamescreen.ODoors.get(((ButtonBody) fixtureB.getUserData()).getMapObject().getName()).setButtonpressed(true);
        }
    }

    /**
     * Checks both characters contact with all the ramps on the game
     */
    private void contactRamps(){

        //  allows fire boy to jump on slopes
        if (fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "rampa") {
            gamescreen.getFireboy2d().canjump = true;
        }

        //  allows water girl to jump on slopes
        if (fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "rampa") {
            gamescreen.getWatergirl2d().canjump = true;
        }
    }

    /**
     * Checks both characters contact with all the ramps on the game
     */
    private void contactLake(){

        if(fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "redlake"){
            gamescreen.getFireboy2d().setAlive(false);
            gamescreen.endGame();
        }

        if(fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "bluelake"){
            gamescreen.getFireboy2d().setAlive(false);
            gamescreen.endGame();
        }

        if(fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "greenlake"){
            gamescreen.getWatergirl2d().setAlive(false);
            gamescreen.endGame();
        }

        if(fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "greenlake"){
            gamescreen.getFireboy2d().setAlive(false);
            gamescreen.endGame();
        }
    }

    /**
     * Checks the contacts needed for the game to finish
     *
     * @param contact The contact between 2 objects
     */
    @Override
    public void endContact(Contact contact) {

        this.contact = contact;
        fixtureA = contact.getFixtureA();
        fixtureB = contact.getFixtureB();


        if (fixtureA.getUserData() == "fireboy" && fixtureB.getUserData() == "reddoor") {
            gamescreen.reddoorbody.setDooropened(false);
        }

        if (fixtureA.getUserData() == "watergirl" && fixtureB.getUserData() == "bluedoor") {
            gamescreen.bluedoorbody.setDooropened(false);
        }


        if (fixtureA.getUserData() == "fireboy" && (fixtureB.getUserData() instanceof ButtonBody)) {
            System.out.println("fire dentro button");
            gamescreen.ODoors.get(((ButtonBody) fixtureB.getUserData()).getMapObject().getName()).setButtonpressed(false);
        }

        if (fixtureA.getUserData() == "watergirl" && (fixtureB.getUserData() instanceof ButtonBody)) {
            System.out.println("water dentro button");
            gamescreen.ODoors.get(((ButtonBody) fixtureB.getUserData()).getMapObject().getName()).setButtonpressed(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
