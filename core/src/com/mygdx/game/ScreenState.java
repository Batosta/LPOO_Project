package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public enum ScreenState {

    MENU_SCREEN {
        public Screen getScreen(Object... params) {
            return new MainMenuScreen((FireBoyWaterGirl) params[0]);
        }
    },

    GAME_SCREEN {
        public Screen getScreen(Object... params) {
            return new GameScreen((FireBoyWaterGirl) params[0]);
        }
    }/*,

    GAMEOVER_SCREEN {
        public Screen getScreen(Object... params) {
            return new GameScreen((Integer) params[0]);
        }
    }*/;

    public abstract Screen getScreen(Object... params);
}