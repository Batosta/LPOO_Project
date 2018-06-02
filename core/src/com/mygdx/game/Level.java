package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Queue;

import java.util.HashMap;

public class Level {
    private World world;
    private TmxMapLoader maploader;
    private TiledMap tiledmap;
    private GameScreen gamescreen;
    private FireBoyWaterGirl game;

    private FireBoyView fireBoyView;
    private WaterGirlView waterGirlView;
    private FireBoy2D fireboy2D;
    private WaterGirl2D watergirl2D;

    private Queue<DiamondBody> reddiamonds;
    private Queue<DiamondBody> bluediamonds;

    private boolean gamewon=false;


    private HashMap<String, PlatformBody> platforms;

    /**
     * HashMap with the view of the horizontal and vertical colored doors to be opened by the colored buttons
     */
    HashMap<String, PlatformView> platformsView;

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
     * Queue with the diamonds set before to destroy
     */
    Queue<Body> todestroydiamonds;

    /**
     * hash map with the colored buttons to open colored doors
     */
    HashMap<String, ButtonBody> buttons;

    /**
     * Blue door body used in Box2D. Is only a sensor
     */
    public DoorBody bluedoorbody;

    /**
     * Red door body used in Box2D. Is only a sensor
     */
    public DoorBody reddoorbody;

    /**
     * the path of the tmx file with the tilemap of this level.
     */
    String mappath;

    BodyDef bdef;

    PolygonShape polyshape;

    FixtureDef fdef;

    Body body;

    int jumptimer;

    public Level(FireBoyWaterGirl game, GameScreen gamescreen, String mappath){
        jumptimer=0;
        this.mappath=mappath;
        this.game=game;
        this.gamescreen=gamescreen;
        this.maploader=new TmxMapLoader();
        loadMap(mappath);
        createWorld();
    }

    public void loadMap(String mappath){
        this.setTiledmap(maploader.load(mappath));
    }

    public void restartGame(){
        createWorld();
    }

    public void createWorld(){
        if (world != null) {
            world.dispose();
        }
        world = new World(new Vector2(0, -15f), true);
        loadMap(mappath);
//        rendering=true;
        createObjects();
        createViews();
        world.setContactListener(new WorldContactListener(this));
//        if (DEBUG_PHYSICS) {
//            boxrenderer = new Box2DDebugRenderer();
//            debugCamera = camera.combined.cpy();
//            debugCamera.scl(1 / PIXEL_TO_METER);
//        }
    }

    public void endGame(){
//        rendering=false;
        gamescreen.endGame();
    }

    public void renderLevel(float delta){
        updateObjects(delta);
        jumptimer++;
    }


    /**
     * Function that creates the body for every object that exists in the world
     */
    private void createObjects() {

        bdef = new BodyDef();
        polyshape = new PolygonShape();
        fdef = new FixtureDef();

        createCharacterObjects();
        createLakeObjects();
        createDiamondObjects();
        createPlatformObjects();
        createButtonObjects();
        createDoorObjects();
        createPolyObjects();
        createRectObjects();
    }

