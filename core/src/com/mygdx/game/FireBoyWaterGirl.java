package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FireBoyWaterGirl extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		beginGame();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Begins the game.
	 */
	private void beginGame(){

		GameUnique game = new GameUnique(0, 0, 0, 0);

		//setScreen(new GameView(this, model));
	}
}
