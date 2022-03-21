package com.mymmo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import util.GameCamera;
import util.TCPNetworkRequests;
import util.ResourceUtil;
import util.UDPNetworkRequests;

public class GameScreen implements Screen {

    SpriteBatch batch;
    GameCamera gameCamera;
    Player player;
    World world;


    @Override
    public void show () {

        byte[] byteData = {0b1111, 0b0101, 0b01010101};






        //TCPNetworkRequests.createNewSocket("standard","localhost",65432);
        UDPNetworkRequests UDP = new UDPNetworkRequests();
        String a = UDP.sendEcho("localhost",4545,"hiya!!!");
        System.out.println(a);


        batch = new SpriteBatch();
        gameCamera = new GameCamera(750f,500f, world);
        ResourceUtil.loadInitialTextures();
        player = new Player(new Vector2(500,500), 50, 50);
        EntityHandler.addEntity(player);
        world = new World(500);


    }


    public void update(float delta){
        EntityHandler.updateEntities(delta);
        //TCPNetworkRequests.sendDataToSocket("standard","hey");
        //byte[] bytes = TCPNetworkRequests.readSocketData("standard",1);
    }

    @Override
    public void render (float delta) {

        //INPUT
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            world = new World(500);
        }



        //Call update first to process game logic
        update(delta);
        ScreenUtils.clear(1, 0, 0, 1);
        //CAMERA
        //(make sure to update position, rotation e.t.c BEFORE drawing sprites)




        //Lerp the camera so that the player is in the centre of the screen
        Vector2 playerPos = player.getPosition();
        gameCamera.lerpCamera(new Vector2(playerPos.x+ player.getWidth()/2, playerPos.y + player.getHeight()/2),0.1f,delta);


        //Do not remove this line:
        gameCamera.update();

        //BEGIN SPRITEBATCH
        batch.setProjectionMatrix(gameCamera.camera.combined);
        batch.begin();



        //WORLD TILES
        world.drawTilesInRange(gameCamera.camera,batch);



        //ENTITIES

        EntityHandler.drawEntities(batch, delta);


        //END SPRITEBATCH
        batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
