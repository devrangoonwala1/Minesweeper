/**
 * Name: Dev Rangoonwala
 * Date: 12/7/24
 * Class: csc133 - 08
 * Description: This is the XYTextureObject class which helps load the textures into the tiles using the vs_texture_1.glsl and the fs_texture_1.glsl
 */

package pkgSlRenderer;
/*
TODO:
Make this a generic class with separate init_texture() for 2D textures.
 */

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.glBlendColor;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

class XYTextureObject {
    private String texFilepath;
    private int texID;

    public XYTextureObject(String filepath) {
        this.texFilepath = filepath;

        texID = glGenTextures();
        // If we don't enable blending, the transparent pixels in the texture
        // will render as dark pixels:
        glBindTexture(GL_TEXTURE_2D, texID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        loadImageToTexture();
    }

    public void bind_texture() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texID);
    }

    public void unbind_texture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void loadImageToTexture() {
        IntBuffer texWidth    = BufferUtils.createIntBuffer(1);
        IntBuffer texHeight   = BufferUtils.createIntBuffer(1);
        IntBuffer texChannels = BufferUtils.createIntBuffer(1);

        stbi_set_flip_vertically_on_load(true);

        ByteBuffer texImage = stbi_load(texFilepath, texWidth, texHeight, texChannels, 4);
        if (texImage != null) {
            if (texChannels.get(0) == 4 ) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, texWidth.get(0), texHeight.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, texImage);
            } else if (texChannels.get(0) == 3) {
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texWidth.get(0), texHeight.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, texImage);
            } else {
                assert false: "Error loading texture: images with " + texChannels.get(0) +
                        " channels is not supported";
            }
        }
        else {
            assert false : "Error loading the texture image \"" + texFilepath + "\" ";
        }
        // Now that the texture is loaded, the image memory can be released to OS:
    }  //  public void loadImageToTexture(...)

}
