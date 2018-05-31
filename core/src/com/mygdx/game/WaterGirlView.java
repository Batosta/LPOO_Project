package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A view representing the WaterGirl.
 */
public class WaterGirlView extends CharacterView {

    /**
     * Constructor of the WaterGirl view
     *
     * @param game the game.
     * @param text the texture with all possible positions for the Water Girl.
     */
    public WaterGirlView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     * Chooses the correct texture or animation to be used
     * depending on the moving flags.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {

        sprite.draw(batch);
    }
}
