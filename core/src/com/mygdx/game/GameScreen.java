package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
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


import java.util.HashMap;

/**
 * Game screen. Draws all the views of all objects of the game.
 */
public class GameScreen extends ScreenAdapter implements InputProcessor {


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
    HashMap<String, PlatformView> ODoorsView;

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

    /**
     * Queue with the red diamonds to be caught by Fire Boy
     */
    Queue<DiamondBody> reddiamonds;

    /**
     * Queue with the blue diamonds to be caught by Water Girl
     */
    Queue<DiamondBody> bluediamonds;

    /**
     * Queue with the red lakes that Water Girl can not touch
     */
    Queue<LakeBody> redlakes;

    /**
     * Queue with the blue lakes that Fire Boy can not touch
     */
    Queue<LakeBody> bluelakes;

    /**
     * Queue with the green lakes that neither Water Girl and Fire Boy can not touch
     */
    Queue<LakeBody> greenlakes;

    /**
     * HashMap with the horizontal and vertical colored doors to be opened by the colored buttons
     */
    HashMap<String, PlatformBody> ODoors;

    /**
     * Queue with the diamonds set before to destroy
     */
    Queue<Body> todestroydiamonds;

    /**
     * hash map with the colored buttons to open colored doors
     */
    HashMap<String, ButtonBody> buttons;

    private World world;

    private Box2DDebugRenderer boxrenderer;

    private boolean rendering = true;

    TmxMapLoader maploader;

    TiledMap tiledmap;

    OrthogonalTiledMapRenderer renderer;

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


    /**
     * flag used in game over dialog
     */
    public boolean gameover_flag = false;

    //      Game table (with game over dialog)

    Table table;

    Image background;


    /**
     * Creates the screen.
     *
     * @param fbwg The game
     */
    public GameScreen(FireBoyWaterGirl fbwg) {
        this.fbwg = fbwg;

        maploader = new TmxMapLoader();
        tiledmap = maploader.load("level2.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledmap, 1);

        createViews();

        camera = createCamera();

        viewport = new FitViewport(GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER, GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));
        stage = new Stage(viewport, (fbwg).getSpriteBatch());

        world = new World(new Vector2(0, -15f), true);

        createObjects();
        world.setContactListener(new WorldContactListener(this));

        createInputProcessor();

        createStage();

    }


