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
     * @param text the texture with all possible positions for a certain object.
     */
    public DiamondView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Abstract method creates the sprites for all the diamonds.
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     *
     * @return the sprite representing the Diamond.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game, String text) {

        Texture texture = game.getAssetManager().get(text);
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}

