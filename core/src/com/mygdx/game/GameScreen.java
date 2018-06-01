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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.maps.tiled.TiledMap;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Game screen. Draws all the views of all objects of the game.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor{


    public static float BATCH_CONST = 1.5f;

    private int gameLevel = 0;

    /**
     * Used to debug the position of the physics fixtures (show lines)
     */
    private static final boolean DEBUG_PHYSICS = true;

    /**
     * Map width (meters).
     */
    public static int MAP_WIDTH = 30;

    /**
     * Map height (meters).
     */
    //public static int MAP_HEIGHT = 25;

    /*
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
     * The camera used to show the viewport.
     */
    private OrthographicCamera camera;

    /**
     * The game viewport.
     */
    private Viewport viewport;

    /**
     * The game viewport2.
     */
    private Viewport viewport2;

    /**
     * The transformation matrix used to transform meters into
     * pixels in order to show fixtures in their correct places.
     */
    private Matrix4 debugCamera;

    /**
     * The music playing.
     */
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("facil.mp3"));

    private FireBoyView fireBoyView;

    /**
     * HashMap with the view of the horizontal and vertical colored doors to be opened by the colored buttons
     */
    HashMap<String, PlatformView> platformsView;

    private WaterGirlView waterGirlView;

    private FireBoy2D fireboy2d;

    private WaterGirl2D watergirl2d;

    /**
     * Blue door body used in Box2D. Is only a sensor
     */
    public DoorBody bluedoorbody;

    /**
     * Red door body used in Box2D. Is only a sensor
     */
    public DoorBody reddoorbody;

//    /**
//     * Queue with the red diamonds to be caught by Fire Boy
//     */
//    Queue<DiamondBody> reddiamonds;
//
//    /**
//     * Queue with the blue diamonds to be caught by Water Girl
//     */
//    Queue<DiamondBody> bluediamonds;
//
//    /**
//     * Queue with the red lakes that Water Girl can not touch
//     */
//    Queue<LakeBody> redlakes;
//
//    /**
//     * Queue with the blue lakes that Fire Boy can not touch
//     */
//    Queue<LakeBody> bluelakes;
//
//    /**
//     * Queue with the green lakes that neither Water Girl and Fire Boy can not touch
//     */
//    Queue<LakeBody> greenlakes;
//
//    /**
//     * HashMap with the horizontal and vertical colored doors to be opened by the colored buttons
//     */
//    HashMap<String, ODoorBody> ODoors;



