package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing the WaterGirl.
 */
public class WaterGirlView extends BodyView {

    /**
     * Constructor of the WaterGirl view
     *
     * @param game the game.
     */
    public WaterGirlView(FireBoyWaterGirl game) {

        super(game);
    }

    /**
     * Abstract method creates the sprites for the WaterGirl.
     *
     * @param game the game.
     *
     * @return the sprite representing the WaterGirl.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {

        Texture texture = game.getAssetManager().get("water.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
