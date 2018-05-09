package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * The game main class.
 */
public class FireBoyWaterGirl extends Game {

	private SpriteBatch batch;
	private AssetManager assetManager;
	
	@Override
	public void create() {
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

		GameModel game = new GameModel(15, 15, 5, 10);

		setScreen(new GameScreen(this, game));
	}

	/**
	 * Returns assetManager for all textures/sounds.
	 *
	 * @return assetManager.
	 */
	public AssetManager getAssetManager(){

		return assetManager;
	}

	/**
	 * Returns the batch used for drawing.
	 *
	 * @return batch.
	 */
	public SpriteBatch getSpriteBatch(){

		return batch;
	}
}
