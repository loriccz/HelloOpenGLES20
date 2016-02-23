/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;

/**
 * A two-dimensional triangle for use as a drawn object in OpenGL ES 2.0.
 */
public class Triangle {

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            // the matrix must be included as a modifier of gl_Position
            // Note that the uMVPMatrix factor *must be first* in order
            // for the matrix multiplication product to be correct.
            "  gl_Position = uMVPMatrix * vPosition;" +
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}";

    private FloatBuffer vertexBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    public float[] getTriangleCoords() {
        return triangleCoords;
    }

    public void setTriangleCoords(float[] triangleCoords) {
        this.triangleCoords = triangleCoords;
    }

    private float triangleCoords[] = {
//             in counterclockwise order:
//            0.0f,  0.622008459f, 0.0f,   // top
//           -0.5f, -0.311004243f, 0.0f,   // bottom left
//            0.5f, -0.311004243f, 0.0f    // bottom right
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f
    };
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public RGBA getColor() {
        return color;
    }

    public void setColor(RGBA color) {
        this.color = color;
    }

    protected RGBA color = new RGBA ( 0.63671875f, 0.76953125f, 0.22265625f, 1.0f);
//    private float color[] = //{0.0f, 0.0f, 0.0f};
//            {}; //defaultni barva

    /**
     * Sets up the drawing object data for use in an OpenGL ES context.
     */

    private void UpdateCache() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }
    private void BasicSetup() {
        UpdateCache();

        // prepare shaders and OpenGL program
        int vertexShader = MyGLRenderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader);   // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables
    }
    public Triangle() {
        BasicSetup();
    }

    public Triangle(Coord[] coords) {

        this.triangleCoords[0] = coords[0].getX();
        this.triangleCoords[1] = coords[0].getY();
        this.triangleCoords[2] = coords[0].getZ();
        this.triangleCoords[3] = coords[1].getX();
        this.triangleCoords[4] = coords[1].getY();
        this.triangleCoords[5] = coords[1].getZ();
        this.triangleCoords[6] = coords[2].getX();
        this.triangleCoords[7] = coords[2].getY();
        this.triangleCoords[8] = coords[2].getZ();
        BasicSetup();
    }

    public Triangle(float[] floats, boolean is2d, RGBA color) {

        if (is2d) {
            this.triangleCoords[0] = floats[0];
            this.triangleCoords[1] = floats[1];
            this.triangleCoords[2] = 0.0f;
            this.triangleCoords[3] = floats[2];
            this.triangleCoords[4] = floats[3];
            this.triangleCoords[5] = 0.0f;
            this.triangleCoords[6] = floats[4];
            this.triangleCoords[7] = floats[5];
            this.triangleCoords[8] = 0.0f;

        } else {
            for (int i=0; i<9; i++) {
                this.triangleCoords[i] = floats[i];
            }

        }
        this.setColor(color);
        BasicSetup();
    }

    public void transformMove(float dx, float dy, float dz) {
        for (int i=0; i<3; i++) {
            this.triangleCoords[i*3] = this.triangleCoords[i*3] + dx;
            this.triangleCoords[i*3+1] = this.triangleCoords[i*3+1] + dy;
            this.triangleCoords[i*3+2] = this.triangleCoords[i*3+2] + dz;
        }
        UpdateCache(); //resource intenzive? TODO zvyknout si a naucit se pouzivat tranformacni matice
    }

    public void transformScale(float dx, float dy, float dz, Coord origin) {
//        float sx = this.triangleCoords[0];
//        float sy = this.triangleCoords[1];
//        float sz = this.triangleCoords[2];
        float sx = origin.getX();
        float sy = origin.getY();
        float sz = origin.getZ();
        this.transformMove(-sx,-sy,-sz);
        for (int i=0; i<3; i++) {
            this.triangleCoords[i*3] = this.triangleCoords[i*3] * dx;
            this.triangleCoords[i*3+1] = this.triangleCoords[i*3+1] * dy;
            this.triangleCoords[i*3+2] = this.triangleCoords[i*3+2] * dz;
        }
        this.transformMove(sx,sy,sz);
        UpdateCache();
    }

    /**
     * Encapsulates the OpenGL ES instructions for drawing this shape.
     *
     * @param mvpMatrix - The Model View Project matrix in which to draw
     * this shape.
     */
    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(
                mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color.getValues(), 0);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        MyGLRenderer.checkGlError("glGetUniformLocation");

        // Apply the projection and view transformation
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        MyGLRenderer.checkGlError("glUniformMatrix4fv");

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
