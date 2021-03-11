import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import comp3170.*;
import org.joml.Matrix3f;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Week2 extends JFrame implements GLEventListener {
    final private File DIRECTORY = new File("src");
    final private String VERTEX_SHADER = "vertex.glsl";
    final private String FRAGMENT_SHADER = "fragment.glsl";

    private int width = 800;
    private int height = 800;
    private GLCanvas canvas;
    private Shader shader;
    private int vertexBuffer, indexBuffer, indexCount;
    private int colorBuffer;


    public Week2(){
        super("Week2 demo");

        // init OpenGL
        GLProfile profile = GLProfile.get(GLProfile.GL4);
        GLCapabilities caps = new GLCapabilities(profile);
        canvas = new GLCanvas(caps);
        canvas.addGLEventListener(this);
        add(canvas);

        // init JFrame
        setSize(width, height);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e){
                 System.exit(0);
             }
        });
    }

    public static void main(String[] args){
        new Week2();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClearColor(0, 0, 0, 1);
        try{
            File vertexShader = new File(DIRECTORY, VERTEX_SHADER);
            File fragmentShader = new File(DIRECTORY, FRAGMENT_SHADER);
            shader = new Shader(vertexShader, fragmentShader);
        }
        catch (IOException | comp3170.GLException e ){
            e.printStackTrace();
            System.exit(1);
        }
        // Outline
        // Inside top
        // Inside bottom
        float[] vertices = {
                // Outline
                -0.4f, 0.6f,
                0.2f, 0.6f,
                0.4f, 0.4f,
                0.4f, 0.2f,
                0.2f, 0f,
                0.4f, -0.2f,
                0.4f, -0.4f,
                0.2f, -0.6f,
                - 0.4f, -0.6f,

                // Inside top
                -0.2f, 0.4f,
                0f, 0.4f,
                0.1f, 0.3f,
                0f, 0.2f,
                -0.2f, 0.2f,

                // Inside bottom
                -0.2f, -0.2f,
                0f, -0.2f,
                0.1f, -0.3f,
                0f, -0.4f,
                -0.2f, -0.4f,
        };
        System.out.println("Vertex buffer length: " + vertices.length);
        vertexBuffer = shader.createBuffer(vertices, gl.GL_FLOAT_VEC2);

        int[] indices = new int[]{
                0, 1, 9,
                1, 9, 10,
                1, 10, 11,
                1, 2, 11,
                2, 11, 12,
                2, 3, 12,
                3, 4, 12,
                4, 12, 15,
                4, 5, 15,
                5, 6, 15,
                6, 15, 16,
                6, 7, 16,
                7, 16, 17,
                7, 17, 18,
                7, 8, 18,
                12, 14, 15,
                12, 13, 14,
                0, 9, 13,
                8, 14, 18,
                0, 13, 14,
                0, 8, 14,
                0, 8, 14,


        };
        indexBuffer = shader.createIndexBuffer(indices);
        indexCount = indices.length;

        float[] colors = new float[vertices.length/2 * 3];
        Random r = new Random();
        for(int i = 0; i < colors.length; i++){
            colors[i] = r.nextFloat();
        }

        colorBuffer = shader.createBuffer(colors, gl.GL_FLOAT_VEC3);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {

    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        shader.enable();
        shader.setAttribute("a_position", vertexBuffer);
        shader.setAttribute("a_color", colorBuffer);

        float[] topColor = {0.6f, 0.8f, 1f};
        float[] bottomColor = {0.1f, 0.22f, 0.5f};
       // shader.setUniform("u_topColor", topColor);
        //shader.setUniform("u_bottomColor", bottomColor);
        gl.glBindBuffer(gl.GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        gl.glDrawElements(gl.GL_TRIANGLES, indexCount, gl.GL_UNSIGNED_INT, indexBuffer);
        Matrix3f M = new Matrix3f(1f, 0f, 0f,  0f, 1f, 0f,  0f, 0f, 1f);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
}