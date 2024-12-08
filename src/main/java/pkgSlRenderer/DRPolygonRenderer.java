/**
 * Name: Dev Rangoonwala
 * Date: 12/7/24
 * Class: csc133 - 08
 * Description: This DRPolygonRenderer class is an extension of the DRRenderer class which has the renderTile method which is used to make the necessary shape of the two triangles which then is a square. The render method is used to help get the window working where it will help with showing the tiles and showing the images which corresponds to the tiles and size. The makeTextureCoords method is used to help with the generation of textures using the coordinate system.
 */

package pkgSlRenderer;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import pkgBackendEngine.DRCellData;
import pkgBackendEngine.DRSIMSBoard;
import pkgBackendEngine.DRSpot;
import pkgSlUtils.DRWindowManager;
import pkgSlUtils.XYMouseListener;


import java.nio.FloatBuffer;

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
        float z = 0.0f;
        int i = 0;
        while(i < NUM_ROWS){
            int j = 0;
            while(j < NUM_COLS){
                x0 = -1.0f + j * widthOfTileMax + spaceRows / 2.0f;
                y0 = 1.0f - i * heightOfTileMax - spaceCols / 2.0f - heightTiles;
                x1 = x0;
                y1 = y0 + heightTiles;
                x2 = x0 + widthTiles;
                y2 = y1;
                x3 = x2;
                y3 = y0;

                //Triangle 1:
                vertices[index++] = x0;
                vertices[index++] = y0;
                vertices[index++] = z;

                vertices[index++] = x1;
                vertices[index++] = y1;
                vertices[index++] = z;

                vertices[index++] = x2;
                vertices[index++] = y2;
                vertices[index++] = z;

                //Triangle 2:
                vertices[index++] = x2;
                vertices[index++] = y2;
                vertices[index++] = z;

                vertices[index++] = x3;
                vertices[index++] = y3;
                vertices[index++] = z;

                vertices[index++] = x0;
                vertices[index++] = y0;
                vertices[index++] = z;
                j++;
            }
            i++;
        }
        return vertices;

    }

    private float[] makeTextureCoords(int tiles){
        float[] textureCoords = {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,

                1.0f, 1.0f,
                1.0f, 0.0f,
                0.0f, 0.0f,
        };

        float[] textCoords = new float[tiles * 6 * 2];
        int i = 0;
        while (i < tiles){
            System.arraycopy(textureCoords, 0, textCoords, i * 12, 12);
            i++;
        }

        return textCoords;

    }


    @Override
    public void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS){
        float space = 0.2f;
        int maxMine = 14;

        DRSIMSBoard board = new DRSIMSBoard(NUM_ROWS, NUM_COLS, maxMine);
        DRCellData[][] ms_board = board.getMs_Board();

        float[] vertices = renderTiles(NUM_ROWS, NUM_COLS, space);

        FloatBuffer vertex = BufferUtils.createFloatBuffer(vertices.length);
        vertex.put(vertices).flip();

        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int index=0;
        int size = 3;
        boolean normalized = false;
        int stride = 0;
        int pointer = 0;

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, normalized, stride, pointer);
        glEnableVertexAttribArray(index);

        float[] textureCoordinate = makeTextureCoords(NUM_ROWS * NUM_COLS);
        FloatBuffer bufferTextCoords = BufferUtils.createFloatBuffer(textureCoordinate.length);
        bufferTextCoords.put(textureCoordinate).flip();

        index = 1;
        size = 2;

        int textCoordVBO = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textCoordVBO);
        glBufferData(GL_ARRAY_BUFFER, bufferTextCoords, GL_STATIC_DRAW);
        glVertexAttribPointer(index, size, GL_FLOAT, normalized, stride, pointer);
        glEnableVertexAttribArray(index);


        int buffer = 0;
        glBindBuffer(GL_ARRAY_BUFFER, buffer);
        glBindVertexArray(0);



        DRCamera camera = new DRCamera();
        float x = 1.0f;
        float y = 1.0f;
        float z = 1.0f;
        float w = 1.0f;
        Vector4f COLOR_FACTOR = new Vector4f(x, y, z, w);


        shaderObj = new DRShaderObject("assets/shaders/vs_texture_1.glsl", "assets/shaders/fs_texture_1.glsl");
        shaderObj.set_shader_program();

        shaderObj.loadMatrix4f("uProjMatrix", camera.getProjectionMatrix());
        shaderObj.loadMatrix4f("uViewMatrix", camera.getViewMatrix());
        shaderObj.loadVector4f("COLOR_FACTOR", COLOR_FACTOR);

        mysteryBoxTexture = new XYTextureObject("src/main/resources/assets/images/MysteryBox_2.png");
        shiningDiamondTexture = new XYTextureObject("src/main/resources/assets/images/ShiningDiamond_1.png");
        mineBombTexture = new XYTextureObject("src/main/resources/assets/images/MineBomb_2.png");



        do{
            glfwPollEvents();
            float red = 0.0f;
            float green = 0.0f;
            float blue = 0.0f;
            float alpha = 1.0f;
            glClearColor(red, green, blue, alpha);
            glClear(GL_COLOR_BUFFER_BIT);

            shaderObj.set_shader_program();

            int first = 0;
            int count = NUM_ROWS * NUM_COLS * 9;

            glBindVertexArray(vao);
            glDrawArrays(GL_TRIANGLES, first, count);
            glBindVertexArray(0);


            if(XYMouseListener.mouseButtonDown(0) && board.isGameActive()){
                float XM = XYMouseListener.getX();
                float YM = XYMouseListener.getY();

                XYMouseListener.mouseButtonDownReset(0);


                float rowX = (XM / width) * 2.0f -1.0f;
                float colY = 1.0f - (YM / height) * 2.0f;

                float maxTileWidth = 2.0f / NUM_COLS;
                float maxTileHeight = 2.0f / NUM_ROWS;

                int colIndices = (int)((rowX + 1.0f)/maxTileWidth);
                int rowIndices = (int)((1.0f - colY)/maxTileHeight);

                if (colIndices < 0 || colIndices >= NUM_COLS || rowIndices < 0 || rowIndices >= NUM_ROWS) {
                    continue;
                }


                int rowClick = rowIndices;
                int colClick = colIndices;
                DRCellData unit = ms_board[rowClick][colClick];

                if (rowIndices >= 0 && rowIndices < NUM_ROWS && colIndices >= 0 && colIndices < NUM_COLS) {

                    if (unit.getStatus() == DRSpot.CELL_STATUS.NOT_EXPOSED) {
                        board.changeCellStatus(rowClick, colClick);
                        unit.setStatus(DRSpot.CELL_STATUS.EXPOSED);
                        if (unit.getType() == DRSpot.CELL_TYPE.MINE) {
                            board.changeGameActive(false);
                            for (int row = 0; row < NUM_ROWS; row++) {
                                for (int col = 0; col < NUM_COLS; col++) {
                                    ms_board[row][col].setStatus(DRSpot.CELL_STATUS.EXPOSED);

                                }
                            }
                            System.out.printf("\nMouse click at: (%d, %d) Score: %d", rowClick, colClick, board.getCurrentScore());
                        } else {
                            int score = board.getCurrentScore() + unit.getCell_score();
                            board.setCurrentScore(score);
                            System.out.printf("\nMouse click at: (%d, %d) Score: %d", rowClick, colClick, score);
                        }
                    }
                }
            }

            glBindVertexArray(vao);

            int row = 0;
            while (row < NUM_ROWS) {
                int col = 0;
                while (col < NUM_COLS){
                    DRCellData boardUnits = ms_board[row][col];
                    XYTextureObject placeCurrentText;
                    if (boardUnits.getStatus() == DRSpot.CELL_STATUS.NOT_EXPOSED) {
                        placeCurrentText = mysteryBoxTexture;
                    }
                    else {
                        if (boardUnits.getType() == DRSpot.CELL_TYPE.MINE) {
                            placeCurrentText = mineBombTexture;
                        }
                        else{
                            placeCurrentText = shiningDiamondTexture;
                        }
                    }
                    placeCurrentText.bind_texture();

                    int textureIndex = row * NUM_COLS + col;
                    int textureVertex = textureIndex * 6;
                    glDrawArrays(GL_TRIANGLES, textureVertex, 6);
                    col++;
                }
                row++;
            }

            glBindVertexArray(0);
            my_wm.swapBuffers();

        } while (!my_wm.isGlfwWindowClosed());
        glDeleteBuffers(vbo);
        glDeleteBuffers(textCoordVBO);
        glDeleteVertexArrays(vao);
        my_wm.destroyGlfwWindow();
    }
}

