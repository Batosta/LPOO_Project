package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.mygdx.game.Character.Moving.STAND;

/**
 * A model representing the WaterGirl.
 */
public class WaterGirl extends Character {

    /**
     * Constructor of the WaterGirl
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public WaterGirl(float x, float y){

        super(x, y);
    }

    public void handleInputs(float delta) {

        moving=STAND;

        if(Gdx.input.isKeyPressed(Input.Keys.A))
        {
            moveLeft(delta);
            // if(!getGrounded())
            //   moving=JUMPLEFT;
        }else  if(Gdx.input.isKeyPressed(Input.Keys.D))
        {
            moveRight(delta);
        }

        if((Gdx.input.isKeyJustPressed(Input.Keys.W) && getGrounded()) || !getGrounded()) {
            jump(delta);
            // if(jumpstate != DESCENDING)
            //   jumpstate=ASCENDING;
            //setGrounded(false);
        }
    }

    public void update(float delta) {
        //if(moving == LEFT || moving == JUMPLEFT)
        //  moveLeft(delta);
        //if(moving == RIGHT)
        //  moveRight(delta);
        //if(!getGrounded() || moving == JUMPLEFT){
        //   jump(delta);
        // }
    }

    //   public void move(){
    // }
}