package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a wall
 */
public class WallView extends BodyView{

    /**
     * Constructor of the wall view
     *
     * @param game the game.
     */
    public WallView(FireBoyWaterGirl game) {
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

        Texture texture = game.getAssetManager().get("wall.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
