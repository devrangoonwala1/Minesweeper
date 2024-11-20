/**
 * Name: Dev Rangoonwala
 * Date: 10/25/24
 * Class: csc133 - 08
 * Description: This DRPolygonRenderer class is an extension of the DRRenderer class which has the drawPolygon method which draws the necessary shape of the polygon and the renderer
 */

package pkgSlRenderer;
import pkgSlUtils.DRWindowManager;
import pkgSlUtils.SlKeyListener;


import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;



public class DRPolygonRenderer extends DRRenderer {
    private boolean[][] live;
    private boolean[][] next;
    private boolean[][] temp;

    private int numberOfRows;
    private int numberOfCols;

    boolean KeepRunning = true;

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
        live = new boolean[NUM_ROWS][NUM_COLS];
        next = new boolean[NUM_ROWS][NUM_COLS];


        numberOfRows = NUM_ROWS;
        numberOfCols = NUM_COLS;


        Random random = new Random();
        for (int i = 0; i < NUM_ROWS; i++) {
            System.out.println();
            for (int j = 0; j < NUM_COLS; j++) {
                live[i][j] = random.nextBoolean();
            }
        }


        float radius = 1.0f/Math.max(NUM_ROWS, NUM_COLS);

        do{
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);
            glBegin(GL_TRIANGLE_FAN);


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
                        glColor4f(1, 0, 0, 1);
                    }
                    drawPolygon(spacingX, spacingY, radius, polygonVertices);
                    col++;
                }
                row++;
            }


            liveArray(NUM_ROWS, NUM_COLS);


            if (FRAME_DELAY > 0) {
                try {
                    Thread.sleep(FRAME_DELAY);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            else {
                KeepRunning = false;
            }


            if (SlKeyListener.isKeyPressed(GLFW_KEY_D) ) {
                KeepRunning = false;
                if (FRAME_DELAY > 0){
                    FRAME_DELAY -= 500;
                    if (FRAME_DELAY < 0){
                        FRAME_DELAY = 0;
                    }
                }
                else if(FRAME_DELAY < 0 || FRAME_DELAY == 0){
                    FRAME_DELAY = 0;
                }
                System.out.println("+++ Frame delay is now: " + FRAME_DELAY + " ms!");
                KeepRunning = true;
                SlKeyListener.resetKeypressEvent(GLFW_KEY_D);
                SlKeyListener.resetKeypressEvent(GLFW_KEY_LEFT_SHIFT);
            }
            else if (SlKeyListener.isKeyPressed(GLFW_KEY_I) ) {
                KeepRunning = false;
                FRAME_DELAY += 500;
                System.out.println("+++ Frame delay is now: " + FRAME_DELAY + " ms!");
                KeepRunning = true;
                SlKeyListener.resetKeypressEvent(GLFW_KEY_I);
                SlKeyListener.resetKeypressEvent(GLFW_KEY_LEFT_SHIFT);
            }
            else if (SlKeyListener.isKeyPressed(GLFW_KEY_R) ) {
                KeepRunning = false;
                boardReset(NUM_ROWS, NUM_COLS);
                System.out.println("Board Reset");
                KeepRunning = true;
                SlKeyListener.resetKeypressEvent(GLFW_KEY_R);
                SlKeyListener.resetKeypressEvent(GLFW_KEY_LEFT_SHIFT);
            }


            my_wm.swapBuffers();

        } while (!my_wm.isGlfwWindowClosed());
        my_wm.destroyGlfwWindow();

    }



    public void liveArray(int NUM_ROWS, int NUM_COLS){
        int liveNeighbor;
        for (int rows = 0; rows < NUM_ROWS; rows++){
            for (int cols = 0; cols < NUM_COLS; cols++){
                liveNeighbor = countNeightbors(rows, cols);

                if (live[rows][cols] == true) {
                    next[rows][cols] = (liveNeighbor == 2 || liveNeighbor == 3);
                }
                else{
                    next[rows][cols] = (liveNeighbor == 3);
                }
            }
        }
        swap();
    }


    public int countNeightbors(int row, int col){
        int count = 0;
        int x = -1;
        int nextRow = 1;
        while (x <= nextRow){
            int y = -1;
            int nextCol = 1;
            while (y <= nextCol){
                if (!(x == 0 && y == 0)){
                    int neighborRow = row + x;
                    int neighborCol = col + y;
                    if (neighborRow >= 0 && neighborRow < numberOfRows && neighborCol >= 0 && neighborCol < numberOfCols) {
                        if (live[neighborRow][neighborCol]){
                            count++;
                        }
                    }
                }
                y++;
            }
            x++;
        }
        return count;
    }



    public void swap(){
        temp = live;
        live = next;
        next = temp;
    }


    public void boardReset(int rows, int cols){
        int row = 0;
        while (row < rows) {
            int col = 0;
            while (col < cols){
                live[row][col] = my_rand.nextBoolean();
                col++;
            }
            row++;
        }
    }




}

