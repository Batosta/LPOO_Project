package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * A model representing the FireBoy.
 */
public class FireBoy extends Character {

    private static final float MOVE_SPEED = 10f;

    /**
     * Is the FireBoy jumping accelerating.
     */
        private boolean isjumping;

    /**
     * constructur of the FireBoy
     *
     * @param x the x Coordinate
     * @param y the y Coordinate
     */
    public FireBoy(float x, float y) {
        super(x, y);
    }

    public void moveRight(float delta) {
        this.setXY(this.getX() + delta * MOVE_SPEED, this.getY());
    }

    public void moveLeft(float delta) {
        this.setXY(this.getX() - delta * MOVE_SPEED, this.getY());
    }


    public void handleInputs(float delta) {
        boolean isjumping;
                if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
    {
        this.moveLeft(delta);
    }
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
    {
        this.moveRight(delta);
    }
                if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    this.jump(delta);
                }
    }

    public void jump(float delta){
       // isjumping=true;
    }

 //   public void move(){
   // }
}
