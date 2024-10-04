package pkgSlUtils;


import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11C.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;
import static pkgSlRenderer.slRenderEngine.my_wm;

public class slWindowManager {
    public static long glfw_win;
    public static slWindowManager my_window = new slWindowManager();


    public static int[] getCurrentWindowSize(){
        int[] size = new int[2];
        return size;
    }

    public static slWindowManager get() {

        return my_window;
    }

    public void destroyGlfwWindow(){

    }

    public void swapBuffers(){

    }

    public boolean isGlfwWindowClosed(){
        return false;
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

        glfw_win = glfwCreateWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC 133", NULL, NULL);

        if (glfw_win == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        updateContextToThis();

        while (!glfwWindowShouldClose(glfw_win)) {
            glfwPollEvents();
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfw_win);
        }

        return glfw_win;


    }

    public int[] getWindowSize(){
        int[] size = new int[2];
        return size;
    }

    public static void updateContextToThis(){
        glfwWindowShouldClose(glfw_win);
        glfwMakeContextCurrent(glfw_win);

        GL.createCapabilities();
        float CC_RED = 0.0f, CC_GREEN = 0.0f, CC_BLUE = 1.0f, CC_ALPHA = 1.0f;
        glClearColor(CC_RED, CC_GREEN, CC_BLUE, CC_ALPHA);
    }

}
