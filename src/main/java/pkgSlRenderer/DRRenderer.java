package pkgSlRenderer;

import pkgDriver.SlSpot;
import pkgDriver.SlSpot.*;
import pkgSlUtils.SlWindowManager;
import java.util.Random;

public abstract class SlRenderer {
    //global
    protected Random my_rand = new Random();
    protected static SlWindowManager my_wm;
    protected int polygonVertices = 3;
    private final int polygonMaxVertice = 20;

    //drawPolygons
    protected final float z = 0.0f;

    //Render
    protected float width = SlSpot.WIN_WIDTH;
    protected float height = SlSpot.WIN_HEIGHT;
    private final float startingPoint = -1.0f;


    public int getPolygonMaxVertice() {
        return polygonMaxVertice;
    }

    public float getStartingPoint() {
        return startingPoint;
    }

    public void setPolygonVertices(int polygonVertices) {
        this.polygonVertices = polygonVertices;
    }



    public abstract void initOpenGL(SlWindowManager my_wm);

    public abstract void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS);

    public abstract void render(float radius);

    public abstract void render();




}