    /**
     * Creates the camera that will display the viewport.
     *
     * @return the camera.
     */
    private OrthographicCamera createCamera() {

        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER * ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth()));

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        if (DEBUG_PHYSICS) {
            boxrenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    public void createViews() {

        setFireBoyView(new FireBoyView(fbwg, "fire.png"));
        setWaterGirlView(new WaterGirlView(fbwg, "water.png"));
    }

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

        handleInput(delta);
        updateTimeLabel();
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
//        boxrenderer.render(world,camera.combined);

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
        //music.play();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            boxrenderer.render(world, debugCamera);
        }

        showGameTime(delta);

        stage.draw();
    }

    public void checkLevelStatus() {
        if (bluedoorbody.getDooropened() && reddoorbody.getDooropened() && bluediamonds.size == 0 && reddiamonds.size == 0) {
            tiledmap.getLayers().get(10).setVisible(false);
            gamewon = true;
        }
    }

    public void endGame() {
        rendering = false;
        stage.addActor(background);

    }

    private void destroyObjects() throws InterruptedException {
        for (int i = 0; i < todestroydiamonds.size; i++) {
            if (todestroydiamonds.get(i).getFixtureList().get(0).getUserData() == "bluediamond")
                bluediamonds.removeLast();
            if (todestroydiamonds.get(i).getFixtureList().get(0).getUserData() == "reddiamond")
                reddiamonds.removeLast();
            world.destroyBody(todestroydiamonds.get(i));
            todestroydiamonds.removeIndex(i);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Draw objects on the screen
     */
    private void drawObjects() {
        getFireBoyView().update(getFireboy2d());
        getFireBoyView().draw(fbwg.getSpriteBatch());

        getWaterGirlView().update(getWatergirl2d());
        getWaterGirlView().draw(fbwg.getSpriteBatch());

        for (HashMap.Entry<String, PlatformView> entry : ODoorsView.entrySet()) {

            entry.getValue().update(ODoors.get(entry.getKey()));
            entry.getValue().draw(fbwg.getSpriteBatch());
        }

        //missing the rest of the object draws
    }

    private void updateObjects(float delta) {

        fireboy2d.update(delta);
        watergirl2d.update(delta);
        bluedoorbody.update(delta);
        if (ODoors.get("purple") != null) {
            ODoors.get("purple").update(delta);
        }
        if (ODoors.get("red") != null) {
            ODoors.get("red").update(delta);
        }
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

    //Compor esta funcao para ficar mais simples
    public void handleInput(float delta) {

        //              FireBoy input
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (getFireboy2d().jumpstate == BoxCharacter.Jump.STOP)
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
            else if (getFireboy2d().canjump) {
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
                getFireboy2d().canjump = false;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && getFireboy2d().getB2body().getLinearVelocity().x <= 6) {
            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0.5f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && getFireboy2d().getB2body().getLinearVelocity().x >= -6) {
            getFireboy2d().getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
        }
        // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            System.out.println(getFireboy2d());
            if (getFireboy2d().getB2body().getLinearVelocity().x > 0) {
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
                if (getFireboy2d().getB2body().getLinearVelocity().x < 0)
                    getFireboy2d().getB2body().setLinearVelocity(0, getFireboy2d().getB2body().getLinearVelocity().y);
            }
            if (getFireboy2d().getB2body().getLinearVelocity().x < 0) {
                getFireboy2d().getB2body().applyLinearImpulse(new Vector2(0.4f, 0), getFireboy2d().getB2body().getWorldCenter(), true);
                if (getFireboy2d().getB2body().getLinearVelocity().x > 0)
                    getFireboy2d().getB2body().setLinearVelocity(0, getFireboy2d().getB2body().getLinearVelocity().y);
            }
        }
        //              WaterGirl input
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            if (getWatergirl2d().jumpstate == BoxCharacter.Jump.STOP)
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getWatergirl2d().getB2body().getWorldCenter(), true);
            else if (getWatergirl2d().canjump) {
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0, 8.3f), getFireboy2d().getB2body().getWorldCenter(), true);
                getWatergirl2d().canjump = false;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && getWatergirl2d().getB2body().getLinearVelocity().x <= 6) {
            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0.5f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && getWatergirl2d().getB2body().getLinearVelocity().x >= -6) {
            getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
        }
        // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (getWatergirl2d().getB2body().getLinearVelocity().x > 0) {
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
                if (getWatergirl2d().getB2body().getLinearVelocity().x < 0)
                    getWatergirl2d().getB2body().setLinearVelocity(0, getWatergirl2d().getB2body().getLinearVelocity().y);
            }
            if (getWatergirl2d().getB2body().getLinearVelocity().x < 0) {
                getWatergirl2d().getB2body().applyLinearImpulse(new Vector2(0.4f, 0), getWatergirl2d().getB2body().getWorldCenter(), true);
                if (getWatergirl2d().getB2body().getLinearVelocity().x > 0)
                    getWatergirl2d().getB2body().setLinearVelocity(0, getWatergirl2d().getB2body().getLinearVelocity().y);
            }
        }
    }

    private void createCharacters(){

        for(MapObject object : tiledmap.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)){

            if(object.getName().equals("fireBoy")){

                setFireboy2d(new FireBoy2D(world, ((RectangleMapObject)object).getRectangle().getX() * PIXEL_TO_METER, ((RectangleMapObject)object).getRectangle().getY() * PIXEL_TO_METER));
            } else{

                setWatergirl2d(new WaterGirl2D(world, ((RectangleMapObject)object).getRectangle().getX() * PIXEL_TO_METER, ((RectangleMapObject)object).getRectangle().getY() * PIXEL_TO_METER));            }
        }
    }

    public void createObjects() {

        todestroydiamonds = new Queue<Body>();

        BodyDef bdef = new BodyDef();
        PolygonShape polyshape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        createCharacters();

        //Green Lakes
        greenlakes = new Queue<LakeBody>();
        for (MapObject object : tiledmap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            greenlakes.addFirst(new LakeBody(world, object, 2));      //2 if green
        }
        //Red Lakes
        redlakes = new Queue<LakeBody>();
        for (MapObject object : tiledmap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            redlakes.addFirst(new LakeBody(world, object, 0));      //2 if green
        }

        //Blue Lakes
        bluelakes = new Queue<LakeBody>();
        for (MapObject object : tiledmap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            bluelakes.addFirst(new LakeBody(world, object, 1));      //1 if blue
        }


        bluediamonds = new Queue<DiamondBody>();
        for (MapObject object : tiledmap.getLayers().get(7).getObjects().getByType(PolygonMapObject.class)) {
            bluediamonds.addFirst(new DiamondBody(world, object, 1));     //1 if blue
        }

        reddiamonds = new Queue<DiamondBody>();
        for (MapObject object : tiledmap.getLayers().get(8).getObjects().getByType(PolygonMapObject.class)) {
            reddiamonds.addFirst(new DiamondBody(world, object, 0));      //0 if red
        }


        ODoors = new HashMap<String, PlatformBody>();
        ODoorsView = new HashMap<String, PlatformView>();
        for (MapObject object : tiledmap.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {         //ADD DOORS COLORS HERE

            ODoors.put(object.getName(), new PlatformBody(world, object, 0));
            if (object.getName().equals("purple"))
                ODoorsView.put(object.getName(), new PlatformView(fbwg, "horpurpledoor.png"));
            if (object.getName().equals("red"))
                ODoorsView.put(object.getName(), new PlatformView(fbwg, "horreddoor.png"));
        }

        for (MapObject object : tiledmap.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            ODoors.put(object.getName(), new PlatformBody(world, object, 1));
            if (object.getName().equals("purple"))
                ODoorsView.put(object.getName(), new PlatformView(fbwg, "verpurpledoor.png"));
            if (object.getName().equals("red")) {
                ODoorsView.put(object.getName(), new PlatformView(fbwg, "verreddoor.png"));
            }
        }

        buttons = new HashMap<String, ButtonBody>();
        for (MapObject object : tiledmap.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            buttons.put(object.getName(), new ButtonBody(world, object));
        }

        for (MapObject object : tiledmap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            if (object.getName().equals("blueDoor")) {
                bluedoorbody = new DoorBody(world, object, 1);
            } else if (object.getName().equals("redDoor"))
                reddoorbody = new DoorBody(world, object, 0);
        }

        for (MapObject object : tiledmap.getLayers().get(1).getObjects().getByType(PolygonMapObject.class)) {
            Polygon poly = ((PolygonMapObject) object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(poly.getX() * PIXEL_TO_METER, poly.getY() * PIXEL_TO_METER);
            float[] vertices = poly.getVertices();
            float[] newVertices = new float[vertices.length];
            for (int i = 0; i < vertices.length; ++i) {
                newVertices[i] = vertices[i] * PIXEL_TO_METER;
            }
            body = world.createBody(bdef);
            polyshape.set(newVertices);
            fdef.shape = polyshape;
            fdef.isSensor = false;
            body.createFixture(fdef).setUserData("rampa");
        }

        for (MapObject object : tiledmap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2) * PIXEL_TO_METER);

            body = world.createBody(bdef);

            polyshape.setAsBox((rect.getWidth() / 2) * PIXEL_TO_METER, (rect.getHeight() / 2) * PIXEL_TO_METER);
            fdef.shape = polyshape;

            body.createFixture(fdef);
        }
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
     * Create the whole Screen Stage
     */
    public void createStage() {

        background = new Image((Texture) fbwg.getAssetManager().get("gameover_dialog.png"));
        stage = new Stage(viewport, fbwg.getSpriteBatch());
        table = new Table();

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.GREEN);
        table.top();
        table.setFillParent(true);
        Label timeLabel = new Label("TIME", font);
        table.add(timeLabel).expandX();
        table.row();
        this.timeLabel = new Label("00:00", font);
        table.add(this.timeLabel);
        table.debugAll();

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
        return false;
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
     * The Water Girl view used to draw the Water Girl.
     */
    public WaterGirlView getWaterGirlView() {
        return waterGirlView;
    }

    public void setWaterGirlView(WaterGirlView waterGirlView) {
        this.waterGirlView = waterGirlView;
    }

    /**
     * Fire Boy used in Box2D
     */
    public FireBoy2D getFireboy2d() {
        return fireboy2d;
    }

    public void setFireboy2d(FireBoy2D fireboy2d) {
        this.fireboy2d = fireboy2d;
    }

    /**
     * The Fire Boy view used to the Fire Boy.
     */
    public FireBoyView getFireBoyView() {
        return fireBoyView;
    }

    public void setFireBoyView(FireBoyView fireBoyView) {
        this.fireBoyView = fireBoyView;
    }

    /**
     * Water Girl used in Box2D
     */
    public WaterGirl2D getWatergirl2d() {
        return watergirl2d;
    }

    public void setWatergirl2d(WaterGirl2D watergirl2d) {
        this.watergirl2d = watergirl2d;
    }
}
