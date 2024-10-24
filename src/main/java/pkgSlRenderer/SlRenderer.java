package pkgSlRenderer;

import pkgSlUtils.SlWindowManager;

public abstract class SlRenderer {
    protected int polygonVertices = 3;
    protected final int polygonMaxVertice = 20;

    public abstract void initOpenGL(SlWindowManager my_wm);

    public abstract void render(int FRAME_DELAY, int NUM_ROWS, int NUM_COLS);

    public abstract void render(float radius);

    public abstract void render();









}
