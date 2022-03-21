package com.mymmo.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class EntityHandler {

    public static List<Entity> entities = new ArrayList<Entity>();

    public static void addEntity(Entity e){
        entities.add(e);
    }

    public static void updateEntities(float delta){
        for(Entity e : entities){
            e.update(delta);
        }
    }

    public static void drawEntities(SpriteBatch batch, float delta){
        for(Entity e : entities){
            Vector2 pos = e.getPosition();
            e.updateAnimationState(delta);
            batch.draw(e.getTexture(), pos.x, pos.y,e.getWidth(),e.getHeight());
        }
    }

}
