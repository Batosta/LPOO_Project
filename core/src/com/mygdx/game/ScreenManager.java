package com.mygdx.game;

import com.badlogic.gdx.Screen;

public class ScreenManager {

    private static ScreenManager instance;

    private FireBoyWaterGirl game;

    private ScreenState currentScreen;

    /**
     * Constructor of the screen manager.
     *
     */
    public ScreenManager(){
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(FireBoyWaterGirl game){
        this.game=game;
    }

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
