package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a ball
 */
public class ButtonView extends BodyView{

    /**
     * Constructor of the button view
     *
     * @param game the game.
     */
    public ButtonView(FireBoyWaterGirl game) {

        super(game);
    }

    /**
     * Abstract method creates the sprites for all the buttons.
     *
     * @param game the game.
     *
     * @return the sprite representing the button.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {

        Texture texture = game.getAssetManager().get("purpleButton.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
