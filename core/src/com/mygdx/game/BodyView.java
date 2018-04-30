package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class BodyView {

    Sprite sprite;

     /**
      * Abstract method creates the sprites for all the objects.
      *
      * @param game the game.
      *
      * @return the sprite representing this view.
      */

    public BodyView(FireBoyWaterGirl game){
        sprite = createSprite(game);
    }

    public abstract Sprite createSprite(FireBoyWaterGirl game);
}