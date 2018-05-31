package com.mygdx.game;

import com.badlogic.gdx.Screen;

/**
 * Class that controls which Screen should be being used at each moment
 */
public class ScreenManager {

    /**
     * The instance of the ScreenManager
     */
    private static ScreenManager instance;

    /**
     * The game itself
     */
    private FireBoyWaterGirl game;

    /**
     * Constructor of the screen manager
     */
    public ScreenManager(){
    }

    /**
     * Function that returns the currents instance of the ScreenManager
     *
     * @return instance of the ScreenManager
     */
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    /**
     * Initializes the whole game
     *
     * @param game The game itself
     */
    public void initialize(FireBoyWaterGirl game){

        this.game = game;
    }

    /**
     * Functions that shows the current screen
     *
     * @param screenState The current screen state
     * @param params All the other parameters
     */
    public void showScreen(ScreenState screenState, Object... params) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        Screen newScreen = screenState.getScreen(params);
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
