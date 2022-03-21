package com.mymmo.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Entity {

    public Vector2 getPosition();

    public void setPosition(Vector2 position);

    public void move(float x, float y);

    public void rotate(float angle);

    public float getAngle();


    public void setTexture(TextureRegion texture);

    public TextureRegion getTexture();

    public void updateAnimationState(float delta);

    public void update(float delta);

    public float getWidth();

    float getHeight();
}
