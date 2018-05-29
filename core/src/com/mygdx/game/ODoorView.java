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

    boolean entered=false;

    /**
     * constructor of the ODoor view
     *
     * @param game the game.
     * @param text the texture with all possible positions for the ODoor
     */
    public ODoorView(FireBoyWaterGirl game, String text) {

        super(game, text);

    }

    @Override
    public void update(BoxBody body){
        super.update(body);
        if(((ODoorBody)body).getOpendoor()){
            stateTime += Gdx.graphics.getDeltaTime();
            entered=true;
            sprite.setRegion((TextureRegion)animation.getKeyFrame(stateTime));
        } else {
            if(entered){
                animation.setPlayMode(Animation.PlayMode.REVERSED);
                sprite.setRegion((TextureRegion)animation.getKeyFrame(stateTime));
            }

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
        System.out.println(text);
        Texture texture = game.getAssetManager().get(text);
        TextureRegion[][] thrustRegion = TextureRegion.split(
                texture,
                96,    // 3 columns
                32);   // 4 lines
        TextureRegion[] frames = new TextureRegion[11];

        int index=0;

        for(int i = 0; i < 4 ; i++){
            for(int j = 0 ; j < 3 ; j++){
                if(index==11)
                    break;
                frames[index++] =  thrustRegion[i][j];
            }
        }


        // 0.25 seconds per frame
        animation = new Animation<TextureRegion>(.10f, frames);
        animation.setPlayMode(Animation.PlayMode.NORMAL);                           // Can hide this
        //return new Sprite((Texture)game.getAssetManager().get(text));
        Sprite sprite = new Sprite((TextureRegion)animation.getKeyFrame(0));
        sprite.setSize(sprite.getWidth()/BATCH_CONST,sprite.getHeight()/BATCH_CONST);
        return sprite;
    }

}
