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

		loadImages();
		//setScreen(new GameScreen(this, game));
		//setScreen(new MainMenuScreen(this));
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
		this.assetManager.load("ball.png", Texture.class);
		this.assetManager.load("purpleButton.png", Texture.class);
		this.assetManager.load("cube.png", Texture.class);
		this.assetManager.load("blueDiamond.png", Texture.class);
		this.assetManager.load("redDiamond.png", Texture.class);
		this.assetManager.load("greenLake.png", Texture.class);
		this.assetManager.load("blueLake.png", Texture.class);
		this.assetManager.load("redLake.png", Texture.class);
		this.assetManager.load("portal.png", Texture.class);
		this.assetManager.load("lever.png", Texture.class);
		this.assetManager.load("wall.png", Texture.class);
		this.assetManager.load("purplePlatform.png", Texture.class);
		this.assetManager.load("horreddoor.png", Texture.class);
		this.assetManager.load("horpurpledoor.png", Texture.class);
		this.assetManager.load("verreddoor.png", Texture.class);
		this.assetManager.load("verpurpledoor.png", Texture.class);

		this.assetManager.load("mainMenuBackground.jpg", Texture.class);
		this.assetManager.load("startButtonNormal.png", Texture.class);
		this.assetManager.load("startButtonPressed.png", Texture.class);
		this.assetManager.load("quitButtonNormal.png", Texture.class);
		this.assetManager.load("quitButtonPressed.png", Texture.class);




		this.assetManager.load("facil.mp3", Music.class);

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
