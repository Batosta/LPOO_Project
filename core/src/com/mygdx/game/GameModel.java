package com.mygdx.game;

/**
 * The model representing the game.
 */
public class GameModel {

    /**
     * The fire character controlled by one of the users.
     */
    private FireBoy fireboy;

    /**
     * The water character controlled by one of the users.
     */
    private WaterGirl watergirl;

    public GameModel(float fireX, float fireY, float waterX, float waterY){

        fireboy = new FireBoy(fireX, fireY);
        watergirl = new WaterGirl(waterX, waterY);
    }

    /**
     * Return the player FireBoy.
     *
     * @return the fireboy.
     */
    public FireBoy getFireBoy(){

        return fireboy;
    }

    /**
     * Return the player WaterGirl.
     *
     * @return the watergirl.
     */
    public WaterGirl getWaterGirl(){

        return watergirl;
    }
}
