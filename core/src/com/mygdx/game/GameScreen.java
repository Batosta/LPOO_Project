package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen extends ScreenAdapter{

    FireBoyWaterGirl fbwg;
    GameModel model;

    public GameScreen(FireBoyWaterGirl fbwg, GameModel model) {

        this.fbwg = fbwg;
        this.model = model;

        loadImages();
    }

    public void loadImages(){

        this.fbwg.getAssetManager().load("coise.jpg0", Texture.class);
    }
}
