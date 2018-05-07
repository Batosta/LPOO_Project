package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.game.Character.Moving.*;
import static com.mygdx.game.FireBoy.Jump.ASCENDING;
import static com.mygdx.game.FireBoy.Jump.DESCENDING;

/**
 * A view representing the FireBoy
 */
public class FireBoyView extends BodyView {

    /**
     * The texture used when the Fire Boy is moving right
     */
     private TextureRegion rightTex;

    /**
     * The texture used when the Fire Boy is moving left
     */
    private TextureRegion leftTex;

    /**
     * The texture used when the Fire Boy is standing
     */
    private TextureRegion standTex;

    /**
     * The texture used when the Fire Boy is jumping
     */
    private TextureRegion upTex;

    /**
     * The texture used when the Fire Boy is jumping right
     */
    private TextureRegion upRightTex;

    /**
     * The texture used when the Fire Boy is jumping left
     */
    private TextureRegion upLeftTex;

    /**
     * The texture used when the Fire Boy going down and right
     */
    private TextureRegion downRightTex;

    /**
     * The texture used when the Fire Boy going down and right
     */
    private TextureRegion downLeftTex;

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
        return new Sprite(standTex);
    }

    public void createRegions(FireBoyWaterGirl game){
        Texture texture = game.getAssetManager().get("fire.png");
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