    /**
     * Function that creates both the FireBoy and the WaterGirl bodies in the world
     */
    private void createCharacterObjects(){

        for(MapObject object : tiledmap.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)){

            if(object.getName().equals("fireBoy")){
                fireboy2D=(new FireBoy2D(world, ((RectangleMapObject)object).getRectangle().getX() * GameScreen.PIXEL_TO_METER, ((RectangleMapObject)object).getRectangle().getY() * GameScreen.PIXEL_TO_METER));
            } else{
                watergirl2D=(new WaterGirl2D(world, ((RectangleMapObject)object).getRectangle().getX() * GameScreen.PIXEL_TO_METER, ((RectangleMapObject)object).getRectangle().getY() * GameScreen.PIXEL_TO_METER));            }
        }
    }

    /**
     * Function that creates the green, the blue and the red lake bodies in the world
     */
    private void createLakeObjects(){

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
    }

    /**
     * Function that creates both the blue and the red diamond bodies in the world
     */
    private void createDiamondObjects(){

        todestroydiamonds = new Queue<Body>();

        bluediamonds = new Queue<DiamondBody>();
        for (MapObject object : tiledmap.getLayers().get(7).getObjects().getByType(PolygonMapObject.class)) {
            bluediamonds.addFirst(new DiamondBody(world, object, 1));     //1 = blue
        }

        reddiamonds = new Queue<DiamondBody>();
        for (MapObject object : tiledmap.getLayers().get(8).getObjects().getByType(PolygonMapObject.class)) {
            reddiamonds.addFirst(new DiamondBody(world, object, 0));      //0 = red
        }
    }

    /**
     * Function that creates both the horizontal and the vertical platform bodies in the world
     */
    private void createPlatformObjects(){

        platforms = new HashMap<String, PlatformBody>();
        platformsView = new HashMap<String, PlatformView>();
        for (MapObject object : tiledmap.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {

            platforms.put(object.getName(), new PlatformBody(world, object, 0));
            if (object.getName().equals("purple"))
                platformsView.put(object.getName(), new PlatformView(game, "horpurpledoor.png"));
            if (object.getName().equals("red"))
                platformsView.put(object.getName(), new PlatformView(game, "horreddoor.png"));
        }

        for (MapObject object : tiledmap.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {

            platforms.put(object.getName(), new PlatformBody(world, object, 1));
            if (object.getName().equals("purple"))
                platformsView.put(object.getName(), new PlatformView(game, "verpurpledoor.png"));
            if (object.getName().equals("red")) {
                platformsView.put(object.getName(), new PlatformView(game, "verreddoor.png"));
            }
        }
    }

    /**
     * Function that creates the button bodies in the world
     */
    private void createButtonObjects(){

        buttons = new HashMap<String, ButtonBody>();
        for (MapObject object : tiledmap.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            buttons.put(object.getName(), new ButtonBody(world, object));
        }
    }

    /**
     * Function that creates the door bodies in the world
     */
    private void createDoorObjects(){

        for (MapObject object : tiledmap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            if (object.getName().equals("blueDoor")) {
                bluedoorbody = new DoorBody(world, object, 1);
            } else if (object.getName().equals("redDoor"))
                reddoorbody = new DoorBody(world, object, 0);
        }
    }

    /**
     * Function that creates the rectangular bodies in the world (walls)
     */
    private void createRectObjects(){

        for (MapObject object : tiledmap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2) * GameScreen.PIXEL_TO_METER);

            body = world.createBody(bdef);

            polyshape.setAsBox((rect.getWidth() / 2) * GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2) * GameScreen.PIXEL_TO_METER);
            fdef.shape = polyshape;

            body.createFixture(fdef);
        }
    }

    /**
     * Function that creates the polygonal bodies in the world (walls)
     */
    private void createPolyObjects(){

        for (MapObject object : tiledmap.getLayers().get(1).getObjects().getByType(PolygonMapObject.class)) {
            Polygon poly = ((PolygonMapObject) object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(poly.getX() * GameScreen.PIXEL_TO_METER, poly.getY() * GameScreen.PIXEL_TO_METER);
            float[] vertices = poly.getVertices();
            float[] newVertices = new float[vertices.length];
            for (int i = 0; i < vertices.length; ++i) {
                newVertices[i] = vertices[i] * GameScreen.PIXEL_TO_METER;
            }
            body = world.createBody(bdef);
            polyshape.set(newVertices);
            fdef.shape = polyshape;
            fdef.isSensor = false;
            body.createFixture(fdef).setUserData("rampa");
        }
    }

    public void createViews(){
        setFireBoyView(new FireBoyView(game,"fire.png"));
        setWaterGirlView(new WaterGirlView(game, "water.png"));
    }

    private void updateObjects(float delta) {

        fireboy2D.update(delta);
        watergirl2D.update(delta);
        bluedoorbody.update(delta);
        if(platforms.get("purple")!=null)
            platforms.get("purple").update(delta);
        if(platforms.get("red")!=null)
            platforms.get("red").update(delta);
    }

    public void checkLevelStatus() {
        if(bluedoorbody.getDooropened()/* && reddoorbody.getDooropened() && getBluediamonds().size == 0 && getReddiamonds().size == 0*/) {
            tiledmap.getLayers().get(10).setVisible(false);
            setGamewon(true);
        }
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

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)&&jumptimer>30) {
            jumptimer=0;

            if (fireboy2D.jumpstate == BoxCharacter.Jump.STOP)
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(0, 8.3f), fireboy2D.getB2body().getWorldCenter(), true);
            else if (fireboy2D.canjump) {
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(0, 8.3f), fireboy2D.getB2body().getWorldCenter(), true);
                fireboy2D.canjump = false;
            }
        }
    }

    /**
     * Function that handles the going right of the FireBoy
     */
    private void fireBoyRight(){

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && fireboy2D.getB2body().getLinearVelocity().x <= 6) {
            fireboy2D.getB2body().applyLinearImpulse(new Vector2(0.5f, 0), fireboy2D.getB2body().getWorldCenter(), true);
        }
    }

    /**
     * Function that handles the going left of the FireBoy
     */
    private void fireBoyLeft(){

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && fireboy2D.getB2body().getLinearVelocity().x >= -6) {
            fireboy2D.getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), fireboy2D.getB2body().getWorldCenter(), true);
        }
    }

    /**
     * Function that handles when the FireBoy is not moving
     */
    private void fireBoyNoDir(){

        if (!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (fireboy2D.getB2body().getLinearVelocity().x > 0) {
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), fireboy2D.getB2body().getWorldCenter(), true);
                if (fireboy2D.getB2body().getLinearVelocity().x < 0)
                    fireboy2D.getB2body().setLinearVelocity(0, fireboy2D.getB2body().getLinearVelocity().y);
            }
            if (fireboy2D.getB2body().getLinearVelocity().x < 0) {
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(0.4f, 0), fireboy2D.getB2body().getWorldCenter(), true);
                if (fireboy2D.getB2body().getLinearVelocity().x > 0)
                    fireboy2D.getB2body().setLinearVelocity(0, fireboy2D.getB2body().getLinearVelocity().y);
            }
        }
    }

    /**
     * Function that handles WaterGirl's user inputs
     */
    private void handleWaterGirlInput(){

        waterGirlUp();
        waterGirlRight();
        waterGirlLeft();
        waterGirlNoDir();
    }

    /**
     * Function that handles the jump of the WaterGirl
     */
    private void waterGirlUp(){

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)&&jumptimer>30) {
            jumptimer=0;
            if (watergirl2D.jumpstate == BoxCharacter.Jump.STOP)
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(0, 8.3f), watergirl2D.getB2body().getWorldCenter(), true);
            else if (watergirl2D.canjump) {
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(0, 8.3f), fireboy2D.getB2body().getWorldCenter(), true);
                watergirl2D.canjump = false;
            }
        }
    }

    /**
     * Function that handles the going right of the WaterGirl
     */
    private void waterGirlRight(){

        if (Gdx.input.isKeyPressed(Input.Keys.D) && watergirl2D.getB2body().getLinearVelocity().x <= 6) {
            watergirl2D.getB2body().applyLinearImpulse(new Vector2(0.5f, 0), watergirl2D.getB2body().getWorldCenter(), true);
        }
    }

    /**
     * Function that handles the going left of the WaterGirl
     */
    private void waterGirlLeft(){

        if (Gdx.input.isKeyPressed(Input.Keys.A) && watergirl2D.getB2body().getLinearVelocity().x >= -6) {
            watergirl2D.getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), watergirl2D.getB2body().getWorldCenter(), true);
        }
    }

    /**
     * Function that handles when the WaterGirl is not moving
     */
    private void waterGirlNoDir(){

        if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (watergirl2D.getB2body().getLinearVelocity().x > 0) {
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), watergirl2D.getB2body().getWorldCenter(), true);
                if (watergirl2D.getB2body().getLinearVelocity().x < 0)
                    watergirl2D.getB2body().setLinearVelocity(0, watergirl2D.getB2body().getLinearVelocity().y);
            }
            if (watergirl2D.getB2body().getLinearVelocity().x < 0) {
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(0.4f, 0), watergirl2D.getB2body().getWorldCenter(), true);
                if (watergirl2D.getB2body().getLinearVelocity().x > 0)
                    watergirl2D.getB2body().setLinearVelocity(0, watergirl2D.getB2body().getLinearVelocity().y);
            }
        }
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
     * The Water Girl view used to draw the Water Girl.
     */
    public WaterGirlView getWaterGirlView() {
        return waterGirlView;
    }

    public void setWaterGirlView(WaterGirlView waterGirlView) {
        this.waterGirlView = waterGirlView;
    }

    public HashMap<String, PlatformView> getODoorsView(){
        return platformsView;
    }

    public Queue<Body> getTodestroydiamonds(){
        return this.todestroydiamonds;
    };

    public World getWorld(){
        return this.world;
    }

    public TiledMap getTiledmap() {
        return tiledmap;
    }

    public void setTiledmap(TiledMap tiledmap) {
        this.tiledmap = tiledmap;
    }

    public FireBoy2D getfireboy2D() {
        return fireboy2D;
    }

    public void setfireboy2D(FireBoy2D fireboy2D) {
        this.fireboy2D = fireboy2D;
    }

    public WaterGirl2D getwatergirl2D() {
        return watergirl2D;
    }

    public void setwatergirl2D(WaterGirl2D watergirl2D) {
        this.watergirl2D = watergirl2D;
    }

    /**
     * Queue with the red diamonds to be caught by Fire Boy
     */
    public Queue<DiamondBody> getReddiamonds() {
        return reddiamonds;
    }

    public void setReddiamonds(Queue<DiamondBody> reddiamonds) {
        this.reddiamonds = reddiamonds;
    }

    /**
     * Queue with the blue diamonds to be caught by Water Girl
     */
    public Queue<DiamondBody> getBluediamonds() {
        return bluediamonds;
    }

    public void setBluediamonds(Queue<DiamondBody> bluediamonds) {
        this.bluediamonds = bluediamonds;
    }

    /**
     * HashMap with the horizontal and vertical colored doors to be opened by the colored buttons
     */
    public HashMap<String, PlatformBody> getODoors() {
        return platforms;
    }

    public void setODoors(HashMap<String, PlatformBody> ODoors) {
        this.platforms = ODoors;
    }

    public boolean isGamewon() {
        return gamewon;
    }

    public void setGamewon(boolean gamewon) {
        this.gamewon = gamewon;
    }
}
