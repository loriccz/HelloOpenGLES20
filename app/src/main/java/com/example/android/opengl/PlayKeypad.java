package com.example.android.opengl;

import android.util.Log;

/**
 * Created by MAREK on 23.2.2016.
 */
public class PlayKeypad extends Triangle {
    private static final String TAG = PlayKeypad.class.getSimpleName();
    //je pouze zobrazovaci container kterymu se posilaj zmeny pri eventech zachycenejch jinde
    private static final float DEFAULT_SIZE = 0.5f;


    private Triangle arrows[];
    private Coord origin;
    private float size;
    private boolean left;
    private RGBA lighter_c, darker_c, lighter_pc, darker_pc;

    private void BasicSetup() {
        this.lighter_c = new RGBA(0.8f, 0.8f, 0.8f, 1.0f);
        this.darker_c = new RGBA(0.6f, 0.6f, 0.6f, 1.0f);
        this.lighter_pc = new RGBA(0.2f, 0.2f, 0.8f, 1.0f);
        this.darker_pc = new RGBA(0.1f, 0.1f, 0.6f, 1.0f);
        this.size = DEFAULT_SIZE;
//nasetit velikost 1 od pocatku nula, na konci vsechny parametry preskalovat hromadne velikosti a pricist origin(pocatek)
        arrows = new Triangle[] {

                new Triangle(new float[]{0.00f,0.66f,0.33f,0.33f,0.25f,0.50f}, true, lighter_c),
                new Triangle(new float[]{0.00f,0.66f,0.16f,0.41f,0.33f,0.33f}, true, darker_c),

                //leva dolni sipka posun vlevo
                new Triangle(new float[]{0.00f,0.16f,0.33f,0.16f,0.25f,0.25f}, true, lighter_c),
                new Triangle(new float[]{0.00f,0.16f,0.25f,0.08f,0.33f,0.16f}, true, darker_c),

                //velka sipka uprostred smer dolu
                new Triangle(new float[]{0.50f,0.66f,0.33f,0.50f,0.50f,0.00f}, true, lighter_c),
                new Triangle(new float[]{0.50f,0.66f,0.50f,0.00f,0.66f,0.50f}, true, darker_c),

                //rotace vpravo
                new Triangle(new float[]{0.66f,0.33f,1.00f,0.66f,0.75f,0.50f}, true, lighter_c),
                new Triangle(new float[]{0.66f,0.33f,0.86f,0.41f,1.00f,0.66f}, true, darker_c),

                //posun vpravo
                new Triangle(new float[]{0.66f,0.16f,1.00f,0.16f,0.75f,0.25f}, true, lighter_c),
                new Triangle(new float[]{0.66f,0.16f,0.75f,0.08f,1.00f,0.16f}, true, darker_c)
        };



        for (Triangle arrow: arrows) {

            arrow.transformMove(this.origin.getX(), this.origin.getY(), this.origin.getZ());
        }

    }

    public PlayKeypad (boolean is_left, Coord origin) {
        this.left = is_left;
        this.origin = origin;
        BasicSetup();
    }

    public void pressAllArrows(boolean pressed) {
        Log.d(TAG,"press all arrows called" );
        RGBA lighter, darker;
        if (pressed) {
            lighter = this.lighter_pc;
            darker = this.darker_pc;
        } else {
            lighter = this.lighter_c;
            darker = this.darker_c;
        }
        for (int i=0; i<5; i++) {
            arrows[i*2].setColor(lighter);
            arrows[i*2+1].setColor(darker);
        }

    }

    public void pressArrow(MoveDir direction, boolean pressed) {
        Log.d(TAG, "pressArrow called with dir:"+direction.toString());
        RGBA lighter, darker;
        if (pressed) {
            lighter = this.lighter_pc;
            darker = this.darker_pc;
        } else {
            lighter = this.lighter_c;
            darker = this.darker_c;
        }
        switch (direction) {


            case UP:
                break;

            case ROTATE_LEFT:
                this.arrows[0].setColor(lighter);
                this.arrows[1].setColor(darker);
                break;
            case LEFT:
                this.arrows[2].setColor(lighter);
                this.arrows[3].setColor(darker);
                break;
            case DOWN:
                this.arrows[4].setColor(lighter);
                this.arrows[5].setColor(darker);
                break;
            case ROTATE_RIGHT:
                this.arrows[6].setColor(lighter);
                this.arrows[7].setColor(darker);
                break;
            case RIGHT:
                this.arrows[8].setColor(lighter);
                this.arrows[9].setColor(darker);
                break;
        }
    }

    public MoveDir onPress(float x, float y) { //nejsou to glx souradnice

//upraveno na tyhlety non-gl souradnice

//        float ox = this.origin.getX();
//        float oy = this.origin.getY();
//
//        float wx = this.szx/3;
//        float wy = this.szy/2;
        float ox = this.left ? 0.0f : 0.5f;
        float oy = 1.0f;

        float wx = 1/6f;
        float wy = 0.25f;

        if (x>ox      && x<ox+wx   && y<oy    && y>oy-wy)   return MoveDir.LEFT;
        if (x>ox      && x<ox+wx   && y<oy-wy && y>oy-wy*2) return MoveDir.ROTATE_LEFT;
        if (x>ox+wx   && x<ox+wx*2 && y<oy    && y>oy-wy*2) return MoveDir.DOWN;
        if (x>ox+wx*2 && x<ox+wx*3 && y<oy    && y>oy-wy)   return MoveDir.RIGHT;
        if (x>ox+wx*2 && x<ox+wx*3 && y<oy-wy && y>oy-wy*2) return MoveDir.ROTATE_RIGHT;
        return MoveDir.INVALID;
    }

    @Override
    public void draw(float[] mvpMatrix) {
        for (Triangle tr : this.arrows ) tr.draw(mvpMatrix);
    }
}