//    /**
//     * hash map with the colored buttons to open colored doors
//     */
//    HashMap<String, ButtonBody> buttons;

    private World world;

    private Box2DDebugRenderer boxrenderer;

    private boolean rendering = true;

    TmxMapLoader maploader;

    TiledMap tiledmap;

    OrthogonalTiledMapRenderer renderer;

    private Level currentLevel;

    private int  currentLevelID;

    ArrayList<Level> levels= new ArrayList<Level>();

    /**
     * Float variable that keeps the number of seconds that passed since the level has started
     */
    public float gameTimer = 0;

    /**
     * gamewon tells if the level was won.
     */
    public boolean gamewon = false;

    private Stage stage;
    private Label timeLabel;

    private float restartButtoncenterX;

    private float menuButtoncenterX;

    private float restartButtoncenterY;

    private float menuButtoncenterY;

    private float restartButtonWidth;

    private float menuButtonwidth;

    private float restartButtonHeight;

    private float menuButtonHeight;

    /**
     * flag used in game over dialog
     */
    public boolean gameover_flag = false;

    //      Game table (with game over dialog)

    Table table;

    Image background;

    BodyDef bdef;

    PolygonShape polyshape;

    FixtureDef fdef;

    Body body;


    /**
     * Creates the screen.
     *
     * @param fbwg The game
     */
    public GameScreen(FireBoyWaterGirl fbwg) {

        this.fbwg = fbwg;
        maploader = new TmxMapLoader();
        //tiledmap = maploader.load("provmap.tmx");
        //renderer = new OrthogonalTiledMapRenderer(tiledmap,1);

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
        camera = new OrthographicCamera(VIEWPORT_WIDTH/PIXEL_TO_METER, VIEWPORT_HEIGHT/PIXEL_TO_METER);
        viewport = new FitViewport(GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER, VIEWPORT_HEIGHT/PIXEL_TO_METER);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            boxrenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

//    public void createWorld(){
//        if (world != null) {
//            world.dispose();
//        }
//        world = new World(new Vector2(0, -15f), true);
//        rendering=true;
//        //createObjects();
//        createViews();
//        world.setContactListener(new WorldContactListener(this));
//        if (DEBUG_PHYSICS) {
//            boxrenderer = new Box2DDebugRenderer();
//            debugCamera = camera.combined.cpy();
//            debugCamera.scl(1 / PIXEL_TO_METER);
//        }
//    }

//    public void createViews(){
//        setFireBoyView(new FireBoyView(fbwg, "fire.png"));
//        setWaterGirlView(new WaterGirlView(fbwg, "water.png"));
//    }

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
        currentLevel.handleInput(delta);
        currentLevel.renderLevel(delta);

        handleInput(delta);
        updateTimeLabel();
        updateObjects(delta);
        renderer.setView(camera);

        //Clears screen
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Game map renderer
        renderer.render();

        // World renderer
        if (rendering)
            world.step(1 / 60f, 6, 2);

        if(rendering) {
            world.step(1 / 60f, 6, 2);
        }

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
        //music.play();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            boxrenderer.render(world, debugCamera);
        }

        //showGameTime(delta);

        stage.draw();
    }

    /**
     * Checks if the conditions for the level to end were completed
     */
    public void getLevelStatus(){
        this.world =  getCurrentLevel().getWorld();
        this.renderer = new OrthogonalTiledMapRenderer(currentLevel.getTiledmap(),1);
    }

    public void checkLevelStatus() {
        if(currentLevel.isGamewon()){
            //  TODO dialog com opçoes?
            currentLevelID++;
            setCurrentLevel(currentLevelID);
        }
    }

    public void restartGame(){
        currentLevel.restartGame();
        getLevelStatus();
        rendering=true;
        background.setVisible(false);
    }

    public void endGame(){
            rendering=false;
            background.setVisible(true);
    }

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
        currentLevel.getFireBoyView().update(currentLevel.getFireboy2D());
        currentLevel.getFireBoyView().draw(fbwg.getSpriteBatch());

        currentLevel.getWaterGirlView().update(currentLevel.getWatergirl2D());
        currentLevel.getWaterGirlView().draw(fbwg.getSpriteBatch());

        for (HashMap.Entry<String, ODoorView> entry: currentLevel.getODoorsView().entrySet()) {
            entry.getValue().update(currentLevel.getODoors().get(entry.getKey()));
            entry.getValue().draw(fbwg.getSpriteBatch());
        }
    }
