package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import static com.mygdx.game.Character.Moving.*;
import static com.mygdx.game.FireBoy.Jump.ASCENDING;
import static com.mygdx.game.FireBoy.Jump.DESCENDING;
import static com.mygdx.game.FireBoy.Jump.STOP;


/**
 * A model representing the FireBoy.
 */
public class FireBoy extends Character {

    private static final float MOVE_SPEED = 10f;
    private float JUMP_SPEED = 10f;

    public enum Jump {
        ASCENDING, DESCENDING, STOP
    }

    Jump jumpstate= STOP;

    /**
     * FireBoy jumping direction.
     */
        private boolean isascending;

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

    public void jump(float delta){

        if(jumpstate == ASCENDING) {       //está a subir
            this.setXY(this.getX(), this.getY() + delta * JUMP_SPEED);
            JUMP_SPEED-=0.5;
            if(JUMP_SPEED<=0){
                jumpstate=DESCENDING;
                JUMP_SPEED=0;
            }
        } else
            if(jumpstate == DESCENDING){       //essta a descer
                this.setXY(this.getX(), this.getY() - delta * JUMP_SPEED);
                JUMP_SPEED+=0.5;
                if(JUMP_SPEED > 10f){
                    jumpstate=STOP;
                    moving=STAND;
                }
            }

    }


    public void handleInputs(float delta) {
        if(moving!=JUMP)
        moving=STAND;
                if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
                {
                    if(moving==JUMP)
                        moving=JUMPLEFT;
                    else moving=LEFT;
    }
                if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
    {
        moving=RIGHT;
    }
                if(Gdx.input.isKeyJustPressed(Input.Keys.UP) && moving!=JUMP) {
                    if(jumpstate != DESCENDING)
                    jumpstate=ASCENDING;
                    moving=JUMP;
                }
    }

    public Moving getMoving(){
        return this.moving;
    }

    public void update(float delta) {
        if(moving == LEFT || moving == JUMPLEFT)
            moveLeft(delta);
        if(moving == RIGHT)
            moveRight(delta);
        if(moving == JUMP || moving == JUMPLEFT){
            jump(delta);
        }
    }

    //   public void move(){
   // }
}
