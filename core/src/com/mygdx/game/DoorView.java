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
     */
    public DoorView(FireBoyWaterGirl game) {

        super(game);
    }

    /**
     * Abstract method creates the sprites for all the doors.
     *
     * @param game the game.
     *
     * @return the sprite representing door view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {
        Texture texture = game.getAssetManager().get("redDoor.png");  //TODO imagem certa. implementar para diferentes imagens
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
