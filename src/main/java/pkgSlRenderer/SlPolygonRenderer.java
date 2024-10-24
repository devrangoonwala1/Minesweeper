/**
 * Name: Dev Rangoonwala
 * Date: 10/8/24
 * Class: csc133 - 08
 * Description: The slRenderEngine class helps with creating the circle that is then displayed on the screen, this is where the generateCircleSegmentVerties help make the triangle which can be created into a circle, the updateRandVertices all it does is help with generating the random coordinates and the random color of the circle, the render method is where pretty much everything is called out to make the program work.
 */

package pkgSlRenderer;

import pkgSlUtils.SlWindowManager;

import java.util.Random;

import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;


public class SlPolygonRenderer extends SlRenderer{

    private final int NUM_3D_COORDS = 3;
    private final int MAX_CIRCLES = 100;
    private final int NUM_RGBA = 4;
    private final float C_RADIUS = 0.05f;
    private final int UPDATE_INTERVAL = 30;

    private float[][] rand_coords;
    private float[][] rand_colors;

    Random my_rand = new Random();
    private static SlWindowManager my_wm;

    public void initOpenGL(SlWindowManager my_wm) {
        this.my_wm = my_wm;
    }


    /*
    private static void generateCircleSegmentVertices(float[] center, float start, float[] vertex, float C_RADIUS, float finish, int numberOfTriangles){
        int index = 0;
        int i = 0;
        while (i < numberOfTriangles){
            vertex[index++] = center[0];
            vertex[index++] = center[1];
            vertex[index++] = center[2];

            float x1 = center[0] + C_RADIUS * (float) cos(start + i * finish);
            float y1 = center[1] + C_RADIUS * (float) sin(start + i * finish);

            vertex[index++] = x1;
            vertex[index++] = y1;
            vertex[index++] = center[2];

            float x2 = center[0] + C_RADIUS * (float) cos(start + (i+1) * finish);
            float y2 = center[1] + C_RADIUS * (float) sin(start + (i+1) * finish);

            vertex[index++] = x2;
            vertex[index++] = y2;
            vertex[index++] = center[2];

            i++;
        }

    }



    private void updateRandVertices() {
        rand_coords = new float[MAX_CIRCLES][NUM_3D_COORDS];
        rand_colors = new float[MAX_CIRCLES][NUM_RGBA];
        float circleWindowArea = 1.0f - C_RADIUS;
        int x = 0;

        while (x < MAX_CIRCLES){
            float[] coordinates = rand_coords[x];
            float xCoordinate = (2 * (my_rand.nextFloat()) - (1.0f)) * (circleWindowArea);
            float yCoordinate = (2 * (my_rand.nextFloat()) - (1.0f)) * (circleWindowArea);
            float zCoordinate = 0.0f;
            coordinates[0] = xCoordinate;
            coordinates[1] = yCoordinate;
            coordinates[2] = zCoordinate;
            int y = 0;
            while (y < NUM_RGBA){
                rand_colors[x][y] = my_rand.nextFloat();
                y++;
            }
            x++;

        }

    }
    */



    @Override
    public void render(){
        final int frame = 500;
        final int row = 20;
        final int col = 20;
        render(frame, row, col);
    }

    @Override
    public void render(float radius){

        int[] sizeOfWindow = my_wm.getWindowSize();
        int width = sizeOfWindow[0];
        int height = sizeOfWindow[1];

        int row = (int)(height/(2*radius));
        int col = (int)(width/(2*radius));
    }


    private void drawPolygon(float rows, float cols, float C_RADIUS, int polygonVertices) {

        glBegin(GL_TRIANGLES);

        for (int i = 0; i < polygonVertices; i++) {

            double vertex = 2 * Math.PI * i / polygonVertices;
            glVertex3d(-0.955f + ((Math.cos(vertex) * C_RADIUS) + (cols * 2 * C_RADIUS)),
                    -0.955f + ((Math.sin(vertex) * C_RADIUS) + (rows * 2 * C_RADIUS)),
                    0);
        }

    }

    @Override
    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS){
        //updateRandVertices();

        final float begin_angle = 0.0f, end_angle = (float) (2.0f * PI);
        float increase = (end_angle - begin_angle) / polygonVertices;
        float[] vertex = new float[polygonVertices * NUM_3D_COORDS * NUM_3D_COORDS];
        int counter = 0;
        do{
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLES);

            float x = my_rand.nextFloat(2.0f) + 0.3f;
            float y = my_rand.nextFloat(2.0f) + 0.3f;
            float z = my_rand.nextFloat(2.0f) + 0.3f;
            float g = my_rand.nextFloat(2.0f) + 0.3f;


            for (int row = 0; row < NUM_ROWS; row++) {
                for (int col = 0; col < NUM_COLS; col++) {

                    glColor4f(x, y, z, g);
                    drawPolygon(row, col, C_RADIUS, polygonVertices);
                }
            }

            polygonVertices++;
            if(polygonVertices > polygonMaxVertice){
                polygonVertices = 3;
            }

            if (FRAME_DELAY > 0) {
                try {
                    Thread.sleep(FRAME_DELAY);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            /*
            for (int x = 0; x < MAX_CIRCLES; x++) {
                float[] circleColors = rand_colors[x];
                glColor4f(circleColors[0], circleColors[1], circleColors[2], circleColors[3]);
                generateCircleSegmentVertices(rand_coords[x], begin_angle, vertex, C_RADIUS, increase, polygonVertices);

                for(int y = 0; y < vertex.length; y+=3) {
                    glVertex3f(vertex[y], vertex[y+1], vertex[y+2]);
                }

            }


            polygonVertices++;

            if(polygonVertices > getPolygonMaxVertice()){
                polygonVertices = 3;
            }

            if (FRAME_DELAY > 0) {
                try {
                    Thread.sleep(FRAME_DELAY);
                    updateRandVertices();

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

             */


            glEnd();

            my_wm.swapBuffers();

        } while (!my_wm.isGlfwWindowClosed());
        my_wm.destroyGlfwWindow();

    }


}

