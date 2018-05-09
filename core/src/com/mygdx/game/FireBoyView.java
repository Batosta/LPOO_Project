package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Character.Jump.*;
import static com.mygdx.game.Character.Moving.*;

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


    /**
     * Updates this Fire Boy model.
     *
     * @param model the model used to update this view
     */
    @Override
    public void update(Body model) {        //dar re-check do código. Pode ser melhorado i guess
        super.update(model);

        if(((Character)model).getMoving() == STAND){        // ESTA SEMPRE A POR STAND. depois mete por cima as outras posiçoes

            sprite.setRegion(standTex);
        }
        if(((Character)model).getMoving() == RIGHT)
        {

            System.out.println("right tex");
            sprite.setRegion(rightTex);
        }
        if(((Character)model).getMoving() == LEFT){

            sprite.setRegion(leftTex);
        }
        if(((FireBoy)model).jumpstate == ASCENDING){

            System.out.println("ascending tex");
            sprite.setRegion(upTex);
        }
        if((((FireBoy)model).jumpstate == ASCENDING) && (((Character)model).getMoving() == RIGHT)){

            sprite.setRegion(upRightTex);
        }
        if((((FireBoy)model).jumpstate == ASCENDING) && (((Character)model).getMoving() == LEFT)){

            sprite.setRegion(upLeftTex);
        }
        if(((FireBoy)model).jumpstate == DESCENDING){                       //nao há esta text ainda

            System.out.println("descending tex");
            sprite.setRegion(standTex);
        }
        if((((FireBoy)model).jumpstate == DESCENDING) && (((Character)model).getMoving() == LEFT)){

            sprite.setRegion(downLeftTex);
        }
        if((((FireBoy)model).jumpstate == DESCENDING) && (((Character)model).getMoving() == RIGHT)){

            sprite.setRegion(downRightTex);
        }

        // goingright = ((FireBoy)model).isAccelerating();
        //((ShipModel)model).setAccelerating(false);
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
