package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.game.GameScreen.PIXEL_TO_METER;

/**
 * A view representing a character
 */
public class CharacterView extends BodyView {

    /**
     * Body from World which view will use to be updated.
     */
    public BoxBody body;

    /**
     * The texture used when the Character is moving right
     */
    public TextureRegion rightTex;

    /**
     * The texture used when the Character is moving left
     */
    public TextureRegion leftTex;

    /**
     * The texture used when the Character is standing
     */
    public TextureRegion standTex;

    /**
     * The texture used when the Character is jumping
     */
    public TextureRegion upTex;

    /**
     * The texture used when the Character is jumping right
     */
    public TextureRegion upRightTex;

    /**
     * The texture used when the Character is jumping left
     */
    public TextureRegion upLeftTex;

    /**
     * The texture used when the Character is going down and right
     */
    public TextureRegion downRightTex;

    /**
     * The texture used when the Character is going down and right
     */
    public TextureRegion downLeftTex;

    /**
     * The constructor of the view for the character
     *
     * @param game the game itself
     * @param text the path for the image
     */
    public CharacterView(FireBoyWaterGirl game, String text){

        super(game, text);
    }

    /**
     * Abstract method creates the sprites for the Fire Boy.
     *
     * @param game the game.
     * @param text the character texture that possesses all his possible positions.
     *
     * @return the sprite representing Fire Boy view.
     */
    public Sprite createSprite(FireBoyWaterGirl game, String text){

        createRegions(game, text);
        return new Sprite(standTex);
    }

    /**
     * Creates all the textures of the character
     *
     * @param game The game itself
     * @param text The text with all the information of the assets refering the characters
     */
    public void createRegions(FireBoyWaterGirl game, String text){

        Texture texture = game.getAssetManager().get(text);
        standTex = new TextureRegion(texture, 0, 0, texture.getWidth()/4, texture.getHeight()/2);
        leftTex = new TextureRegion(texture, texture.getWidth()/4, 0, texture.getWidth()/4, texture.getHeight()/2);
        rightTex = new TextureRegion(texture, texture.getWidth()/2, 0, texture.getWidth()/4, texture.getHeight()/2);
        upTex = new TextureRegion(texture, texture.getWidth()*3/4,0,texture.getWidth()/4, texture.getHeight()/2);
        upRightTex = new TextureRegion(texture, 0,texture.getHeight()/2,texture.getWidth()/4, texture.getHeight()/2);
        upLeftTex = new TextureRegion(texture, texture.getWidth()/4,texture.getHeight()/2,texture.getWidth()/4, texture.getHeight()/2);
        downRightTex = new TextureRegion(texture, texture.getWidth()/2,texture.getHeight()/2,texture.getWidth()/4, texture.getHeight()/2);
        downLeftTex = new TextureRegion(texture, texture.getWidth()*3/4,texture.getHeight()/2,texture.getWidth()/4, texture.getHeight()/2);
    }

    /**
     * Updates the character body
     *
     * @param body Body from World which view will use to be updated.
     */
    @Override
    public void update(BoxBody body) {

        this.body = body;

        //move sprite a bit up
        sprite.setCenter(body.getB2body().getPosition().x/PIXEL_TO_METER, body.getB2body().getPosition().y/PIXEL_TO_METER+10);

        stand();
        moving();
        ascending();
    }

    /**
     * Standing textures
     */
    private void stand(){

        if (((BoxCharacter) body).moving == BoxCharacter.Moving.STAND) {        // ESTA SEMPRE A POR STAND. depois mete por cima as outras posiçoes
            sprite.setRegion(standTex);
        }
    }

    /**
     * Moving textures
     */
    private void moving(){

        if (((BoxCharacter) body).moving == BoxCharacter.Moving.RIGHT) {

            sprite.setRegion(rightTex);
        }
        if (((BoxCharacter) body).moving == BoxCharacter.Moving.LEFT) {

            sprite.setRegion(leftTex);
        }
    }

    /**
     * Ascending textures
     */
    private void ascending() {

        if (((BoxCharacter) body).jumpstate == BoxCharacter.Jump.ASCENDING) {
            sprite.setRegion(upTex);
        }
        if (((BoxCharacter) body).jumpstate == BoxCharacter.Jump.ASCENDING && ((BoxCharacter) body).moving == BoxCharacter.Moving.RIGHT) {
            sprite.setRegion(upRightTex);
        }


        if (((BoxCharacter) body).jumpstate == BoxCharacter.Jump.ASCENDING && ((BoxCharacter) body).moving == BoxCharacter.Moving.LEFT) {
            sprite.setRegion(upLeftTex);
        }
        if (((BoxCharacter) body).jumpstate == BoxCharacter.Jump.DESCENDING) {
            sprite.setRegion(standTex);
        }
        if ((((BoxCharacter) body).jumpstate == BoxCharacter.Jump.DESCENDING) && (((BoxCharacter) body).moving == BoxCharacter.Moving.LEFT)) {

            sprite.setRegion(downLeftTex);
        }

        if ((((BoxCharacter) body).jumpstate == BoxCharacter.Jump.DESCENDING) && (((BoxCharacter) body).moving == FireBoy2D.Moving.RIGHT)) {

            sprite.setRegion(downRightTex);
        }
    }
}
