package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game screen. Draws all the views of all objects of the game.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor{

    /**
     * Used to debug the position of the physics fixtures (show lines)
     */
    private static final boolean DEBUG_PHYSICS = false;

    /**
     * Viewport width (meters).
     */
    public static float VIEWPORT_WIDTH = 30;

    /**
     * Viewport height (meters).
     */
    public static float VIEWPORT_HEIGHT = 25;

    /**
     * Each pixel shows "PIXEL_TO_METER" meters.
     */
    public static float PIXEL_TO_METER = 0.03125f;

    /**
     * The game.
     */
    FireBoyWaterGirl fbwg;

    /**
     * The Camera class that operates as a very simple real world camera
     */
    private OrthographicCamera camera;

    /**
     * The game viewport.
     */
    private Viewport viewport;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    /**
     * The music playing.
     */
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("StairwayToHeaven.mp3"));

    /**
     * The view representing the FireBoy
     */
    private FireBoyView fireBoyView;

    /**
     * The view representing the WaterGirl
     */
    private WaterGirlView waterGirlView;

    /**
     * A box body that represents the FireBoy on the world
     */
    private FireBoy2D fireboy2d;

    /**
     * A box body that represents the WaterGirl on the world
     */
    private WaterGirl2D watergirl2d;

    /**
     * The world itself
     */
    private World world;

    /**
     * The renderer used to debug the world
     */
    private Box2DDebugRenderer boxrenderer;

    /**
     * While true the world keeps being renderer
     */
    private boolean rendering = true;

    /**
     * The tiled map editor loader
     */
    TmxMapLoader maploader;

    /**
     * The renderer from an Orthogonal Tiled map
     */
    OrthogonalTiledMapRenderer renderer;

    /**
     * The current level on the game itself
     */
    private Level currentLevel;

    /**
     * The number of the current level on the game itself
     */
    private int  currentLevelID;

    /**
     * An array with all the possible levels on the game
     */
    ArrayList<Level> levels= new ArrayList<Level>();

    /**
     * Float variable that keeps the number of seconds that passed since the level has started
     */
    public float gameTimer = 0;

    /**
     * gamewon tells if the level was won.
     */
    public boolean gamewon = false;

    /**
     * A 2D scene graph containing hierarchies of actors. Stage handles the viewport
     */
    private Stage stage;

    /**
     * A text label, representing the time in this case
     */
    private Label timeLabel;

    /**
     * A boolean that determines if the Menu should be being shown or not
     */
    private boolean showmenu=false;

    /**
     * A boolean that determines if the Resume button (in the Game Menu) can or can not be used
     */
    private boolean resumebutton=false;

    /**
     * The X coordinate of the restart button on the Paused Menu
     */
    private float restartButtoncenterX;

    /**
     * The Y coordinate of the restart button on the Paused Menu
     */
    private float restartButtoncenterY;

    /**
     * The width of the restart button on the Paused Menu
     */
    private float restartButtonWidth;

    /**
     * The height of the restart button on the Paused Menu
     */
    private float restartButtonHeight;

    /**
     * The X coordinate of the menu button on the Paused Menu
     */
    private float menuButtoncenterX;

    /**
     * The Y coordinate of the menu button on the Paused Menu
     */
    private float menuButtoncenterY;

    /**
     * The width of the menu button on the Paused Menu
     */
    private float menuButtonWidth;

    /**
     * The height of the menu button on the Paused Menu
     */
    private float menuButtonHeight;

    /**
     * The X coordinate of the resume button on the Paused Menu
     */
    private float resumeButtoncenterX;

    /**
     * The Y coordinate of the resume button on the Paused Menu
     */
    private float resumeButtoncenterY;

    /**
     * The width of the resume button on the Paused Menu
     */
    private float resumeButtonWidth;

    /**
     * The height of the resume button on the Paused Menu
     */
    private float resumeButtonHeight;

    /**
     * Game table (with game over dialog)
     */
    Table table;

    /**
     * A drawable that shows the Game Over Menu
     */
    Image gameovermenu;

    /**
     * A drawable that shows the Paused Menu
     */
    Image pausemenu;

    /**
     * A boolean that determines if the time should keep counting the seconds played or not
     */
    boolean runtimer=true;

    /**
     * Creates the screen.
     *
     * @param fbwg The game
     */
    public GameScreen(FireBoyWaterGirl fbwg) {

        this.fbwg = fbwg;
        maploader = new TmxMapLoader();

        createCameras();

        stage = new Stage(viewport, (fbwg).getSpriteBatch());

        loadLevels();

        currentLevelID=0;
        setCurrentLevel(currentLevelID);

        createInputProcessor();

        createStage();

    }


    /**
     * Creates the camera that will display the viewport.
     *
     * @return the camera.
     */
    private OrthographicCamera createCameras(){
        setCamera(new OrthographicCamera(VIEWPORT_WIDTH/PIXEL_TO_METER, VIEWPORT_HEIGHT/PIXEL_TO_METER));
        viewport = new FitViewport(GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER, VIEWPORT_HEIGHT/PIXEL_TO_METER);

        getCamera().position.set(getCamera().viewportWidth / 2f, getCamera().viewportHeight / 2f, 0);
        getCamera().update();

        if (DEBUG_PHYSICS) {
            boxrenderer = new Box2DDebugRenderer();
            debugCamera = getCamera().combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return getCamera();
    }

    /**
     * Creates the input processor
     */
    public void createInputProcessor() {

        Gdx.input.setInputProcessor(this);
    }

    /**
     * Renders this screen
     *
     * @param delta time in seconds since last render
     */
    @Override
    public void render(float delta) {

        checkLevelStatus();

        handleInput();
        currentLevel.handleInput(delta);
        currentLevel.renderLevel(delta);

        updateTimeLabel();

        renderer.setView(getCamera());

        //Clears screen
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Game map renderer
        renderer.render();

        // World renderer
        if (rendering)
            world.step(1 / 60f, 6, 2);

        try {
            destroyObjects();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fbwg.getSpriteBatch().enableBlending();
        fbwg.getSpriteBatch().begin();
        drawObjects();
        fbwg.getSpriteBatch().end();

        music.setVolume((float) 0.1);
        music.play();

        if (DEBUG_PHYSICS) {
            debugCamera = getCamera().combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            boxrenderer.render(world, debugCamera);
        }

        incGameTimer(delta);
        stage.draw();
    }

    /**
     * If the R key is pressed, the game is set as Paused
     */
    private void handleInput(){

        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            pauseGame();
        }
    }

    /**
     * Pauses the game, showing the corresponding menu
     */
    private void pauseGame(){
        setMenu(1);
    }

    /**
     * Checks if the conditions for the level to end were completed
     */
    public void getLevelStatus(){
        this.world =  getCurrentLevel().getWorld();
        this.renderer = new OrthogonalTiledMapRenderer(currentLevel.getTiledmap(),1);
    }

    /**
     * Function that checks if the current level has already been won or not
     */
    public void checkLevelStatus() {

        if(currentLevel.isGamewon()){

            currentLevelID++;
            if(currentLevelID<levels.size())
            setCurrentLevel(currentLevelID);
            else endGame();
        }
    }

    /**
     * Restarts the whole game
     */
    public void restartGame(){

        currentLevel.restartGame();
        getLevelStatus();
        rendering=true;
        gameovermenu.setVisible(false);
        pausemenu.setVisible(false);
        gameTimer=0;
        runtimer=true;
    }

    /**
     * Resumes the game again
     */
    public void resumeGame(){

        rendering=true;
        gameovermenu.setVisible(false);
        pausemenu.setVisible(false);
        runtimer=true;
    }

    /**
     * Shows the Main Menu again in case the players lost
     */
    public void endGame(){

        setMenu(0);
    }

    /**
     * Shows the Main/Pause Menu
     *
     * @param nmr if 0, main menu, if 1 pause menu
     */
    private void setMenu(int nmr){

        showmenu=true;
        setButtons();
        rendering=false;
        if(nmr==0) {
            resumebutton = false;
            gameovermenu.setVisible(true);
        }
        else if(nmr==1) {
            resumebutton = true;
            pausemenu.setVisible(true);
        }
        runtimer=false;
    }

    /**
     * Sets the Menu Screen buttons positions
     */
    public void setButtons(){
        restartButtoncenterX=Gdx.graphics.getWidth()/2.3f;
        restartButtoncenterY=Gdx.graphics.getHeight()/2f;
        restartButtonWidth=Gdx.graphics.getWidth()/3f;
        restartButtonHeight=Gdx.graphics.getHeight()/6f;
        menuButtoncenterX=Gdx.graphics.getWidth()/1.7f;
        menuButtoncenterY=Gdx.graphics.getHeight()/2f;
        menuButtonWidth=Gdx.graphics.getWidth()/3f;
        menuButtonHeight=Gdx.graphics.getHeight()/6f;
        resumeButtoncenterX=Gdx.graphics.getWidth()/2f;
        resumeButtoncenterY=Gdx.graphics.getHeight()/1.6f;
        resumeButtonWidth=Gdx.graphics.getWidth()/3f;
        resumeButtonHeight=Gdx.graphics.getHeight()/3f;
    }

    /**
     * Destroys the diamonds view if they have been caught by the corresponding character
     *
     * @throws InterruptedException
     */
    private void destroyObjects() throws InterruptedException {

        for(int i = 0 ; i < currentLevel.getTodestroydiamonds().size ; i++){
            if(currentLevel.getTodestroydiamonds().get(i).getFixtureList().get(0).getUserData() == "bluediamond")
                currentLevel.getBluediamonds().removeLast();
            if(currentLevel.getTodestroydiamonds().get(i).getFixtureList().get(0).getUserData() == "reddiamond")
                currentLevel.getReddiamonds().removeLast();
            world.destroyBody(currentLevel.getTodestroydiamonds().get(i));
            currentLevel.getTodestroydiamonds().removeIndex(i);
        }
    }

    @Override
    public void resize(int width, int height){

        viewport.update(width,height,true);
    }

    /**
     * Draw objects on the screen
     */
    private void drawObjects(){
        currentLevel.getFireBoyView().update(currentLevel.getfireboy2D());
        currentLevel.getFireBoyView().draw(fbwg.getSpriteBatch());

        currentLevel.getWaterGirlView().update(currentLevel.getwatergirl2D());
        currentLevel.getWaterGirlView().draw(fbwg.getSpriteBatch());

        for (HashMap.Entry<String, PlatformView> entry: currentLevel.getODoorsView().entrySet()) {
            entry.getValue().update(currentLevel.getODoors().get(entry.getKey()));
            entry.getValue().draw(fbwg.getSpriteBatch());
        }
    }

    /**
     * Function that loades the level maps
     */
    public void loadLevels(){
        addLevel("level1.tmx");
        addLevel("level2.tmx");
        addLevel("level3.tmx");
    }

    public void addLevel(String mappath){
        levels.add(new Level(fbwg,this,mappath));
    }

    /**
     * Updated the label with the time played in the form "Minutes:Seconds"
     */
    private void updateTimeLabel() {

        int minutes = (int) (gameTimer / 60);
        int seconds = (int) (gameTimer % 60);

        if (minutes < 10) {

            if (seconds < 10)

                timeLabel.setText("0" + Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
            else
                timeLabel.setText("0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));
        } else {

            if (seconds < 10)
                timeLabel.setText(Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
            else
                timeLabel.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
    }

    /**
     * In case the game is not paused, keeps the incrementation of the time
     *
     * @param delta time in seconds since last render
     */
    public void incGameTimer(float delta){
        if(runtimer)
        gameTimer+=delta;
    }

    /**
     * Create the whole Screen Stage
     */
    private void createStage() {

        gameovermenu = new Image((Texture) fbwg.getAssetManager().get("gameovermenu.png"));
        pausemenu = new Image((Texture) fbwg.getAssetManager().get("pausemenu.png"));
        stage = new Stage(viewport, fbwg.getSpriteBatch());

        stage.addActor(gameovermenu);
        stage.addActor(pausemenu);
        gameovermenu.setVisible(false);
        pausemenu.setVisible(false);
        createTable();
    }

    /**
     * Function that creates the time table
     */
    private void createTable(){
        table = new Table();
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.GREEN);

        table.top();
        table.setFillParent(true);

        Label timeLabel = new Label("TIME", font);
        table.add(timeLabel).expandX();
        table.row();

        this.timeLabel = new Label("00:00", font);
        table.add(this.timeLabel);

        stage.addActor(table);
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

        if(showmenu) {

            if (screenX > (restartButtoncenterX - restartButtonWidth / 2) && screenX < (restartButtoncenterX + restartButtonWidth / 2) && screenY < (restartButtoncenterY + restartButtonHeight / 2) && screenY > (restartButtoncenterY - restartButtonHeight / 2)) {
                showmenu=false;
                restartGame();
            }

            if (screenX > (menuButtoncenterX - menuButtonWidth / 2) && screenX < (menuButtoncenterX + menuButtonWidth / 2) && screenY < (menuButtoncenterY + menuButtonHeight / 2) && screenY > (menuButtoncenterY - menuButtonHeight / 2)) {
                showmenu=false;
                ScreenManager.getInstance().showScreen(ScreenState.MENU_SCREEN, fbwg);
            }

            if (resumebutton && screenX > (resumeButtoncenterX - resumeButtonWidth / 2) && screenX < (resumeButtoncenterX + resumeButtonWidth / 2) && screenY < (resumeButtoncenterY + resumeButtonHeight / 2) && screenY > (resumeButtoncenterY - resumeButtonHeight / 2)) {
                showmenu=false;
                resumeGame();
            }

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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     * Fire Boy used in Box2D
     */
    public FireBoy2D getFireboy2d() {

        return fireboy2d;
    }

    /**
     * Redeclares the FireBoy2D
     *
     * @param fireboy2d The new fireBoy2D
     */
    public void setFireboy2d(FireBoy2D fireboy2d) {

        this.fireboy2d = fireboy2d;
    }

    /**
     * The Fire Boy view used to the Fire Boy.
     */
    public FireBoyView getFireBoyView() {

        return fireBoyView;
    }

    /**
     * Gives the FireBoy's object another value
     *
     * @param fireBoyView The new fireBoyView
     */
    public void setFireBoyView(FireBoyView fireBoyView) {

        this.fireBoyView = fireBoyView;
    }

    /**
     * Water Girl used in Box2D
     */
    public WaterGirl2D getWatergirl2d() {

        return watergirl2d;
    }

    /**
     * Redeclares the WaterGirl2D
     *
     * @param watergirl2d The new waterGirl2d
     */
    public void setWatergirl2d(WaterGirl2D watergirl2d) {

        this.watergirl2d = watergirl2d;
    }

    /**
     * The Water Girl view used to draw the Water Girl.
     */
    public WaterGirlView getWaterGirlView() {

        return waterGirlView;
    }

    /**
     * Gives the WaterGirl's object another value
     *
     * @param waterGirlView The new WaterGirlView
     */
    public void setWaterGirlView(WaterGirlView waterGirlView) {

        this.waterGirlView = waterGirlView;
    }

    /**
     * Returns the current level the user is playing
     *
     * @return the level
     */
    public Level getCurrentLevel() {

        return currentLevel;
    }

    /**
     * Sets the current level to another level
     *
     * @param id The int indicator of the level number
     */
    public void setCurrentLevel(int id) {

        gameTimer=0;
        this.currentLevel = levels.get(id);
        getLevelStatus();
    }

    /**
     * The camera used to show the viewport.
     */
    public OrthographicCamera getCamera() {

        return camera;
    }

    /**
     * Sets the camera that shows the game as the new camera
     *
     * @param camera the new camera
     */
    public void setCamera(OrthographicCamera camera) {

        this.camera = camera;
    }
}
