package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

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
     * Abstract method creates the sprites for all the objects.
     *
     * @param game the game.
     *
     * @return the sprite representing this view.
     */
    public abstract Sprite createSprite(FireBoyWaterGirl game);
}