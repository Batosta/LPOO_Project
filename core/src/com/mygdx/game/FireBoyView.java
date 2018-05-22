package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Character.Jump.*;
import static com.mygdx.game.Character.Moving.*;
import static com.mygdx.game.GameScreen.PIXEL_TO_METER;

/**
 * A view representing the FireBoy
 */
public class FireBoyView extends CharacterView {

    /**
     * constructor of the fireboy view
     *
     * @param game the game.
     * @param text the texture with all possible positions for the Fire Boy.
     */
    public FireBoyView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }
    FireBoy2D fb;


    /**
     * Updates this Fire Boy model.
     *
     * @param body the body from world. to update the sprites
     */
    @Override
    public void update(BoxBody body) {

        System.out.println("x:" + body.b2body.getPosition().x + "y: " + body.b2body.getPosition().y);
        sprite.setCenter(body.b2body.getPosition().x/ PIXEL_TO_METER, body.b2body.getPosition().y / PIXEL_TO_METER);

        if(((BoxCharacter)body).getMoving() == BoxCharacter.Moving.STAND){        // ESTA SEMPRE A POR STAND. depois mete por cima as outras posiçoes
            System.out.println("stand tex");
            sprite.setRegion(standTex);
        }
        if(((BoxCharacter)body).getMoving() == BoxCharacter.Moving.RIGHT)
        {

            System.out.println("right tex");
            sprite.setRegion(rightTex);
        }
        if(((BoxCharacter)body).getMoving() == BoxCharacter.Moving.LEFT){

            sprite.setRegion(leftTex);
        }
        if(((BoxCharacter)body).jumpstate == FireBoy2D.Jump.ASCENDING){
            System.out.println("ascendin");
            sprite.setRegion(upTex);
        }/*
        if(((body2d).jumpstate == ASCENDING) && ((body2d).getMoving() == RIGHT)){

            sprite.setRegion(upRightTex);
        }
        if(((body2d).jumpstate == ASCENDING) && ((body2d).getMoving() == LEFT)){

            sprite.setRegion(upLeftTex);
        }
        if((body2d).jumpstate == DESCENDING){                       //nao há esta text ainda

            System.out.println("descending tex");
            sprite.setRegion(standTex);
        }
        if(((body2d).jumpstate == DESCENDING) && ((body2d).getMoving() == LEFT)){

            sprite.setRegion(downLeftTex);
        }
        if(((body2d).jumpstate == DESCENDING) && ((body2d).getMoving() == RIGHT)){

            sprite.setRegion(downRightTex);
        }

        // goingright = ((FireBoy)model).isAccelerating();
        //((ShipModel)model).setAccelerating(false);*/
    }

    /**
     * Draws the sprite from this view using a sprite batch.
     * Chooses the correct texture or animation to be used
     * depending on the moving flags.
     *
     * @param batch The sprite batch to be used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        //                      if (goingleft)
        //                    sprite.setRegion(acceleratingAnimation.getKeyFrame(stateTime, true));
        //          else
        //            sprite.setRegion(notAcceleratingRegion);

        sprite.draw(batch);
    }

}
