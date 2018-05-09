package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a door.
 */
public class DoorView extends BodyView {

    /**
     * Constructor of the door view.
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     */
    public DoorView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Abstract method creates the sprites for all the doors.
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     *
     * @return the sprite representing door view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game, String text) {
        Texture texture = game.getAssetManager().get(text);  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
