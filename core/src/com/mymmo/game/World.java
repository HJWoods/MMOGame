package com.mymmo.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import util.PerlinNoiseGenerator;
import util.ResourceUtil;

import java.util.HashMap;
import java.util.Random;

public class World {

    PerlinNoiseGenerator perlin;
    public static int WORLD_TILE_SIZE = 32;
    public static int WORLD_MAX_WIDTH = 10000;
    public static int WORLD_MAX_HEIGHT = 10000;
    public HashMap<Vector2, String> tileMap = new HashMap<Vector2, String>();
    /**
     * Constructor for World. Sets up perlin noise and pre-computes some values.
     * @param preComputedPerlinValues Number of values to precompute right from the start. Depending on <br>
     *                                specs of the device, it may be more or less efficient to compute more <br>
     *                                values. n^2 values are precomputed.
     */
    public World(int preComputedPerlinValues) {
        //TODO: when moving to an online version the seed must be retrieved from the server.
        
        Random r = new Random();
        
        perlin = new PerlinNoiseGenerator(2f, 100, 12, 1,
                1.3f, 0.66f, r.nextLong());

        loadTileTextures();

        for(int i = 0; i < preComputedPerlinValues; i++){
            for(int j = 0; j < preComputedPerlinValues; j++){
                loadTile(i,j);
            }
        }
    }

    /**
     * Retrieve the given tile based on the perlin noise function and load it into tileMap.
     * @param i x-position
     * @param j y-position
     */
    public void loadTile(int i, int j){
        float n = perlin.noise(i,j);
        Vector2 key = new Vector2(i,j);
        if (n > 1.25) {
            this.tileMap.put(key, "Rock");
        } else if (n > 0.95) {
            this.tileMap.put(key, "Grass");
        } else if (n > 0.5) {
            this.tileMap.put(key, "Grass");
        } else if (n > 0) {
            this.tileMap.put(key, "Dirt");
        }else{
            this.tileMap.put(key, "Water");
        }
    }

    /**
     * Loads a chunk of tiles starting at point (x,y) and continuing chunkSize on each axis
     * @param chunkSize The size of the chunk to be loaded (^2)
     * @param x The x-position of P(x,y) which is the upper-left corner of the chunk
     * @param y The y-position of P(x,y) which is the upper-left corner of the chunk
     */
    public void loadChunk(int chunkSize, int x, int y){
        for(int i = x; i < x+chunkSize; i++){
            for(int j = y; j < y+chunkSize; j++){
                loadTile(i,j);
            }
        }
    }


    /**
     * Loads textures to be used for the World. This function will need to be updated if you
     * change tilesets.
     */
    public void loadTileTextures(){
        //Grass
        ResourceUtil.addNewTextureRegionWithName("standard_tileset.png","Grass",0,64,WORLD_TILE_SIZE,WORLD_TILE_SIZE);
        //Dirt
        ResourceUtil.addNewTextureRegionWithName("standard_tileset.png","Dirt",64,64,WORLD_TILE_SIZE,WORLD_TILE_SIZE);
        //Rock
        ResourceUtil.addNewTextureRegionWithName("standard_tileset.png","Rock",96,64,WORLD_TILE_SIZE,WORLD_TILE_SIZE);
        //Water
        ResourceUtil.addNewTextureRegionWithName("standard_tileset.png","Water",32,64,WORLD_TILE_SIZE,WORLD_TILE_SIZE);
    }

    public String getTile(int x, int y) {
        return tileMap.get(new Vector2(x, y));
    }

    public TextureRegion getTileTexture(int x, int y){
        String textureName = getTile(x,y);
        return ResourceUtil.textureRegions.get(textureName);
    }


    /**
     * Draw the tile located at point P=(x,y).
     *
     * @param x The x position of the tile
     * @param y The y position of the tile
     * @param batch The SpriteBatch that the tile is to be drawn by
     * @see #getTile(int, int)
     */
    public void drawTile(int x, int y, SpriteBatch batch) {
        TextureRegion texture = this.getTileTexture(x, y);
        if(texture != null){
            batch.draw(texture, x * WORLD_TILE_SIZE, y * WORLD_TILE_SIZE, WORLD_TILE_SIZE, WORLD_TILE_SIZE);
        }

    }


    public void drawTilesInRange(Camera cam, SpriteBatch batch) {
        int numberOfTilesX = (int) (cam.viewportWidth / WORLD_TILE_SIZE);
        int numberOfTilesY = (int) (cam.viewportHeight / WORLD_TILE_SIZE);
        int cameraTilePosX = (int) (cam.position.x / WORLD_TILE_SIZE);
        int cameraTilePosY = (int) (cam.position.y / WORLD_TILE_SIZE);
        int minX = Math.max(1, cameraTilePosX - (numberOfTilesX));
        int minY = Math.max(1, cameraTilePosY - (numberOfTilesY));
        int maxX = cameraTilePosX + (numberOfTilesX);
        int maxY = cameraTilePosY + (numberOfTilesY);

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                this.drawTile(i, j, batch);
            }
        }
    }




}
