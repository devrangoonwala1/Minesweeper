package pkgDriver;
import static pkgDriver.DRSpot.*;

import pkgSlRenderer.DRPolygonRenderer;
import pkgSlRenderer.DRRenderer;
import pkgSlUtils.DRWindowManager;

public class DRCSC133Driver {
    public static void main(String[] my_args) {
        DRRenderer my_re = new DRPolygonRenderer();
        DRWindowManager.get().initGLFWWindow(WIN_WIDTH, WIN_HEIGHT, "CSUS CSC133");
        my_re.initOpenGL(DRWindowManager.get());

        final int FRAME_DELAY = 700, NUM_ROWS = 9, NUM_COLS = 7;
        my_re.render(FRAME_DELAY, NUM_ROWS, NUM_COLS);
    } // public static void main(String[] my_args)
} // public class DRCSC133Driver(...)