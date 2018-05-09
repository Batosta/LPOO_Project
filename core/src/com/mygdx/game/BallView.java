package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A view representing a ball
 */
public class BallView extends BodyView{

    /**
     * Constructor of the ball view
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     */
    public BallView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Abstract method creates the sprites for all the balls.
     *
     * @param game the game.
     * @param text the texture with all possible positions for a certain object.
     *
     * @return the sprite representing the Ball.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game, String text) {

        Texture texture = game.getAssetManager().get(text);
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
