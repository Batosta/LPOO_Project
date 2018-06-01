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
    /**
     * HashMap with the view of the horizontal and vertical colored doors to be opened by the colored buttons
     */
    HashMap<String, ODoorView> ODoorsView;

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

    private HashMap<String, ODoorBody> ODoors;
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

    public Level(FireBoyWaterGirl game, GameScreen gamescreen, String mappath){
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
    }

    public void createObjects(){

        todestroydiamonds = new Queue<com.badlogic.gdx.physics.box2d.Body>();
        setFireboy2D(new FireBoy2D(world,50f*GameScreen.PIXEL_TO_METER,100f*GameScreen.PIXEL_TO_METER));
        setWatergirl2D(new WaterGirl2D(world,50f*GameScreen.PIXEL_TO_METER,200f*GameScreen.PIXEL_TO_METER));

        BodyDef bdef = new BodyDef();
        PolygonShape polyshape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //  Fire Boy and Water Girl positions
//        for (MapObject object : getTiledmap().getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
//            if(object.getName()=="fireboy"){
//
//            } else {
//
//            }
//        }

        //Green Lakes
        greenlakes = new Queue<LakeBody>();
        for (MapObject object : getTiledmap().getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            greenlakes.addFirst(new LakeBody(world,object,2));      //2 if green
        }

        //Blue Lakes
        bluelakes = new Queue<LakeBody>();
        for (MapObject object : getTiledmap().getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            bluelakes.addFirst(new LakeBody(world,object,1));      //1 if blue
        }

        //Red Lakes
        redlakes = new Queue<LakeBody>();
        for (MapObject object : getTiledmap().getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            redlakes.addFirst(new LakeBody(world,object,0));      //2 if green
        }


        setODoors(new HashMap<String, ODoorBody>());
        ODoorsView = new HashMap<String, ODoorView>();
        for (MapObject object : getTiledmap().getLayers().get(11).getObjects().getByType(RectangleMapObject.class)){         //ADD DOORS COLORS HERE

            getODoors().put(object.getName(),new ODoorBody(world,object,0));
            if(object.getName().equals("purple"))
                ODoorsView.put(object.getName(), new ODoorView(game,"horpurpledoor.png"));
            if(object.getName().equals("red"))
                ODoorsView.put(object.getName(), new ODoorView(game,"horreddoor.png"));
        }

        for (MapObject object : getTiledmap().getLayers().get(10).getObjects().getByType(RectangleMapObject.class)){
            getODoors().put(object.getName(),new ODoorBody(world,object,1));
            if(object.getName().equals("purple"))
                ODoorsView.put(object.getName(), new ODoorView(game,"verpurpledoor.png"));
            if(object.getName().equals("red")) {
                ODoorsView.put(object.getName(), new ODoorView(game,"verreddoor.png"));
            }
        }

        buttons = new HashMap<String, ButtonBody>();
        for (MapObject object : getTiledmap().getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
            buttons.put(object.getName(),new ButtonBody(world,object));
        }

        for (MapObject object : getTiledmap().getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            System.out.println("test");
            if(object.getName().equals("bluedoor")) {
                bluedoorbody = new DoorBody(world, object, 1);
                System.out.println("door"+bluedoorbody);
            }
            else if (object.getName().equals("reddoor"))
                reddoorbody = new DoorBody(world, object, 0);
        }

        for (MapObject object : getTiledmap().getLayers().get(7).getObjects().getByType(PolygonMapObject.class)) {
            Polygon poly = ((PolygonMapObject) object).getPolygon();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(poly.getX()*GameScreen.PIXEL_TO_METER,poly.getY()*GameScreen.PIXEL_TO_METER);
            float[] vertices = poly.getVertices();
            float[] newVertices = new float[vertices.length];
            for (int i = 0; i < vertices.length; ++i) {
                newVertices[i] = vertices[i]*GameScreen.PIXEL_TO_METER;
            }
            body = world.createBody(bdef);
            polyshape.set(newVertices);
            fdef.shape = polyshape;
            fdef.isSensor=false;
            body.createFixture(fdef).setUserData("rampa");
        }

        for (MapObject object : getTiledmap().getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)  * GameScreen.PIXEL_TO_METER, (rect.getY() + rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);

            body = world.createBody(bdef);

            polyshape.setAsBox((rect.getWidth() / 2)*GameScreen.PIXEL_TO_METER, (rect.getHeight() / 2)*GameScreen.PIXEL_TO_METER);
            fdef.shape = polyshape;

            body.createFixture(fdef);
        }

        setBluediamonds(new Queue<DiamondBody>());
        for (MapObject object : getTiledmap().getLayers().get(5).getObjects().getByType(PolygonMapObject.class)) {
            getBluediamonds().addFirst(new DiamondBody(world,object,1));     //1 if blue
        }

        setReddiamonds(new Queue<DiamondBody>());
        for (MapObject object : getTiledmap().getLayers().get(4).getObjects().getByType(PolygonMapObject.class)) {
            getReddiamonds().addFirst(new DiamondBody(world,object,0));      //0 if red
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
        if(ODoors.get("purple")!=null)
        ODoors.get("purple").update(delta);
        if(ODoors.get("red")!=null)
        ODoors.get("red").update(delta);
    }

    public void checkLevelStatus() {
        if(bluedoorbody.getDooropened()/* && reddoorbody.getDooropened() && getBluediamonds().size == 0 && getReddiamonds().size == 0*/) {
            tiledmap.getLayers().get(10).setVisible(false);
            setGamewon(true);
        }
    }

    public void handleInput(float delta){
        //              FireBoy

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(fireboy2D.jumpstate == BoxCharacter.Jump.STOP)
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(0,8.3f), fireboy2D.getB2body().getWorldCenter(),true);
            else if (fireboy2D.canjump) {
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(0, 8.3f), fireboy2D.getB2body().getWorldCenter(), true);
                fireboy2D.canjump=false;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && fireboy2D.getB2body().getLinearVelocity().x <= 6){
            fireboy2D.getB2body().applyLinearImpulse(new Vector2(0.5f,0), fireboy2D.getB2body().getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && fireboy2D.getB2body().getLinearVelocity().x >= -6) {
            fireboy2D.getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), fireboy2D.getB2body().getWorldCenter(), true);
        }
        // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
        if(!Gdx.input.isKeyPressed(Input.Keys.LEFT) && !Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(fireboy2D.getB2body().getLinearVelocity().x>0) {
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), fireboy2D.getB2body().getWorldCenter(), true);
                if(fireboy2D.getB2body().getLinearVelocity().x<0)
                    fireboy2D.getB2body().setLinearVelocity(0, fireboy2D.getB2body().getLinearVelocity().y);
            }
            if(fireboy2D.getB2body().getLinearVelocity().x<0) {
                fireboy2D.getB2body().applyLinearImpulse(new Vector2(0.4f, 0), fireboy2D.getB2body().getWorldCenter(), true);
                if (fireboy2D.getB2body().getLinearVelocity().x > 0)
                    fireboy2D.getB2body().setLinearVelocity(0, fireboy2D.getB2body().getLinearVelocity().y);
            }
        }

        //              WaterGirl input
        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            if(watergirl2D.jumpstate == BoxCharacter.Jump.STOP)
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(0,8.3f), watergirl2D.getB2body().getWorldCenter(),true);
            else if (watergirl2D.canjump) {
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(0, 8.3f), watergirl2D.getB2body().getWorldCenter(), true);
                watergirl2D.canjump=false;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) && watergirl2D.getB2body().getLinearVelocity().x <= 6){
            watergirl2D.getB2body().applyLinearImpulse(new Vector2(0.5f,0), watergirl2D.getB2body().getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) && watergirl2D.getB2body().getLinearVelocity().x >= -6) {
            watergirl2D.getB2body().applyLinearImpulse(new Vector2(-0.5f, 0), watergirl2D.getB2body().getWorldCenter(), true);
        }
        // TODO por isto a dar bem. a sprite a cair para a direita/esquerda;
        if(!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(watergirl2D.getB2body().getLinearVelocity().x>0) {
                watergirl2D.getB2body().applyLinearImpulse(new Vector2(-0.4f, 0), watergirl2D.getB2body().getWorldCenter(), true);
                if (watergirl2D.getB2body().getLinearVelocity().x < 0)
                    watergirl2D.getB2body().setLinearVelocity(0, watergirl2D.getB2body().getLinearVelocity().y);
            }
            if(watergirl2D.getB2body().getLinearVelocity().x<0) {
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

    public HashMap<String, ODoorView> getODoorsView(){
        return ODoorsView;
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

    public FireBoy2D getFireboy2D() {
        return fireboy2D;
    }

    public void setFireboy2D(FireBoy2D fireboy2D) {
        this.fireboy2D = fireboy2D;
    }

    public WaterGirl2D getWatergirl2D() {
        return watergirl2D;
    }

    public void setWatergirl2D(WaterGirl2D watergirl2D) {
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
    public HashMap<String, ODoorBody> getODoors() {
        return ODoors;
    }

    public void setODoors(HashMap<String, ODoorBody> ODoors) {
        this.ODoors = ODoors;
    }

    public boolean isGamewon() {
        return gamewon;
    }

    public void setGamewon(boolean gamewon) {
        this.gamewon = gamewon;
    }
}
