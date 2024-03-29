package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * The game main class.
 */
public class FireBoyWaterGirl extends Game {

	private SpriteBatch batch;

	private AssetManager assetManager;

	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		beginGame();
	}

	/**
	 * Disposes all assets.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Begins the game.
	 */
	private void beginGame(){

		loadImages();

		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenState.MENU_SCREEN,this);
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
	 * Loads the images used in this screen.
	 */
	public void loadImages(){

		this.assetManager.load("fire.png", Texture.class);
		this.assetManager.load("water.png", Texture.class);
		this.assetManager.load("greenLake.png", Texture.class);
		this.assetManager.load("blueLake.png", Texture.class);
		this.assetManager.load("redLake.png", Texture.class);
		this.assetManager.load("horreddoor.png", Texture.class);
		this.assetManager.load("horpurpledoor.png", Texture.class);
		this.assetManager.load("horgreendoor.png", Texture.class);
		this.assetManager.load("verreddoor.png", Texture.class);
		this.assetManager.load("verpurpledoor.png", Texture.class);
		this.assetManager.load("vergreendoor.png", Texture.class);

		this.assetManager.load("mainMenuBackground.jpg", Texture.class);
		this.assetManager.load("startButtonNormal.png", Texture.class);
		this.assetManager.load("startButtonPressed.png", Texture.class);
		this.assetManager.load("quitButtonNormal.png", Texture.class);
		this.assetManager.load("quitButtonPressed.png", Texture.class);

        this.assetManager.load("pausemenu.png", Texture.class);
		this.assetManager.load("gameovermenu.png", Texture.class);


		this.assetManager.load("StairwayToHeaven.mp3", Music.class);

		this.assetManager.finishLoading();
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
