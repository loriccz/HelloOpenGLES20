package com.example.android.opengl;

/**
 * Created by MAREK on 15.2.2016.
 */
public class PlayField {
    private static final int ROWS = 10;
    private static final int COLUMNS = 10;
    private static final float DEFAULT_TOP_CORNER = -0.7f;
    private static final float DEFAULT_LEFT_CORNER = -0.7f;
    private static final float DEFAULT_COLUMN_WIDTH = 0.1f;
    private static final float DEFAULT_ROW_HEIGHT = 0.1f;
    private FieldStone fields[][];

    private float column_witdh,row_height ;

    public int getColumnCount() {
        if (fields==null) return 0;
        if (fields[0] == null) return 0;
        return fields[0].length;

    }
    public int getRowCount() {
        if (fields==null) return 0;
        return fields.length;
    }

    public float getColumnWidth(){
        return this.column_witdh;
    }
    public float getRowHeight(){
        return this.row_height;
    }


// !!! souradnice jsou offset vuci levemu hornimu rohu.. realne souradnice pro vykreslovani musi pripocitavat ten roh
    public Coord getTop_left_corner() {
        return top_left_corner;
    }

    public void setTop_left_corner(Coord top_left_corner) {
        this.top_left_corner = top_left_corner;
    }

    private Coord top_left_corner;


    public PlayField() {
        top_left_corner = new Coord(DEFAULT_LEFT_CORNER, DEFAULT_TOP_CORNER);
        column_witdh = DEFAULT_COLUMN_WIDTH;
        row_height = DEFAULT_ROW_HEIGHT;
        generateFields();
    }

    public PlayField(Coord top_left_corner, float row_height, float column_witdh) {
        this.top_left_corner = top_left_corner;
        this.column_witdh = column_witdh;
        this.row_height = row_height;
        generateFields();
    }

    private void generateFields(){
        fields = new FieldStone[ROWS][COLUMNS];
        for (int y=0; y<ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                fields[y][x] = new FieldStone(
                        new Coord(x*column_witdh,y* row_height),
                        new RGBA(), this); //tady mozna bude muset bejt nastavena natvrdo barva
            }
        }
    }


    public FieldStoneType typeOn(int pos_x, int pos_y) {
        if (pos_x<0 || pos_x>=COLUMNS || pos_y<0 || pos_y>ROWS) return null;
        if (fields[pos_y][pos_x]==null) return null; //index out of bounds?
        return fields[pos_y][pos_x].getType();
    }

    public void draw(float[] mvpMatrix) {
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                fields[y][x].draw(mvpMatrix);
            }
        }
    }
}
