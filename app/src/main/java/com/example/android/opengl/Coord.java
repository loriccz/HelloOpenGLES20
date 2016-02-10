package com.example.android.opengl;

/**
 * Created by loric on 9.2.2016.
 */
public class Coord {
    private float x; // -1 .. 1
    private float y; // -1 .. 1
    private float z; // -1 .. 1

    public Coord(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coord() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Coord(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0.0f;
    }

    public Coord(float[] far) {
        if (far.length==3) {
            this.x = far[0];
            this.y = far[1];
            this.z = far[2];
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float[] getCoord() {
        float[] ret = new float[3];
        ret[0] = this.x;
        ret[1] = this.y;
        ret[2] = this.z;
        return ret;
    }
}
