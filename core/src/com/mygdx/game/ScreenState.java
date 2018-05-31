package com.mygdx.game;

import com.badlogic.gdx.Screen;

/**
 * Possible states of Screen
 */
public enum ScreenState {

    /**
     * The screen that shows the Main Menu
     */
    MENU_SCREEN {
        /**
         * Function that returns the Main Menu screen
         *
         * @param params Several possible parameters
         *
         * @return The Main Menu Screen
         */
        public Screen getScreen(Object... params) {

            return new MainMenuScreen((FireBoyWaterGirl) params[0]);
        }
    },

    /**
     * The screen that shows the game itself
     */
    GAME_SCREEN {
        /**
         * Function that returns the Game screen
         *
         * @param params Several possible parameters
         *
         * @return The Game Screen
         */
        public Screen getScreen(Object... params) {

            return new GameScreen((FireBoyWaterGirl) params[0]);
        }
    };

    /**
     * Function that returns the current screen
     *
     * @param params Several possible parameters
     *
     * @return The current creen
     */
    public abstract Screen getScreen(Object... params);
}