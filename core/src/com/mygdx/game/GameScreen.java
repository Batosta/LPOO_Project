package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Game screen. Draws all the views of all objects of the game.
 */
public class GameScreen extends ScreenAdapter{

    /**
     * Map width (meters).
     */
    public static int MAP_WIDTH = 100;

    /**
     * Map height (meters).
     */
    public static int MAP_HEIGHT = 100;

    /**
     * Viewport width (meters).
     * Height is set using the screen ratio.
     */
    public static float VIEWPORT_WIDTH = 50;

    /**
     * Each pixel shows "PIXEL_TO_METER" meters.
     */
    public static float PIXEL_TO_METER = 0.05f;

    /**
     * The game.
     */
    FireBoyWaterGirl fbwg;

    /**
     * The game data.
     */
    GameModel model;

    /**
     * The camera used to show the viewport.
     */
    private OrthographicCamera camera;

    /**
     * A ball view used to draw balls.
     */
    private BallView ballView;

    /**
     * A button view used to draw buttons.
     */
    private ButtonView buttonView;

    /**
     * A cube view used to draw cubes.
     */
    private CubeView cubeView;

    /**
     * A diamond view used to draw diamonds.
     */
    private DiamondView diamondView;

    /**
     * A door view used to draw doors.
     */
    private DoorView doorView;

    /**
     * The Fire Boy view used to the Fire Boy.
     */
    private FireBoyView fireBoyView;

    /**
     * A lake view used to draw lakes.
     */
    private LakeView lakeView;

    /**
     * A lever view used to draw levers.
     */
    private LeverView leverView;

    /**
     * A platform view used to draw platforms.
     */
    private PlatformView platformView;

    /**
     * A portal view used to draw portals.
     */
    private PortalView portalView;

    /**
     * A wall view used to draw walls.
     */
    private WallView wallView;

    /**
     * The Water Girl view used to draw the Water Girl.
     */
    private WaterGirlView waterGirlView;

    TmxMapLoader maploader;

    TiledMap tiledmap;

    OrthogonalTiledMapRenderer renderer;

    /**
     * Creates the screen.
     *
     * @param fbwg The game
     * @param model The model to be drawn
     */
    public GameScreen(FireBoyWaterGirl fbwg, GameModel model) {
        this.fbwg = fbwg;
        this.model = model;

        loadImages();

        maploader = new TmxMapLoader();
        //tiledmap = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledmap);

        createViews();

        camera = createCamera();
    }

    /**
     * Creates the camera that will display the viewport.
     *
     * @return the camera.
     */
    private OrthographicCamera createCamera(){

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    public void createViews(){
        ballView = new BallView(fbwg);
        buttonView = new ButtonView(fbwg);
        cubeView = new CubeView(fbwg);
        diamondView = new DiamondView(fbwg);
        fireBoyView = new FireBoyView(fbwg);
        lakeView = new LakeView(fbwg);
        leverView = new LeverView(fbwg);
        platformView = new PlatformView(fbwg);
        portalView = new PortalView(fbwg);
        wallView = new WallView(fbwg);
        waterGirlView = new WaterGirlView(fbwg);
    }

    /**
     * Loads the images used in this screen.
     */
    public void loadImages(){

        this.fbwg.getAssetManager().load("fireboy.png", Texture.class);
        this.fbwg.getAssetManager().load("rightFire.png", Texture.class);
        this.fbwg.getAssetManager().load("rightWater.png", Texture.class);
        this.fbwg.getAssetManager().load("leftFire.png", Texture.class);
        this.fbwg.getAssetManager().load("leftWater.png", Texture.class);
        this.fbwg.getAssetManager().load("jumpFire.png", Texture.class);
        this.fbwg.getAssetManager().load("jumpWater.png", Texture.class);
        this.fbwg.getAssetManager().load("standFire.png", Texture.class);
        this.fbwg.getAssetManager().load("rightFire.png", Texture.class);
        this.fbwg.getAssetManager().load("leftFire.png", Texture.class);
        this.fbwg.getAssetManager().load("jumpFire.png", Texture.class);
        this.fbwg.getAssetManager().load("standWater.png", Texture.class);
        this.fbwg.getAssetManager().load("rightWater.png", Texture.class);
        this.fbwg.getAssetManager().load("leftWater.png", Texture.class);
        this.fbwg.getAssetManager().load("jumpWater.png", Texture.class);
        this.fbwg.getAssetManager().load("ball.png", Texture.class);
        this.fbwg.getAssetManager().load("button.png", Texture.class);
        this.fbwg.getAssetManager().load("cube.png", Texture.class);
        this.fbwg.getAssetManager().load("diamond.png", Texture.class);
        this.fbwg.getAssetManager().load("lake.png", Texture.class);
        this.fbwg.getAssetManager().load("portal.png", Texture.class);
        this.fbwg.getAssetManager().load("lever.png", Texture.class);
        this.fbwg.getAssetManager().load("wall.png", Texture.class);
        this.fbwg.getAssetManager().load("platform.png", Texture.class);

        this.fbwg.getAssetManager().finishLoading();
    }

    /**
     * renders this screen
     *
     * @param delta time in seconds since last render
     */
    @Override
    public void render(float delta) {

        handleInputs(delta);
        updateObjects(delta);

        //camera updates se quiseremos usar camara.
        camera.position.set(model.getFireBoy().getX(),model.getFireBoy().getY(),0);

        //clear screen
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        fbwg.getSpriteBatch().begin();
        //drawBackground
        drawObjects();
        fbwg.getSpriteBatch().end();
    }

    /**
     * Draw objects on the screen
     */
    private void drawObjects(){

        fireBoyView.update(model.getFireBoy());
        fireBoyView.draw(fbwg.getSpriteBatch());

        waterGirlView.update(model.getWaterGirl());
        waterGirlView.draw(fbwg.getSpriteBatch());

        //missing the rest of the object draws
    }

    private void updateObjects(float delta) {
        model.getFireBoy().update(delta);
    }

    private void handleInputs(float delta) {
        model.getFireBoy().handleInputs(delta);
    }

}