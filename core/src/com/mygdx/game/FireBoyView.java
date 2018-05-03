package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A view representing the FireBoy
 */
public class FireBoyView extends BodyView {

    private boolean goingright;

    /**
     * constructor of the fireboy view
     *
     * @param game the game.
     */
    public FireBoyView(FireBoyWaterGirl game) {

        super(game);
    }

    /**
     * Abstract method creates the sprites for the Fire Boy.
     *
     * @param game the game.
     *
     * @return the sprite representing Fire Boy view.
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game){

        Texture texture = game.getAssetManager().get("standFire.png");
        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

        /**
          * Updates this ship model. Also save and resets
          * the accelerating flag from the model.
          *
          * @param model the model used to update this view
          */

        @Override
    public void update(Body model) {
                super.update(model);

                      // goingright = ((FireBoy)model).isAccelerating();
                //((ShipModel)model).setAccelerating(false);
            }

        /**
          * Draws the sprite from this view using a sprite batch.
          * Chooses the correct texture or animation to be used
          * depending on the accelerating flag.
          *
          * @param batch The sprite batch to be used for drawing.
          */
                @Override
    public void draw(SpriteBatch batch) {
//
  //                      if (goingleft)
    //                    sprite.setRegion(acceleratingAnimation.getKeyFrame(stateTime, true));
      //          else
        //            sprite.setRegion(notAcceleratingRegion);

                        sprite.draw(batch);
           }

}

