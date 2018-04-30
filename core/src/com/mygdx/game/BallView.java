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
     */
    public BallView(FireBoyWaterGirl game) {

        super(game);
    }

    /**
     * Abstract method creates the sprites for all the balls.
     *
     * @param game the game.
     *
     * @return the sprite representing the Ball.
     * */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game) {

        Texture texture = game.getAssetManager().get("ball.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }
}
