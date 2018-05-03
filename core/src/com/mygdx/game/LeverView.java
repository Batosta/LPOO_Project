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
     */
    public LeverView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the levers.
     *
     * @param game the game.
     *
     * @return the sprite representing lever view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {
        Texture texture = game.getAssetManager().get("lever.png");  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
