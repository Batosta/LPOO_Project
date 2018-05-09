package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a lever
 */
public class LeverView extends BodyView {

    /**
     * constructor of the lever view
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     */
    public LeverView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Abstract method creates the sprites for all the levers.
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     *
     * @return the sprite representing lever view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game, String text) {
        Texture texture = game.getAssetManager().get(text);  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
