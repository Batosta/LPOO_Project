package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a lake.
 */
public class LakeView extends BodyView {

    /**
     * Constructor of the lake view
     * @param game the game.
     */
    public LakeView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the lakes.
     *
     * @param game the game.
     *
     * @return the sprite representing lake view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {
        Texture texture = game.getAssetManager().get("lake.png");  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}