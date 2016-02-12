package com.example.android.opengl;

import android.util.Log;

/**
 * Created by MAREK on 12.2.2016.
 */
public class Stone extends Square{
    private static final double DEFAULT_STONE_SIZE = 0.1; //upresnit si rozdil mezi float a double
    private static final double DEFAULT_EDGE_SIZE = 0.2;
    private Coord position = new Coord();
    private RGBA stonecolor = new RGBA();
//    private Triangle[] triangles = new Triangle[8];
    private Square squares[];

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getEdge_size() {
        return edge_size;
    }

    public void setEdge_size(double edge_size) {
        this.edge_size = edge_size;
    }

    private double size = DEFAULT_STONE_SIZE;
    private double edge_size = DEFAULT_EDGE_SIZE;

    public Stone(Coord position, RGBA color) {
        this.position = position;
        this.stonecolor = color;

        this.generateValues();
    }

    public Stone() {
        this.stonecolor.setColorByName("black");
        this.generateValues();
    }

    private void generateValues() {
        float ox = this.position.getX();
        float oy = this.position.getY();
        float shift = (float) (this.size*0.5*this.edge_size);
        float sz = (float) this.size;

        this.squares = new Square[5];
        for (Square a : squares) a = new Square();
        Log.d("nullpointer", String.valueOf(this.squares.length)); //debug

        this.squares[0].setSquareCoords(new float[]{
                        ox, oy, 0.0f,
                        ox + shift, oy + shift, 0.0f,
                        ox + sz - shift, oy + shift, 0.0f,
                        ox + sz, oy, 0.0f
                }
        );
        this.squares[0].setColor(new RGBA(0.9f, 0.6f, 0.6f, 1.0f)); //svetle cervena

        this.squares[1].setSquareCoords(new float[]{
                        ox + sz - shift, oy + shift, 0.0f,
                        ox + sz - shift, oy + sz - shift, 0.0f,
                        ox + sz, oy + sz, 0.0f,
                        ox + sz, oy, 0.0f
                }
        );

        this.squares[1].setColor(new RGBA(0.9f, 0.2f, 0.2f, 1.0f)); //saturovana cervena

        this.squares[2].setSquareCoords(new float[]{
                        ox + shift, oy + sz - shift, 0.0f,
                        ox, oy + sz, 0.0f,
                        ox + sz, oy + sz, 0.0f,
                        ox + sz - shift, oy + sz - shift, 0.0f
                }
        );

        this.squares[2].setColor(new RGBA(0.6f, 0.1f, 0.1f, 1.0f)); //saturovana cervena tmava

        this.squares[3].setSquareCoords(new float[]{
                        ox, oy, 0.0f,
                        ox, oy + sz, 0.0f,
                        ox + shift, oy + sz - shift, 0.0f,
                        ox + shift, oy + shift, 0.0f
                }
        );

        this.squares[3].setColor(new RGBA(0.4f, 0.1f, 0.1f, 1.0f)); //saturovana cervena tmava

        this.squares[4].setSquareCoords(new float[]{
                        ox + shift, oy + shift, 0.0f,
                        ox + shift, oy + sz - shift, 0.0f,
                        ox + sz - shift, oy + sz - shift, 0.0f,
                        ox + sz - shift, oy + shift, 0.0f
                }
        );

        this.squares[4].setColor(new RGBA(0.7f, 0.3f, 0.3f, 1.0f)); // cervena medium
//        this.triangles[0].setTriangleCoords(new float[] {
//                ox,
//        });

        //// TODO: 12.2.2016
    }

    @Override
    public void draw(float[] mvpMatrix) {
        for (Square sq : this.squares ) {
            sq.draw(mvpMatrix);
        }
    }

    public RGBA getColor() {
        return stonecolor;
    }

    public void setColor(RGBA color) {
        this.stonecolor = color;
    }



    public Coord getPosition() {
        return position;
    }

    public void setPosition(Coord position) {
        this.position = position;
    }




}
