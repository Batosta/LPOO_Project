package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A view representing the Platform
 */
public class PlatformView extends BodyView {

    /**
     * Time used to select the current animation frame.
     */
    private float stateTime = 0;

    /**
     * The whole animation of the platform
     */
    private Animation animation;


    /**
     * Constructor of the Platform view
     *
     * @param game the game.
     * @param text the texture with all possible positions for the Platform
     */
    public PlatformView(FireBoyWaterGirl game, String text) {

        super(game, text);
    }

    /**
     * Function that updates the body, platform in this case, from World
     *
     * @param body Body from World which view will use to be updated.
     */
    @Override
    public void update(BoxBody body){
        super.update(body);
        if(((PlatformBody)body).platformState==PlatformBody.PlatformState.OPENING){
            stateTime += Gdx.graphics.getDeltaTime();
            sprite.setRegion((TextureRegion)animation.getKeyFrame(stateTime));
        } else if(((PlatformBody)body).platformState==PlatformBody.PlatformState.CLOSING){
                //animation.setPlayMode(Animation.PlayMode.REVERSED);
                sprite.setRegion((TextureRegion)animation.getKeyFrame(stateTime));
                stateTime -= Gdx.graphics.getDeltaTime();
        }
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
        sprite.draw(batch);
    }

    /**
     * Abstract method creates the sprites for all the levers.
     *
     * @param game the game.
     * @param text the texture with all possible positions for the platform
     *
     * @return the sprite that should be used with the current moving flag
     */
    @Override
    public Sprite createSprite(FireBoyWaterGirl game, String text) {
        Texture texture = game.getAssetManager().get(text);
        TextureRegion[] frames = new TextureRegion[11];
        if(text.contains("hor")) {
            TextureRegion[][] thrustRegion = TextureRegion.split(
                    texture,
                    96,    // 3 columns
                    32);   // 4 lines

            int index = 0;

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (index == 11)
                        break;
                    frames[index++] = thrustRegion[i][j];
                }
            }
        } else if(text.contains("ver")){
            TextureRegion[][] thrustRegion = TextureRegion.split(
                    texture,
                    32,    // 3 columns
                    96);   // 4 lines

            int index = 0;

            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 3; i++) {
                    if (index == 11)
                        break;
                    frames[index++] = thrustRegion[i][j];
                }
            }

        }

        // 0.25 seconds per frame
        animation = new Animation<TextureRegion>(.10f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        Sprite sprite = new Sprite((TextureRegion)animation.getKeyFrame(0));
        sprite.setSize(sprite.getWidth(),sprite.getHeight());
        return sprite;
    }
}
