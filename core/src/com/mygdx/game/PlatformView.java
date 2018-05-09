package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a platform.
 */
public class PlatformView extends BodyView {

    /**
     * constructor of the platform view
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     */
    public PlatformView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Abstract method creates the sprites for all the platforms.
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     *
     * @return the sprite representing platform view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game, String text) {
        Texture texture = game.getAssetManager().get(text);  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
