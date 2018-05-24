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


    /*
    @Override
    public void update(BoxBody body) {
        super.update(body);

        if(((BoxCharacter)body).moving == BoxCharacter.Moving.STAND){        // ESTA SEMPRE A POR STAND. depois mete por cima as outras posiçoes
            System.out.println("stand tex");
            sprite.setRegion(standTex);
        }
        if(((BoxCharacter)body).moving == BoxCharacter.Moving.RIGHT)
        {

            System.out.println("right tex");
            sprite.setRegion(rightTex);
        }
        if(((BoxCharacter)body).moving == BoxCharacter.Moving.LEFT){

            sprite.setRegion(leftTex);
        }
        if(((BoxCharacter)body).jumpstate == BoxCharacter.Jump.ASCENDING){
            System.out.println("ascendin");
            sprite.setRegion(upTex);
        }
        if(((BoxCharacter)body).jumpstate == BoxCharacter.Jump.ASCENDING && ((BoxCharacter)body).moving == BoxCharacter.Moving.RIGHT){
            sprite.setRegion(upRightTex);
        }


        if(((BoxCharacter)body).jumpstate == BoxCharacter.Jump.ASCENDING && ((BoxCharacter)body).moving == BoxCharacter.Moving.LEFT){
            sprite.setRegion(upLeftTex);
        }
        if(((BoxCharacter)body).jumpstate == BoxCharacter.Jump.DESCENDING){
            System.out.println("ascendin");
            sprite.setRegion(standTex);
        }
        if((((BoxCharacter)body).jumpstate == BoxCharacter.Jump.DESCENDING) && (((BoxCharacter)body).moving == BoxCharacter.Moving.LEFT)){

            sprite.setRegion(downLeftTex);
        }

        if((((BoxCharacter)body).jumpstate == BoxCharacter.Jump.DESCENDING) && (((BoxCharacter)body).moving == FireBoy2D.Moving.RIGHT)){

            sprite.setRegion(downRightTex);
        }

        // goingright = ((FireBoy)model).isAccelerating();
        //((ShipModel)model).setAccelerating(false);
    }
    */

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
