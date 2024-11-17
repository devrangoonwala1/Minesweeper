/**
 * Name: Dev Rangoonwala
 * Date: 10/25/24
 * Class: csc133 - 08
 * Description: This DRPolygonRenderer class is an extension of the DRRenderer class which has the drawPolygon method which draws the necessary shape of the polygon and the renderer
 */

package pkgSlRenderer;
import pkgBackendEngine.pingPongClassDR;
import pkgSlUtils.DRWindowManager;


import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;



public class DRPolygonRenderer extends DRRenderer {

    public void initOpenGL(DRWindowManager my_wm) {
        this.my_wm = my_wm;
    }


    private void drawPolygon(float x, float y, float radius, int polygonVertices) {

        float xCenter = x * radius;
        float yCenter = y * radius;
        float delTheta = 2.0f * (float) Math.PI / polygonVertices;
        float theta = 0.0f;


        glBegin(GL_TRIANGLE_FAN);

        int i=0;
        while (i < polygonVertices){
            float radiusScalingX = radius * (float)Math.cos(theta + Math.PI/4);
            float radiusScalingY = radius * (float)Math.sin(theta + Math.PI/4);
            glVertex3f((x + (radiusScalingX + (xCenter))),
                    (y + (radiusScalingY + (yCenter))),
                    z);
            theta = delTheta + theta;
            i++;
        }
        glEnd();

    }


    public void render(){
        int delay = 500;
        int rows = 30;
        int cols = 30;
        render(delay, rows, cols);
    }


    public void render(float radius){
        int delay = 500;
        int rows = (int)(height / (radius * height));
        int cols = (int)(width / (radius * width));
        render(delay, rows, cols);
    }


    @Override
    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS){
        boolean[][] live = new boolean[NUM_ROWS][NUM_COLS];
        boolean[][] next = new boolean[NUM_ROWS][NUM_COLS];
        int[][] temp;





        float radius = 1.0f/Math.max(NUM_ROWS, NUM_COLS);

        do{
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLE_FAN);

            float color1 = my_rand.nextFloat(2.0f) + 0.3f;
            float color2 = my_rand.nextFloat(2.0f) + 0.3f;
            float color3 = my_rand.nextFloat(2.0f) + 0.3f;
            float color4 = my_rand.nextFloat(1.0f) + 0.3f;

            Random random = new Random();
            for (int i = 0; i < NUM_ROWS; i++) {
                System.out.println();
                for (int j = 0; j < NUM_COLS; j++) {
                    live[i][j] = random.nextBoolean();
                    //System.out.print(live[i][j] + " ");
                }
            }


            int row = 0;
            while (row < NUM_ROWS){
                int col = 0;
                while (col < NUM_COLS) {
                    float centeringRow = (2.0f / (NUM_ROWS + 1)) * (row + 1);
                    float centeringCol = (2.0f / (NUM_COLS + 1)) * (col + 1);
                    float spacingX = getStartingPoint() + centeringCol;
                    float spacingY = getStartingPoint() + centeringRow;

                    if (live[row][col] == true) {
                        glColor4f(0, 1, 0, 1);
                    }
                    else{
                        //glColor4f(color1, color2, color3, color4);
                        glColor4f(1, 0, 0, 1);
                    }
                    drawPolygon(spacingX, spacingY, radius, polygonVertices);
                    col++;
                }
                row++;
            }




            /*
            polygonVertices++;
            if(polygonVertices > getPolygonMaxVertice()){
                polygonVertices = 3;
            }
             */


            if (FRAME_DELAY > 0) {
                try {
                    Thread.sleep(FRAME_DELAY);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }



            my_wm.swapBuffers();

        } while (!my_wm.isGlfwWindowClosed());
        my_wm.destroyGlfwWindow();

    }

}

