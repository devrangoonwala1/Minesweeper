package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SlCamera {
    private Vector3f lookFrom;
    private Vector3f lookAt;
    private Vector3f lookUp;
    private Matrix4f projectionMatrix;
    private Matrix4f viewMatrix;

    public SlCamera() {
        lookFrom = new Vector3f(0.0f, 0.0f, 0.0f);
        lookAt = new Vector3f(0.0f, 0.0f, 1.0f);
        lookUp = new Vector3f(0.0f, 1.0f, 0.0f);

        this.viewMatrix = new Matrix4f();
        this.projectionMatrix = new Matrix4f();

        updateView();
        updateProjection();

    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void updateProjection(){
        projectionMatrix.identity().ortho(-1.0f, 1.0f, -1.0f,1.0f, 0.1f, 100.0f);
    }

    public void updateView() {
        viewMatrix.identity().lookAt(lookAt, lookFrom, lookUp);
    }

    public void setprojectionMatrix(float left, float right, float bottom, float top, float zNear, float zFar) {
        projectionMatrix.identity().ortho(left, right, bottom, top, zNear, zFar);
    }

    public void setLookFrom(float x, float y, float z) {
        this.lookFrom.set(x, y, z);
        updateView();
    }

    public void setLookAt(float x, float y, float z) {
        this.lookAt.set(x, y, z);
        updateView();
    }

    public void setLookUp(float x, float y, float z) {
        this.lookUp.set(x, y, z);
        updateView();
    }
}
