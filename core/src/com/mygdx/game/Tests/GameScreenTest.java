package com.mygdx.game.Tests;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FireBoyWaterGirl;
import com.mygdx.game.GameScreen;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GameScreenTest{

    @Test
    private void testDead() {
        GameScreen game = new GameScreen(new FireBoyWaterGirl());


        game.addLevel("testmap.tmx");        //in assets (map for test)
        game.setCurrentLevel(3);                       //game comes already with 3 levels.

//        game.getCurrentLevel().getfireboy2D().getB2body().applyLinearImpulse(new Vector2());
    }
}
