package com.mymmo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import util.ResourceUtil;

import java.io.IOException;

public class Player implements Entity {

    TextureRegion texture;
    Animation<TextureRegion> walkAnimation;
    Vector2 position = new Vector2(0,0);
    int width;
    int walkSpeed = 100;
    int height;
    float rotation = 0;
    float animStateTime = 0;
    String animationState = "Walking"; //TODO: Replace with Enum


    public Player(Vector2 pos, int width, int height) {
        this.width = width;
        this.height = height;
        position = pos;
        retrieveAnimations();
    }

    public void retrieveAnimations(){
        try {
            walkAnimation = ResourceUtil.getAnimation("rogue-walk");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(float delta){
        float deltaX = 0;
        float deltaY = 0;

        boolean walking = false;

        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            deltaY = deltaY + 1;
            walking = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            deltaY = deltaY - 1;
            walking = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            deltaX = deltaX - 1;
            walking = true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            deltaX = deltaX + 1;
            walking = true;
        }

        if(animationState == "Idle" && walking){
            changeAnimationState("Walking");
        }else if(animationState == "Walking" && !walking){
            changeAnimationState("Idle");
        }

        move(deltaX*delta*walkSpeed,deltaY*delta*walkSpeed);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public TextureRegion getTexture(){
        return texture;
    }

    public void changeAnimationState(String newState){
        animationState = newState;
        animStateTime = 0;
    }

    @Override
    public void updateAnimationState(float delta) {
        if(animationState == "Idle"){
            TextureRegion frame = walkAnimation.getKeyFrames()[0]; //TODO: Add idle animation
            setTexture(frame);
        }else if(animationState == "Walking"){
            TextureRegion frame = walkAnimation.getKeyFrame(animStateTime,true);
            setTexture(frame);
        }
        animStateTime = animStateTime + delta;
    }

    @Override
    public Vector2 getPosition(){
        return position;
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position = position;
    }


    public void setPosition(float x, float y){
        setPosition(new Vector2(x,y));
    }

    @Override
    public void move(float x, float y){
        setPosition(position.x+x,position.y+y);
    }

    @Override
    public void rotate(float angle){

    }

    public void setTexture(TextureRegion texture){
        this.texture = texture;
    }

    @Override
    public float getAngle(){
        return rotation;
    }

}
