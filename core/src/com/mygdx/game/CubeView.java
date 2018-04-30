package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CubeView extends BodyView {

    /**
     * Constructor of the cube view
     *
     * @param game the game.
     */
    public CubeView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the cubes.
     *
     * @param game the game.
     *
     * @return the sprite representing the Cube.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {

        Texture texture = game.getAssetManager().get("cube.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
