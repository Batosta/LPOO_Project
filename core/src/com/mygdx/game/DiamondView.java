package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a diamond
 */
public class DiamondView extends BodyView{

    /**
     * Constructor of the diamond view
     *
     * @param game the game.
     */
    public DiamondView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the diamonds.
     *
     * @param game the game.
     *
     * @return the sprite representing the Diamond.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {

        Texture texture = game.getAssetManager().get("redDiamond.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}

