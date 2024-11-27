/**
 * Name: Dev Rangoonwala
 * Date: 11/27/24
 * Class: csc133 - 08
 * Description: This DRPolygonRenderer class is an extension of the DRRenderer class which has the renderTile method which is used to make the necessary shape of the two triangles which then is a square. The render method uses the programmable pipeline to then draw a square and with the use of the shaders file which include the fragment shader and vertex shader it is able to set the colors inside the squares.
 */

package pkgSlRenderer;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgSlUtils.DRWindowManager;
import pkgSlUtils.SlKeyListener;


import java.nio.FloatBuffer;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11C.glClear;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;


public class DRPolygonRenderer extends DRRenderer {


    public void initOpenGL(DRWindowManager my_wm) {
        this.my_wm = my_wm;
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



    private float[] renderTiles(int NUM_ROWS, int NUM_COLS, float spacing){
        int square;
        int vertex;
        float[] vertices;
        square = NUM_ROWS * NUM_COLS;
        vertex = square * 6;
        vertices = new float[vertex * 3];

        float widthOfTileMax;
        float heightOfTileMax;
        widthOfTileMax = 2.0f / NUM_COLS;
        heightOfTileMax = 2.0f / NUM_ROWS;

        float spaceRows;
        float spaceCols;
        spaceRows = widthOfTileMax * spacing;
        spaceCols = heightOfTileMax * spacing;

        float widthTiles;
        float heightTiles;
        widthTiles = widthOfTileMax - spaceRows;
        heightTiles = heightOfTileMax - spaceCols;


        int index = 0;
        float x0, x1, x2, x3;
        float y0, y1, y2, y3;
        int i = 0;
        while(i < NUM_ROWS){
            int j = 0;
            while(j < NUM_COLS){
                x0 = -1.0f + j * widthOfTileMax + spaceRows / 2.0f;
                y0 = -1.0f + i * heightOfTileMax + spaceCols / 2.0f;
                x1 = x0;
                y1 = y0 + heightTiles;
                x2 = x0 + widthTiles;
                y2 = y1;
                x3 = x2;
                y3 = y0;

                //Triangle 1:
                vertices[index++] = x0;
                vertices[index++] = y0;
                vertices[index++] = 0.0f;

                vertices[index++] = x1;
                vertices[index++] = y1;
                vertices[index++] = 0.0f;

                vertices[index++] = x2;
                vertices[index++] = y2;
                vertices[index++] = 0.0f;

                //Triangle 2:
                vertices[index++] = x2;
                vertices[index++] = y2;
                vertices[index++] = 0.0f;

                vertices[index++] = x3;
                vertices[index++] = y3;
                vertices[index++] = 0.0f;

                vertices[index++] = x0;
                vertices[index++] = y0;
                vertices[index++] = 0.0f;
                j++;
            }
            i++;
        }
        return vertices;

    }




    @Override
    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS){
        float space = 0.2f;
        float[] vertices = renderTiles(NUM_ROWS, NUM_COLS, space);


        FloatBuffer vertex = BufferUtils.createFloatBuffer(vertices.length);
        vertex.put(vertices).flip();

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3*Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);



        SlCamera camera = new SlCamera();
        Vector4f COLOR_FACTOR = new Vector4f(0.0f, 0.0f, 0.6f, 1.0f);


        shaderObj = new SlShaderObject("assets/shaders/vs_texture_1.glsl", "assets/shaders/fs_texture_1.glsl");
        shaderObj.set_shader_program();

        shaderObj.loadMatrix4f("uProjMatrix", camera.getProjectionMatrix());
        shaderObj.loadMatrix4f("uViewMatrix", camera.getViewMatrix());
        shaderObj.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);



        do{
            glfwPollEvents();
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            shaderObj.set_shader_program();


            glBindVertexArray(vao);
            glDrawArrays(GL_TRIANGLES, 0, NUM_ROWS * NUM_COLS * 9);
            glBindVertexArray(0);

            my_wm.swapBuffers();


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

        } while (!my_wm.isGlfwWindowClosed());
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        my_wm.destroyGlfwWindow();

    }





}

