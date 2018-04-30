package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlatformView extends BodyView {


    /**
     * constructor of the platform view
     * @param game the game.
     */
    public PlatformView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the platforms.
     *
     * @param game the game.
     *
     * @return the sprite representing platform view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {
        Texture texture = game.getAssetManager().get("coise.png");  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
