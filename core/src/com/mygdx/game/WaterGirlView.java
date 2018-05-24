package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Character.Jump.*;
import static com.mygdx.game.Character.Moving.*;

/**
 * A view representing the WaterGirl.
 */
public class WaterGirlView extends CharacterView {

    /**
     * Constructor of the WaterGirl view
     *
     * @param game the game.
     * @param text the texture with all possible positions for the Water Girl.
     */
    public WaterGirlView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }
/*
    @Override
    public void update(BoxBody model) {        //dar re-check do código. Pode ser melhorado i guess
        super.update(model);
/*
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
        if(((WaterGirl)model).jumpstate == ASCENDING){

            System.out.println("ascending tex");
            sprite.setRegion(upTex);
        }
        if((((WaterGirl)model).jumpstate == ASCENDING) && (((Character)model).getMoving() == RIGHT)){

            sprite.setRegion(upRightTex);
        }
        if((((WaterGirl)model).jumpstate == ASCENDING) && (((Character)model).getMoving() == LEFT)){

            sprite.setRegion(upLeftTex);
        }
        if(((WaterGirl)model).jumpstate == DESCENDING){                       //nao há esta text ainda

            System.out.println("descending tex");
            sprite.setRegion(standTex);
        }
        if((((WaterGirl)model).jumpstate == DESCENDING) && (((Character)model).getMoving() == LEFT)){

            sprite.setRegion(downLeftTex);
        }
        if((((WaterGirl)model).jumpstate == DESCENDING) && (((Character)model).getMoving() == RIGHT)){

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
