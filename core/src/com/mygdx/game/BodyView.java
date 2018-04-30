package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class BodyView {

    Sprite sprite;

    public BodyView(FireBoyWaterGirl game) {
        sprite = createSprite(game);
    }

    public abstract Sprite createSprite(FireBoyWaterGirl game);
}