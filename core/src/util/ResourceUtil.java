package util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import java.util.Arrays;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ResourceUtil {

    public static HashMap<String, Texture> textures = new HashMap<String,Texture>();
    public static HashMap<String, TextureRegion> textureRegions = new HashMap<String,TextureRegion>();
    public static HashMap<String, Animation> animations = new HashMap<String, Animation>();

    /**
     * Load a texture.
     * @param texturePath Path of the texture
     * @param textureName Name used to retrieve the texture
     */
    public static void addNewTextureWithName(String texturePath, String textureName){
        if(textures.containsKey(texturePath)){
            System.out.println("Warning: Texture with name '" + textureName + "' has already been loaded!");
        }
        Texture texture = new Texture(texturePath);
        textures.put(textureName,texture);
    }

    /**
     * Load a texture. Name is the same as the path.
     * @param texturePath Path of the texture.
     */
    public static void addNewTexture(String texturePath){
        addNewTextureWithName(texturePath,texturePath);
    }

    /**
     * Load a region of a texture. Generally used to load parts of a tile map or character sheet.
     * @param texturePath The path of the texture
     * @param textureName Name used to retrieve the texture region
     * @param x X-position at which the region starts
     * @param y Y-position at which the region starts
     * @param width Width of the region
     * @param height Height of the region
     */
    public static void addNewTextureRegionWithName(String texturePath, String textureName, int x, int y, int width, int height){
        if(textures.containsKey(texturePath)){
            System.out.println("Warning: Texture with name '" + textureName + "' has already been loaded!");
        }
        Texture texture = new Texture(texturePath);
        TextureRegion textureRegion = new TextureRegion(texture, x, y, width, height);
        textureRegions.put(textureName,textureRegion);
    }

    /**
     * Load a region of a texture. Generally used to load parts of a tile map or character sheet.
     * @param texturePath The path of the texture
     * @param x X-position at which the region starts
     * @param y Y-position at which the region starts
     * @param width Width of the region
     * @param height Height of the region
     */
    public static void addNewTextureRegion(String texturePath, int x, int y, int width, int height){
        addNewTextureRegionWithName(texturePath,texturePath, x, y, width, height);
    }



    public static void addNewAnimation(Texture texture, String animName,
                                       float frameDuration, Vector2 colsAndRows, Animation.PlayMode playMode,
                                       Vector2 offset, int numFrames){

        Animation<TextureRegion> animation = textureToAnimation(texture,frameDuration,colsAndRows,offset,numFrames);
        animation.setPlayMode(playMode);
        animations.put(animName, animation);
    }


    public static void addNewAnimation(Texture texture, String animName, float frameDuration, Vector2 colsAndRows){
        Animation<TextureRegion> animation = textureToAnimation(texture,frameDuration,colsAndRows);
        animations.put(animName,animation);
    }

    public static void addNewAnimation(Texture texture, String animName, float frameDuration, Vector2 colsAndRows, Animation.PlayMode playMode){
        Animation<TextureRegion> animation = textureToAnimation(texture,frameDuration,colsAndRows);
        animation.setPlayMode(playMode);
        animations.put(animName,animation);
    }

    /**
     * Load textures and animations needed at runtime
     */
    public static void loadInitialTextures(){
        addNewTextureWithName("playerTexture.jpg", "playerTexture");
        addNewTextureWithName("rogue-walk.png","character-sheet");
        try {
            addNewAnimation(getTexture("character-sheet"),"rogue-walk",0.1f,
                    new Vector2(8,1), Animation.PlayMode.LOOP_PINGPONG);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieve a texture by its name (or path)
     * @param name name (or path) of the texture
     * @return A texture
     * @throws IOException If texture not loaded
     */
    public static Texture getTexture(String name) throws IOException {
        if(!textures.containsKey(name)){
            throw new IOException("Texture " + name + " has not been loaded!");
        }
        return textures.get(name);

    }

    public static Animation<TextureRegion> getAnimation(String name) throws IOException {
        if(!animations.containsKey(name)){
            throw new IOException("Animation " + name + " has not been loaded!");
        }
        return animations.get(name);
    }



    public static Animation<TextureRegion> textureToAnimation(Texture texture, float frameDuration, Vector2 colsAndRows,
                                                              Vector2 offset, int numFrames){
        int frameCols = (int)colsAndRows.x;
        int frameRows = (int)colsAndRows.y;

        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / frameCols,
                texture.getHeight() / frameRows);
        TextureRegion[] frames = new TextureRegion[numFrames];
        int index = 0;
        for (int i = (int)offset.x; i < frameRows; i++) {
            for (int j = (int)offset.y; j < frameCols; j++) {
                if(index < numFrames){
                    frames[index++] = tmp[i][j];
                }
            }
        }

        return new Animation<TextureRegion>(frameDuration, frames);
    }

    public static Animation<TextureRegion> textureToAnimation(Texture texture, float frameDuration, Vector2 colsAndRows){
        return textureToAnimation(texture,frameDuration,colsAndRows,new Vector2(0,0),(int)(colsAndRows.x * colsAndRows.y));
    }

}
