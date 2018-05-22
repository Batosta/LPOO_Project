package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.lang.Object;

/**
 * Game screen. Draws all the views of all objects of the game.
 */
public class GameScreen extends ScreenAdapter{

    /**
     * Map width (meters).
     */
    public static int MAP_WIDTH = 30;

    /**
     * Map height (meters).
     */
    public static int MAP_HEIGHT = 25;

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
    public static float PIXEL_TO_METER = 1/32f;

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
     * The game viewport.
     */
    private Viewport gamePort;

    /**
     * The music playing.
     */
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("facil.mp3"));

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

    /**
     * Fire Boy used in Box2D
     */
    private FireBoy2D fireboy2d;

    private World world;

    private Box2DDebugRenderer boxrenderer;

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
        tiledmap = maploader.load("provmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledmap,PIXEL_TO_METER);

        createViews();

        camera = createCamera();
        gamePort = new FitViewport(VIEWPORT_WIDTH*PIXEL_TO_METER,VIEWPORT_HEIGHT*PIXEL_TO_METER,camera);

        world = new World(new Vector2(0, -15f), true);
        boxrenderer = new Box2DDebugRenderer();

        createObjects();
    }

    /**
     * Creates the camera that will display the viewport.
     *
     * @return the camera.
     */
    private OrthographicCamera createCamera(){

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;
    }

    public void createViews(){

        ballView = new BallView(fbwg, "ball.png");
        buttonView = new ButtonView(fbwg, "purpleButton.png");
        cubeView = new CubeView(fbwg, "cube.png");
        diamondView = new DiamondView(fbwg, "redDiamond.png");
        fireBoyView = new FireBoyView(fbwg, "fire.png");
        lakeView = new LakeView(fbwg, "redLake.png");
        leverView = new LeverView(fbwg, "lever.png");
        platformView = new PlatformView(fbwg, "purplePlatform.png");
        portalView = new PortalView(fbwg, "portal.png");
        wallView = new WallView(fbwg, "wall.png");
        waterGirlView = new WaterGirlView(fbwg, "water.png");
    }

    /**
     * Loads the images used in this screen.
     */
    public void loadImages(){

        this.fbwg.getAssetManager().load("fire.png", Texture.class);
        this.fbwg.getAssetManager().load("water.png", Texture.class);
        this.fbwg.getAssetManager().load("ball.png", Texture.class);
        this.fbwg.getAssetManager().load("purpleButton.png", Texture.class);
        this.fbwg.getAssetManager().load("cube.png", Texture.class);
        this.fbwg.getAssetManager().load("blueDiamond.png", Texture.class);
        this.fbwg.getAssetManager().load("redDiamond.png", Texture.class);
        this.fbwg.getAssetManager().load("greenLake.png", Texture.class);
        this.fbwg.getAssetManager().load("blueLake.png", Texture.class);
        this.fbwg.getAssetManager().load("redLake.png", Texture.class);
        this.fbwg.getAssetManager().load("portal.png", Texture.class);
        this.fbwg.getAssetManager().load("lever.png", Texture.class);
        this.fbwg.getAssetManager().load("wall.png", Texture.class);
        this.fbwg.getAssetManager().load("purplePlatform.png", Texture.class);

        this.fbwg.getAssetManager().load("facil.mp3", Music.class);

        this.fbwg.getAssetManager().finishLoading();
    }

    /**
     * renders this screen
     *
     * @param delta time in seconds since last render
     */
    @Override
    public void render(float delta) {

        handleInput(delta);
        handleInputs(delta);
        updateObjects(delta);
        renderer.setView(camera);

        //camera updates se quiseremos usar camara.
        //camera.position.x = fireboy2d.b2body.getPosition().x;
        //camera.position.y = fireboy2d.b2body.getPosition().y;
        //camera.position.set(model.getFireBoy().getX(),model.getFireBoy().getY(),0);

        //clear screen
        Gdx.gl.glClearColor(0 / 255f, 0 / 255f, 0 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //game map renderer
        renderer.render();

        // world renderer
        boxrenderer.render(world,camera.combined);

        world.step(1/60f, 6, 2);

        fbwg.getSpriteBatch().begin();
        //drawBackground
        drawObjects();
        fbwg.getSpriteBatch().end();

        music.setVolume((float) 0.05);
        music.play();
    }

    /**
     * Draw objects on the screen
     */
    private void drawObjects(){

        fireBoyView.update(fireboy2d);
        fireBoyView.draw(fbwg.getSpriteBatch());

        //waterGirlView.update(watergirl2d);
        waterGirlView.draw(fbwg.getSpriteBatch());

        //missing the rest of the object draws
    }

    private void updateObjects(float delta) {

        model.getFireBoy().update(delta);
        model.getWaterGirl().handleInputs(delta);
        fireboy2d.update(delta);
    }

    private void handleInputs(float delta) {
        model.getFireBoy().handleInputs(delta);
        model.getWaterGirl().handleInputs(delta);
    }

    public void handleInput(float delta){

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            fireboy2d.b2body.applyLinearImpulse(new Vector2(0,7f),fireboy2d.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && fireboy2d.b2body.getLinearVelocity().x <= 6){
            fireboy2d.b2body.applyLinearImpulse(new Vector2(0.5f,0),fireboy2d.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && fireboy2d.b2body.getLinearVelocity().x >= -6) {
            fireboy2d.b2body.applyLinearImpulse(new Vector2(-0.5f, 0), fireboy2d.b2body.getWorldCenter(), true);
        }

        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(fireboy2d.b2body.getLinearVelocity().x>0)
                fireboy2d.b2body.applyLinearImpulse(new Vector2(-0.4f, 0), fireboy2d.b2body.getWorldCenter(), true);
            if(fireboy2d.b2body.getLinearVelocity().x<0)
                fireboy2d.b2body.applyLinearImpulse(new Vector2(0.4f, 0), fireboy2d.b2body.getWorldCenter(), true);
        }



    }

    public void createObjects(){

        fireboy2d = new FireBoy2D(world,50,150);

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : tiledmap.getLayers().get(4).getObjects().getByType(PolygonMapObject.class)) {
            Polygon poly = ((PolygonMapObject) object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(poly.getX()*PIXEL_TO_METER,poly.getY()*PIXEL_TO_METER);
            float[] vertices = poly.getVertices();
            float[] newVertices = new float[vertices.length];
            for (int i = 0; i < vertices.length; ++i) {
                newVertices[i] = vertices[i]*PIXEL_TO_METER;
            }

            body = world.createBody(bdef);
            shape.set(newVertices);
            fdef.shape = shape;

            body.createFixture(fdef);
        }

        for (MapObject object : tiledmap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)  * PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*PIXEL_TO_METER);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2)*PIXEL_TO_METER, (rect.getHeight() / 2)*PIXEL_TO_METER);
            fdef.shape = shape;

            body.createFixture(fdef);
        }
    }

}