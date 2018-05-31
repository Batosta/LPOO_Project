package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.mygdx.game.Character.Jump.*;
import static com.mygdx.game.Character.Moving.*;
import static com.mygdx.game.GameScreen.BATCH_CONST;
import static com.mygdx.game.GameScreen.PIXEL_TO_METER;

/**
 * A view representing the ODoor
 */
public class ODoorView extends BodyView {

    /**
     * Time used to select the current animation frame.
     */
    private float stateTime = 0;

    private Animation animation;

    private int dir;

    /**
     * constructor of the ODoor view
     *
     * @param game the game.
     * @param text the texture with all possible positions for the ODoor
     */
    public ODoorView(FireBoyWaterGirl game, String text) {              // dir == 0 if horizontal, 1 if vertical
        super(game, text);
    }

    @Override
    public void update(BoxBody body){
        super.update(body);
        if(((ODoorBody)body).doorstate==ODoorBody.DoorState.OPENING){
            stateTime += Gdx.graphics.getDeltaTime();
            sprite.setRegion((TextureRegion)animation.getKeyFrame(stateTime));
        } else if(((ODoorBody)body).doorstate==ODoorBody.DoorState.CLOSING){
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

        System.out.println("after");
        // 0.25 seconds per frame
        animation = new Animation<TextureRegion>(.10f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);                           // Can hide this
        //return new Sprite((Texture)game.getAssetManager().get(text));
        Sprite sprite = new Sprite((TextureRegion)animation.getKeyFrame(0));
        sprite.setSize(sprite.getWidth(),sprite.getHeight());
        return sprite;
    }
}
