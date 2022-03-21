package util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mymmo.game.World;

public class GameCamera {

    public OrthographicCamera camera;
    private World world;

    /**
     * Constructor
     * @param cameraWidth Width of the Orthographic Camera viewport
     * @param cameraHeight Height of the Orthographic Camera viewport
     */
    public GameCamera(float cameraWidth, float cameraHeight, World world){
        // OrthographicCamera automatically scales the screen for you!
        //for more info: https://stackoverflow.com/questions/22312803/libgdx-get-screen-width-and-height-for-2d-games
        camera = new OrthographicCamera(cameraWidth,cameraHeight);
        this.world = world;
    }

    /**
     * Move by a vector
     * @param v a vector
     */
    public void move(Vector2 v){
        camera.translate(v);
    }


    /**
     * Move by x and y values
     * @param x x value
     * @param y y value
     */
    public void move(int x, int y){
        move(new Vector2(x,y));
    }

    /**
     * Update the Camera
     */
    public void update(){
        camera.update();
    }


    /**
     * Set the camera position by two floats
     * @param x the x-position
     * @param y the y-position
     */
    public void setPosition(int x, int y){
        camera.position.set(x,y,0);
    }

    /**
     * Set the camera position by a vector
     * @param v a vector
     */
    public void setPosition(Vector2 v){
        camera.position.set(v, 0);
    }

    public void setPosition(Vector3 v){ camera.position.set(v);}

    /**
     * Get the camera position
     * @return Camera position
     */
    public Vector3 getPosition(){
        return camera.position;
    }

    /**
     * Rotate the camera by an angle
     * @param theta
     */
    public void rotateCamera(float theta){
        //Note that libgdx uses 'infinite degrees', i.e angles >360 are perfectly valid!
        camera.rotate(theta);
    }

    /**
     * Get the camera rotation
     * @return The camera rotation as an angle in degrees
     */
    public float getRotation() {
        //Thanks to: https://stackoverflow.com/questions/29379960/libgdx-how-to-return-the-camera-rotation
        float camAngle = -(float)Math.atan2(camera.up.x, camera.up.y) * MathUtils.radiansToDegrees + 180;
        return camAngle;
    }

    public void lerpCamera(Vector2 target, float speed, float delta) {
        delta *= 60; // standardize for 60fps
        Vector3 camPos = camera.position;

        camPos.x = camera.position.x + (target.x - camera.position.x) * speed * delta;
        camPos.y = camera.position.y + (target.y - camera.position.y) * speed * delta;

        /* Confine the camera to the bounds of the map */

        float widthMaxLimit = World.WORLD_MAX_WIDTH * World.WORLD_TILE_SIZE - camera.viewportWidth / 2;
        float heightMaxLimit = World.WORLD_MAX_HEIGHT * World.WORLD_TILE_SIZE
                - camera.viewportHeight / 2;
        float widthMinLimit = camera.viewportWidth / 2 + World.WORLD_TILE_SIZE;
        float heightMinLimit = camera.viewportHeight / 2 + World.WORLD_TILE_SIZE;

        if (camPos.x < widthMinLimit) camPos.x = widthMinLimit;
        if (camPos.x > widthMaxLimit) camPos.x = widthMaxLimit;
        if (camPos.y < heightMinLimit) camPos.y = heightMinLimit;
        if (camPos.y > heightMaxLimit) camPos.y = heightMaxLimit;
        setPosition(camPos);
        //camera.update();
    }



}
