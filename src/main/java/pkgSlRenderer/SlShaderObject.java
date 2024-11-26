package pkgSlRenderer;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class SlShaderObject {

    int shaderprogram = glCreateProgram();

    public SlShaderObject(String vsFile, String fsFile) {
        String vs_shader_program_string = vsFile;
        shaderprogram = glCreateProgram();

        int vs = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vs, vs_shader_program_string);
        glCompileShader(vs);
        glAttachShader(shaderprogram, vs);

        int fs = glCreateShader(GL_FRAGMENT_SHADER);

        String fs_shader_program_string = fsFile;
        glShaderSource(fs, fs_shader_program_string);
        glCompileShader(fs);
        glAttachShader(shaderprogram, fs);

        glLinkProgram(shaderprogram);

    }


    public void loadVector4f(String vec4Name, Vector4f Color_Factor) {
        int var_loaction = glGetUniformLocation(shaderprogram, vec4Name);
        glUniform4f(var_loaction, Color_Factor.x, Color_Factor.y, Color_Factor.z, Color_Factor.w);
    }

    public void loadMatrix4f(String strMatrixName, Matrix4f my_mat4) {
        int var_location = glGetUniformLocation(shaderprogram, strMatrixName);
        final int OGL_MATRIX_SIZE = 16;
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(OGL_MATRIX_SIZE);
        my_mat4.get(matrixBuffer);
        glUniformMatrix4fv(var_location, false, matrixBuffer);
    } // private static void loadMatrix4f(String strMatrixName, Matrix4f my_mat4)


    public void set_shader_program() {
        glUseProgram(shaderprogram);
    }

    public void compile_shader() {
        glUseProgram(shaderprogram);
    }

    public void colorOfSquare(){
        int uniformColorSquare = glGetUniformLocation(shaderprogram, "COLOR_FACTOR");
        glUniform4f(uniformColorSquare, 0.5f, 0.2f, 0.0f, 1.0f);

    }
}

