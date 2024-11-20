/**
 * Name: Dev Rangoonwala
 * Date: 11/19/24
 * Class: csc133 - 08
 * Description: This class helps with making the openGl window so that it can be displayed on the screen which also depends on the height and width of how the window should be displayed. We also use the glfwSetKeyCallback so that the window can handle the keyboard input events.
 */

package pkgSlUtils;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DRWindowManager {
    private static long glfw_win;
    private static DRWindowManager my_window = new DRWindowManager();
    private static long window = 0;


    public int[] getCurrentWindowSize(){
        int[] size = new int[2];

        return size;
    }


    public static DRWindowManager get() {

        return my_window;
    }

    public void destroyGlfwWindow(){
        glfwDestroyWindow(glfw_win);
        glfwTerminate();
    }

    public void swapBuffers(){
        glfwSwapBuffers(glfw_win);
    }

    public boolean isGlfwWindowClosed(){
        return glfwWindowShouldClose(glfw_win);
    }



    public static long initGLFWWindow(int WIN_WIDTH, int WIN_HEIGHT, String w_title){


        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Could not initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        glfw_win = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, w_title, NULL, NULL);

        if (glfw_win == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        updateContextToThis();

        glfwSetKeyCallback(glfw_win, SlKeyListener::keyCallback);

        return glfw_win;


    }

    SlKeyListener keyListener = new SlKeyListener();



    public SlKeyListener getKeyListener() {
        return keyListener;
    }

    public int[] getWindowSize(){
        int[] size = new int[2];

        return size;
    }

    public static void updateContextToThis(){
        glfwMakeContextCurrent(glfw_win);

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 0.0f, CC_ALPHA = 0.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
    }


}
