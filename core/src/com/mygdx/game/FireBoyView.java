package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class FireBoyView extends BodyView{

    public FireBoyView(FireBoyWaterGirl game) {
        super(game);
    }

    /**
     * Abstract method creates the sprites for all the objects.
     *
     * @param game the game.
     *
     * @return the sprite representing the Fire Boy.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {

        Texture texture = game.getAssetManager().get("standFire.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}

