package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.game.Character.Moving.*;

/**
 * A view representing the FireBoy
 */
public class FireBoyView extends BodyView {

    /**
     * The texture used when the Fire Boy is moving right
     */
     private TextureRegion movrighttex;

    /**
     * The texture used when the Fire Boy is moving left
     */
    private TextureRegion movlefttex;

    /**
     * The texture used when the Fire Boy is stand
     */
    private TextureRegion standtex;

    /**
     * The texture used when the Fire Boy is jumping
     */
    private TextureRegion jumptex;

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
        createRegions(game);
        return new Sprite(standtex);
    }

    public void createRegions(FireBoyWaterGirl game){
        Texture texture = game.getAssetManager().get("fireboy.png");
        standtex = new TextureRegion(texture, 0, 0, texture.getWidth()/4, texture.getHeight());
        movlefttex = new TextureRegion(texture, texture.getWidth()/4, 0, texture.getWidth()/4, texture.getHeight());
        movrighttex = new TextureRegion(texture, texture.getWidth()/2, 0, texture.getWidth()/4, texture.getHeight());
        jumptex = new TextureRegion(texture, texture.getWidth()*3/4,0,texture.getWidth()/4, texture.getHeight());
    }

        /**
          * Updates this Fire Boy model.
          *
          * @param model the model used to update this view
          */

        @Override
    public void update(Body model) {
            super.update(model);
            if(((Character)model).getMoving() == RIGHT)
            {
                sprite.setRegion(movrighttex);
            }
            if(((Character)model).getMoving() == LEFT){
                sprite.setRegion(movlefttex);
            }
            if(((Character)model).getMoving() == JUMP){
                sprite.setRegion(jumptex);
            }
            if(((Character)model).getMoving() == STAND){
                sprite.setRegion(standtex);
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

