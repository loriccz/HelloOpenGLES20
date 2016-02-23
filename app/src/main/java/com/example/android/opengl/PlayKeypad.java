package com.example.android.opengl;

/**
 * Created by MAREK on 23.2.2016.
 */
public class PlayKeypad extends Triangle {
    //je pouze zobrazovaci container kterymu se posilaj zmeny pri eventech zachycenejch jinde
    private static final float DEFAULT_SIZE = 0.2f;

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
            //leva horni sipka rotace vlevo
            new Triangle(new float[]{0.0f,0.0f,0.5f,0.75f,1.0f,1.0f}, true, darker_c),
            new Triangle(new float[]{0.0f,0.0f,1.0f,1.0f,0.75f,0.5f}, true, lighter_c),

            //leva dolni sipka posun vlevo
            new Triangle(new float[]{0.0f,1.5f,0.75f,2.0f,1.0f,1.5f}, true, darker_c),
            new Triangle(new float[]{0.0f,1.5f,1.0f,1.5f,0.75f,1.25f}, true, lighter_c),

            //velka sipka uprostred smer dolu
            new Triangle(new float[]{1.5f,0.0f,1.0f,0.5f,1.5f,2.0f}, true, lighter_c),
            new Triangle(new float[]{1.5f,0.0f,1.5f,2.0f,2.0f,0.5f}, true, darker_c),

            new Triangle(new float[]{2.0f,1.0f,3.0f,0.0f,2.25f,0.5f}, true, lighter_c),
            new Triangle(new float[]{2.0f,1.0f,2.5f,0.75f,3.0f,0.0f}, true, darker_c),

            new Triangle(new float[]{2.0f,1.5f,3.0f,1.5f,2.25f,1.25f}, true, lighter_c),
            new Triangle(new float[]{2.0f,1.5f,2.25f,1.75f,3.0f,1.5f}, true, darker_c)
        };

        for (Triangle arrow: arrows) {
            arrow.transformScale(this.size, this.size, this.size);
            arrow.transformMove(this.origin.getX(), this.origin.getY(), this.origin.getZ());
        }
//        Triangle tmp = new Triangle()
    }

    public PlayKeypad (boolean is_left, Coord origin) {
        this.left = is_left;
        this.origin = origin;
        BasicSetup();

    }

    public void pressArrow(MoveDir direction, boolean pressed) {
        RGBA lighter, darker;
        if (pressed) {
            lighter = this.lighter_pc;
            darker = this.darker_pc;
        } else {
            lighter = this.lighter_c;
            darker = this.darker_c;
        }
        switch (direction) {
            case LEFT:
                this.arrows[2].setColor(darker);
                this.arrows[3].setColor(lighter);
                break;
            case RIGHT: //...
                break;
            case UP:
                break;
            case DOWN:
                break;
            case ROTATE_LEFT:
                this.arrows[1].setColor(lighter);
                this.arrows[0].setColor(darker);
                break;
            case ROTATE_RIGHT:
        }
    }

}
