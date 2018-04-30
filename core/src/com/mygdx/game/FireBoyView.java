package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FireBoyView extends BodyView {

    /**
     * constructor of the fireboy view
     * @param game the game.
     */
    public FireBoyView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for the Fire Boy.
     *
     * @param game the game.
     *
     * @return the sprite representing Fire Boy view.
     */
    public Sprite createSprite(FireBoyWaterGirl game){
        Texture texture = game.getAssetManager().get("fireboy.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

}

