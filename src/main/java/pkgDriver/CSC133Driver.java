package pkgDriver;
import static pkgDriver.SlSpot.*;

import pkgSlRenderer.SlPolygonRenderer;
import pkgSlRenderer.SlRenderer;
import pkgSlUtils.SlWindowManager;

public class CSC133Driver {
    public static void main(String[] my_args) {
        SlRenderer my_re = new SlPolygonRenderer();
        SlWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(SlWindowManager.get());

        final int FRAME_DELAY = 700, NUM_ROWS = 20, NUM_COLS = 20;
        my_re.render(FRAME_DELAY, NUM_ROWS, NUM_COLS);
    } // public static void main(String[] my_args)
} // public class CSC133Driver(...)

