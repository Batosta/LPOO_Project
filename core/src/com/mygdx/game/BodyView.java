package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.GameScreen.PIXEL_TO_METER;

/**
 * A view representing any body.
 */
public abstract class BodyView {

    Sprite sprite;

    /**
     * Creates a view belonging to a body.
     *
     * @param game the game
     */
    public BodyView(FireBoyWaterGirl game){

        sprite = createSprite(game);
    }

    /**
     * Draws a sprite using the spriteBatch
     *
     * @param spriteBatch which is used for drawing.
     */
    public void draw(SpriteBatch spriteBatch){

        sprite.draw(spriteBatch);
    }

    /**
     * Abstract method creates the sprites for all the objects.
     *
     * @param game the game.
     *
     * @return the sprite representing this view.
     */
    public abstract Sprite createSprite(FireBoyWaterGirl game);

    /**
     * View updated using a determined body.
     *
     * @param body Body which view will use to be updated.
     */
    public void update(Body body){

        sprite.setCenter(body.getX() / PIXEL_TO_METER, body.getY() / PIXEL_TO_METER);
    }
}