//
//    private void updateObjects(float delta) {
//
//        fireboy2d.update(delta);
//        watergirl2d.update(delta);
//        bluedoorbody.update(delta);
//        currentLevel.getODoors().get("purple").update(delta);
//        currentLevel.getODoors().get("red").update(delta);
//    }

    /**
     * Function that loades the level maps
     */
    public void loadLevels(){
        levels.add(new Level(fbwg,this,"provmap.tmx"));
        levels.add(new Level(fbwg,this,"level2.tmx"));
    }

    public void setMap(TiledMap tiledmap){
        this.tiledmap=tiledmap;
    }
    /**
     * Function that takes care of showing the time on the screen
     *
     * @param delta time in seconds since last render
     */
    private void showGameTime(float delta) {

        gameTimer += delta;

        //table.set

        //stage.addActor(table);


        stage.draw();
    }

    /**
     * Function that handles both FireBoy and WaterGirl's user inputs
     *
     * @param delta time in seconds since last render
     */
    public void handleInput(float delta) {

        handleFireBoyInput();
        handleWaterGirlInput();
    }

    /**
     * Function that handles FireBoy's user inputs
     */
    private void handleFireBoyInput(){

        fireBoyUp();
        fireBoyRight();
        fireBoyLeft();
        fireBoyNoDir();
    }

    /**
     * Function that handles the jump of the FireBoy
     */
    private void fireBoyUp(){

                                                                //Compor esta funcao para ficar mais simples
//    public void handleInput(float delta){
//        //              FireBoy inputx.input.isK
//        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
//            System.out.println(" input: " + getFireboy2d().jumpstate);
//            if(getFireboy2d().jumpstate == BoxCharacter.Jump.STOP)
//            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0,8.3f), getFireboy2d().getB2body().getWorldCenter(),true);
//            else if (getFireboy2d().canjump) {
//                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
//                getFireboy2d().canjump=false;
//            }
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getFireboy2d().getB2body().getLinearVelocity().x <= 6){
//            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0.5f,0), getFireboy2d().getB2body().getWorldCenter(),true);
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && getFireboy2d().getB2body().getLinearVelocity().x >= -6) {
//            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
//        }
//                // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
//        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            if(getFireboy2d().getB2body().getLinearVelocity().x>0) {
//                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
//                if(getFireboy2d().getB2body().getLinearVelocity().x<0)
//                    getFireboy2d().getB2body().setLinearVelocity(0, getFireboy2d().getB2body().getLinearVelocity().y);
//            }
//            if(getFireboy2d().getB2body().getLinearVelocity().x<0) {
//                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0.4f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
//                if (getFireboy2d().getB2body().getLinearVelocity().x > 0)
//                    getFireboy2d().getB2body().setLinearVelocity(0, getFireboy2d().getB2body().getLinearVelocity().y);
//            }
//        }
//        //              WaterGirl input
//        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
//            if(getWatergirl2d().jumpstate == BoxCharacter.Jump.STOP)
//            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0,8.3f), getWatergirl2d().getB2body().getWorldCenter(),true);
//            else if (getWatergirl2d().canjump) {
//                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
//                getWatergirl2d().canjump=false;
//            }
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.D) && getWatergirl2d().getB2body().getLinearVelocity().x <= 6){
//            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0.5f,0), getWatergirl2d().getB2body().getWorldCenter(),true);
//        }
//        if(Gdx.input.isKeyPressed(Input.Keys.A) && getWatergirl2d().getB2body().getLinearVelocity().x >= -6) {
//            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
//        }
//        // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
//        if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
//            if(getWatergirl2d().getB2body().getLinearVelocity().x>0) {
//                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
//                if (getWatergirl2d().getB2body().getLinearVelocity().x < 0)
//                    getWatergirl2d().getB2body().setLinearVelocity(0, getWatergirl2d().getB2body().getLinearVelocity().y);
//            }
//            if(getWatergirl2d().getB2body().getLinearVelocity().x<0) {
//                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0.4f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
//                if (getWatergirl2d().getB2body().getLinearVelocity().x > 0)
//                    getWatergirl2d().getB2body().setLinearVelocity(0, getWatergirl2d().getB2body().getLinearVelocity().y);
//            }
//        }
//    }

        //              FireBoy input
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(getFireboy2d().jumpstate == BoxCharacter.Jump.STOP)
            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0,8.3f), getFireboy2d().getB2body().getWorldCenter(),true);
            else if (getFireboy2d().canjump) {
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
                getFireboy2d().canjump=false;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getFireboy2d().getB2body().getLinearVelocity().x <= 6){
            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0.5f,0), getFireboy2d().getB2body().getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && getFireboy2d().getB2body().getLinearVelocity().x >= -6) {
            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
        }
                // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(getFireboy2d().getB2body().getLinearVelocity().x>0) {
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
                if(getFireboy2d().getB2body().getLinearVelocity().x<0)
                    getFireboy2d().getB2body().setLinearVelocity(0, getFireboy2d().getB2body().getLinearVelocity().y);
            }
            if(getFireboy2d().getB2body().getLinearVelocity().x<0) {
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0.4f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
                if (getFireboy2d().getB2body().getLinearVelocity().x > 0)
                    getFireboy2d().getB2body().setLinearVelocity(0, getFireboy2d().getB2body().getLinearVelocity().y);
            }
        }
        //              WaterGirl input
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            if(getWatergirl2d().jumpstate == BoxCharacter.Jump.STOP)
            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0,8.3f), getWatergirl2d().getB2body().getWorldCenter(),true);
            else if (getWatergirl2d().canjump) {
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
                getWatergirl2d().canjump=false;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && getWatergirl2d().getB2body().getLinearVelocity().x <= 6){
            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0.5f,0), getWatergirl2d().getB2body().getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && getWatergirl2d().getB2body().getLinearVelocity().x >= -6) {
            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
        }
        // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
        if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(getWatergirl2d().getB2body().getLinearVelocity().x>0) {
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
                if (getWatergirl2d().getB2body().getLinearVelocity().x < 0)
                    getWatergirl2d().getB2body().setLinearVelocity(0, getWatergirl2d().getB2body().getLinearVelocity().y);
            }
            if(getWatergirl2d().getB2body().getLinearVelocity().x<0) {
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0.4f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
                if (getWatergirl2d().getB2body().getLinearVelocity().x > 0)
                    getWatergirl2d().getB2body().setLinearVelocity(0, getWatergirl2d().getB2body().getLinearVelocity().y);
            }
        }
    }

    public void createObjects(){

        todestroydiamonds = new Queue<Body>();
        setFireboy2d(new FireBoy2D(world,50f*PIXEL_TO_METER,100f*PIXEL_TO_METER));
        setWatergirl2d(new WaterGirl2D(world,50f*PIXEL_TO_METER,200f*PIXEL_TO_METER));

        BodyDef bdef = new BodyDef();
        PolygonShape polyshape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //Green Lakes
        greenlakes = new Queue<LakeBody>();
        for (MapObject object : tiledmap.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            greenlakes.addFirst(new LakeBody(world,object,2));      //2 if green
        }

        //Blue Lakes
        bluelakes = new Queue<LakeBody>();
        for (MapObject object : tiledmap.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            bluelakes.addFirst(new LakeBody(world,object,1));      //1 if blue
        }

        //Red Lakes
        redlakes = new Queue<LakeBody>();
        for (MapObject object : tiledmap.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            redlakes.addFirst(new LakeBody(world,object,0));      //2 if green
        }

        if(this.fireboy2d!=null){
            this.fireboy2d=null;
        }
        this.fireboy2d = fireboy2d;
    }

        for (MapObject object : tiledmap.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            ODoors.put(object.getName(),new ODoorBody(world,object,1));
            if(object.getName().equals("purple"))
                ODoorsView.put(object.getName(), new ODoorView(fbwg, "verpurpledoor.png"));
            if(object.getName().equals("red")) {
                ODoorsView.put(object.getName(), new ODoorView(fbwg, "verreddoor.png"));
            }
        }

        buttons = new HashMap<String, ButtonBody>();
        for (MapObject object : tiledmap.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            buttons.put(object.getName(),new ButtonBody(world,object));
        }


    private void updateTimeLabel() {
        int minutes = (int) (gameTimer / 60);
            else
        int seconds = (int) (gameTimer % 60);
        if (minutes < 10) {
            if (seconds < 10)

            else
                timeLabel.setText("0" + Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
        } else {
                timeLabel.setText("0" + Integer.toString(minutes) + ":" + Integer.toString(seconds));
            if (seconds < 10)

                timeLabel.setText(Integer.toString(minutes) + ":" + "0" + Integer.toString(seconds));
                timeLabel.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
        }
     */
     * Updated the label with the time played in the form "Minutes:Seconds"
    /**
    }

    /**
     * Create the whole Screen Stage
     */
    private void createStage() {

        background = new Image((Texture) fbwg.getAssetManager().get("gameover_dialog.png"));
        stage = new Stage(viewport, fbwg.getSpriteBatch());

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
        table.debugAll();

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

        if(screenX > (restartButtoncenterX-restartButtonWidth/2) && screenX < (restartButtoncenterX+restartButtonWidth/2)  && screenY < (restartButtoncenterY+restartButtonHeight/2) && screenY > (restartButtoncenterY-restartButtonHeight/2)){
            restartGame();
        }

        if(screenX > (menuButtoncenterX-menuButtonwidth/2) && screenX < (menuButtoncenterX+menuButtonwidth/2)  && screenY < (menuButtoncenterY+menuButtonHeight/2) && screenY > (menuButtoncenterY-menuButtonHeight/2)){
            ScreenManager.getInstance().showScreen(ScreenState.MENU_SCREEN,fbwg);
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
    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int id) {
        this.currentLevel = levels.get(id);
        getLevelStatus();
    }
}
