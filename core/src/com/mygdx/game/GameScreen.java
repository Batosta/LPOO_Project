package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
/**
 * Game screen. Draws all the views of all objects of the game.
 */
public class GameScreen extends ScreenAdapter{

    /**
     * The game.
     */
    FireBoyWaterGirl fbwg;

    /**
     * The game data.
     */
    GameModel model;

    /**
     * Creates the screen.
     *
     * @param fbwg The game
     * @param model The model to be drawn
     */
    public GameScreen(FireBoyWaterGirl fbwg, GameModel model) {
        this.fbwg = fbwg;
        this.model = model;

        loadImages();
    }

    /**
     * loads the images used in this screen
     */
    public void loadImages(){

        this.fbwg.getAssetManager().load("coise.jpg0", Texture.class);
        this.fbwg.getAssetManager().finishLoading();
    }

    /**
     * renders this screen
     *
     * @param delta time in seconds since last render
     */
    @Override
    public void render(float delta) {
        //camera updates se quiseremos usar camara.

        //clear screen
        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);      // valores certos?

        fbwg.getSpriteBatch().begin();
        //drawBackground
        //drawObjects
        fbwg.getSpriteBatch().end();
    }

    /**
     * Draw objects on the screen
     */
    private void drawObjects(){

    }
}