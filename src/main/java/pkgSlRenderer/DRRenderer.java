/**
 * Name: Dev Rangoonwala
 * Date: 11/27/24
 * Class: csc133 - 08
 * Description: This DRRenderer class is just an abstract class where the subclass or child classes can extend off of this renderer class
 */

package pkgSlRenderer;

import pkgDriver.DRSpot;
import pkgSlUtils.DRWindowManager;

import java.util.Random;

public abstract class DRRenderer {
    //global
    protected Random my_rand = new Random();
    protected static DRWindowManager my_wm;
    protected int polygonVertices = 4;
    private final int polygonMaxVertice = 20;
    protected boolean KeepRunning = true;

    //drawPolygons
    protected final float z = 0.0f;

    //Render
    protected final float width = DRSpot.WIN_WIDTH;
    protected final float height = DRSpot.WIN_HEIGHT;
    private final float startingPoint = -1.0f;

    protected static DRShaderObject shaderObj;


    public int getPolygonMaxVertice() {
        return polygonMaxVertice;
    }

    public float getStartingPoint() {
        return startingPoint;
    }



    public abstract void initOpenGL(DRWindowManager my_wm);

    public abstract void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS);

    public abstract void render(float radius);

    public abstract void render();




}
