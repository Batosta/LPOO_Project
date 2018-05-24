package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen implements Screen {

    private Viewport viewport;
    private Stage stage;

    /**
     * Main game object
     */
    private Game game;

    /**
     * Constructor of the main menu screen
     *
     * @param game the game.
     */
    public MainMenuScreen(Game game){

        this.game = game;
        viewport = new FitViewport(GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER, GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        stage = new Stage(viewport, ((FireBoyWaterGirl) game).getSpriteBatch());

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.YELLOW);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label startGameLabel = new Label("Start", font);
        Label exitLabel = new Label("Exit", font);

        Image backgroundImage = new Image((Texture)((FireBoyWaterGirl) game).getAssetManager().get("mainMenuBackground.jpg"));
        table.add(backgroundImage);
        Image startButton = new Image((Texture)((FireBoyWaterGirl) game).getAssetManager().get("startButtonNormal.png"));
        table.add(startButton);
        Image quitButton = new Image((Texture)((FireBoyWaterGirl) game).getAssetManager().get("quitButtonNormal.png"));
        table.add(quitButton);


        table.debugAll();
        //table.add(startGameLabel).expandX();
        //table.row();
        //table.add(exitLabel).expandX();

        stage.addActor(table);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
