package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.GameScreen.BATCH_CONST;
import static com.mygdx.game.GameScreen.PIXEL_TO_METER;

/**
 * A view representing any body.
 */
public abstract class BodyView {

    Sprite sprite;

    /**
     * Creates a view belonging to a body.
     *
     * @param game the game;
     * @param text the texture with all possible positions for a certain object.
     */
    public BodyView(FireBoyWaterGirl game, String text){

        sprite = createSprite(game, text);
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
     * @param text the texture with all possible positions for a certain object.
     *
     * @return the sprite representing this view.
     */
    public abstract Sprite createSprite(FireBoyWaterGirl game, String text);

    /**
     * View updated using a determined body.
     *
     * @param body Body from World which view will use to be updated.
     */
    public void update(BoxBody body){                       //      NAO SEI COM USAR ISTO

        sprite.setCenter(body.b2body.getPosition().x/PIXEL_TO_METER, body.b2body.getPosition().y/PIXEL_TO_METER);

    }
}