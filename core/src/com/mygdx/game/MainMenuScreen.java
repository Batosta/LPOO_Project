package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Main Menu screen. Draws all the views of all objects of the game.
 */
public class MainMenuScreen extends ScreenAdapter implements InputProcessor {

    /**
     * Manages and determines how world coordinates are mapped to and from the screen.
     */
    private Viewport viewport;

    /**
     * A 2D scene graph containing hierarchies of actors. Stage handles the viewport and distributes input events
     */
    private Stage stage;

    /**
     * Main game object
     */
    private FireBoyWaterGirl game;

    /**
     * Main Menu's background Image
     */
    Image backgroundImage;

    /**
     * Button in the Main Menu Screen that when clicked takes the user to the game itself
     */
    Image startButton;

    /**
     * Button in the Main Menu Screen that when clicked finishes the game
     */
    Image quitButton;

    /**
     * The X coordinate of the buttons that is equal for both
     */
    int buttonX = 155;

    /**
     * The Y coordinate of the buttons that differs on both
     */
    int buttonY;

    /**
     * The width of the buttons that is equal for both
     */
    int width = 335;

    /**
     * The height of the buttons that is equal for both
     */
    int height = 45;

    /**
     * Constructor of the main menu screen
     *
     */
    public MainMenuScreen(FireBoyWaterGirl game){
        this.game=game;

        viewport = new FitViewport(GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER, GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        stage = new Stage(viewport, game.getSpriteBatch());

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        backgroundImage = new Image((Texture)game.getAssetManager().get("mainMenuBackground.jpg"));
        stage.addActor(backgroundImage);

        startButton = new Image((Texture)game.getAssetManager().get("startButtonNormal.png"));
        table.add(startButton);
        table.row();

        quitButton = new Image((Texture)game.getAssetManager().get("quitButtonNormal.png"));
        table.add(quitButton).padTop(50f);

        table.debugAll();
        stage.addActor(table);


        Gdx.input.setInputProcessor(this);
    }

    /**
     * Function that defines the buttonY value according with the received button
     *
     * @param button Which button is begin worked on
     */
    private void decideButtonY(Image button){

        if(button == startButton) {

            buttonY = 180;
        } else{

            buttonY = 255;
        }
    }

    /**
     * Function that checks if the mouse was clicked while above a button
     *
     * @param screenX The X position of the mouse when clicked
     * @param screenY The Y position of the mouse when clicked
     * @param button The button to be checked
     */
    private void touchedButton(int screenX, int screenY, Image button){

        decideButtonY(button);

        if(screenX >= buttonX && screenX <= (buttonX + width)){

            if(screenY >= buttonY && screenY <= (buttonY + height)){

                if(button == startButton) {
                    ScreenManager.getInstance().showScreen(ScreenState.GAME_SCREEN, game);
                }
                else {
                    Gdx.app.exit();
                }
            }
        }
    }

    /**
     * Function that checks if the mouse has passed over the top of a button
     *
     * @param screenX The X position of the mouse when moved
     * @param screenY The Y position of the mouse when moved
     * @param button The button to be checked
     */
    private void colorButtons(int screenX, int screenY, Image button){

        decideButtonY(button);

        if(screenX >= buttonX && screenX <= (buttonX + width) && screenY >= buttonY && screenY <= (buttonY + height)) {

            if (button == startButton) {

                button.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("startButtonPressed.png"))));
            } else {

                button.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("quitButtonPressed.png"))));
            }

        } else {

            if (button == startButton)
                button.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("startButtonNormal.png"))));
            else
                button.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture("quitButtonNormal.png"))));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(100,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(button == Input.Buttons.LEFT) {

            touchedButton(screenX, screenY, startButton);
            touchedButton(screenX, screenY, quitButton);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        colorButtons(screenX, screenY, startButton);
        colorButtons(screenX, screenY, quitButton);

        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
