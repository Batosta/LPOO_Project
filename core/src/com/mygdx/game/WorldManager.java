package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public final class WorldManager {

    public static World world;

    private WorldManager() {}

    public static void clearWorld() {
        if (world != null) {
            world.dispose();
        }

        world = new World(new Vector2(0, 0), true);
    }
}