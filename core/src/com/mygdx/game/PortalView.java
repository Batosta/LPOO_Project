package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a portal.
 */
public class PortalView extends BodyView {

    /**
     * constructor of the portal view
     * @param game the game.
     */
    public PortalView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the portals.
     *
     * @param game the game.
     *
     * @return the sprite representing platform view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {
        Texture texture = game.getAssetManager().get("portal.png");  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

}
