package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SlCamera {
    private Vector3f lookFrom;
    private Vector3f lookAt;
    private Vector3f lookUp;

    public SlCamera() {
        lookFrom = new Vector3f(0.0f, 0.0f, 0.0f);
        lookAt = new Vector3f(0.0f, 0.0f, -1.0f);
        lookUp = new Vector3f(0.0f, 1.0f, 0.0f);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f().ortho(-1.0f, 1.0f, -1.0f, 1.0f, 0.1f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f().lookAt(lookFrom, lookAt, lookUp);
    }
}
