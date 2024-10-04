package pkgSlRenderer;

import org.lwjgl.opengl.GL;
import pkgSlUtils.slWindowManager;

import java.util.Random;

import static java.lang.Math.PI;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;


public class slRenderEngine {
    public int NUM_3D_COORDS = 3;
    public int MAX_CIRCLES = 100;
    public int TRAINGLES_PER_CIRCLE = 40;
    public int NUM_RGBA = 4;
    public float C_RADIUS = 0.05f;
    public int UPDATE_INTERVAL = 30;

    public float[][] rand_coords;
    public float[][] rand_colors;

    Random my_rand = new Random();
    public static slWindowManager my_wm;

    public void initOpenGL(slWindowManager my_wm) {
        //this.my_wm = my_wm;

    }


    /*
    public static void generateCircleSegmentVertices(float[], float, float[], float, float, int){

    }
     */


    private void updateRandVertices() {

    }

    public void render(){

    }


}

