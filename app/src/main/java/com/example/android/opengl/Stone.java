package com.example.android.opengl;

/**
 * Created by MAREK on 12.2.2016.
 */
public class Stone extends Square{
    private static final double DEFAULT_STONE_SIZE = 0.1; //upresnit si rozdil mezi float a double
    private static final double DEFAULT_EDGE_SIZE = 0.2;
    private Coord position = new Coord();
    private RGBA stonecolor = new RGBA();
    private RGBA precalc_colors[];
//    private Triangle[] triangles = new Triangle[8];
    private Square squares[];

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
        this.updateValues();
    }

    public double getEdge_size() {
        return edge_size;
    }

    public void setEdge_size(double edge_size) {

        this.edge_size = edge_size;
        this.updateValues();
    }

    private double size = DEFAULT_STONE_SIZE;
    private double edge_size = DEFAULT_EDGE_SIZE;

    public Stone(Coord position, RGBA color) {
        this.position = position;
        this.setColor(color);

        this.updateValues();
    }

    public Stone() {
        RGBA tmpcol = new RGBA();
        tmpcol.setColorByName("black");
        this.setColor(tmpcol);
        this.updateValues();
    }

    protected void updateValues() {
        float ox = this.position.getX();
        float oy = this.position.getY();
        float shift = (float) (this.size*0.5*this.edge_size);
        float sz = (float) this.size;

        this.squares = new Square[5];
        this.precalc_colors = new RGBA[5];
        for (int a=0; a<5; a++) {
            this.squares[a] = new Square();
            this.precalc_colors[a] = new RGBA(this.getColor());
        }
//        Log.d("nullpointer", String.valueOf(this.squares.length)); //debug

//        Log.d("nullpointer", this.squares[0].getSquareCoordsString());


        this.squares[0].setSquareCoords(new float[]{ //horni polygon
                        ox, oy, 0.0f,
                        ox + shift, oy + shift, 0.0f,
                        ox + sz - shift, oy + shift, 0.0f,
                        ox + sz, oy, 0.0f
                }
        );



        this.squares[0].setColor(this.precalc_colors[0].modifyHSV(new float[]{0.0f, -0.4f, 1.0f}));
//                (new RGBA(0.9f, 0.6f, 0.6f, 1.0f)); //svetle cervena

        this.squares[1].setSquareCoords(new float[]{ //pravej polygon
                        ox + sz - shift, oy + shift, 0.0f,
                        ox + sz - shift, oy + sz - shift, 0.0f,
                        ox + sz, oy + sz, 0.0f,
                        ox + sz, oy, 0.0f
                }
        );


        this.squares[1].setColor(this.precalc_colors[1].modifyHSV(new float[]{0.0f, 1.0f, 0.0f}));
//                (new RGBA(0.9f, 0.2f, 0.2f, 1.0f)); //saturovana cervena


        this.squares[2].setSquareCoords(new float[]{ //dolni polygon
                        ox + shift, oy + sz - shift, 0.0f,
                        ox, oy + sz, 0.0f,
                        ox + sz, oy + sz, 0.0f,
                        ox + sz - shift, oy + sz - shift, 0.0f
                }
        );

//        this.squares[2].setColor(new RGBA(0.6f, 0.1f, 0.1f, 1.0f)); //saturovana cervena tmava
        this.squares[2].setColor(this.precalc_colors[2].modifyHSV(new float[]{0.0f, 1.0f, -0.6f})); //saturovana cervena tmava

        this.squares[3].setSquareCoords(new float[]{ //level polygon
                        ox, oy, 0.0f,
                        ox, oy + sz, 0.0f,
                        ox + shift, oy + sz - shift, 0.0f,
                        ox + shift, oy + shift, 0.0f
                }
        );

//        this.squares[3].setColor(new RGBA(0.4f, 0.1f, 0.1f, 1.0f)); // cervena tmava
        this.squares[3].setColor(this.precalc_colors[3].modifyHSV(new float[]{0.0f,0.0f,-0.6f}));

        this.squares[4].setSquareCoords(new float[]{ //prostredni polygon
                        ox + shift, oy + shift, 0.0f,
                        ox + shift, oy + sz - shift, 0.0f,
                        ox + sz - shift, oy + sz - shift, 0.0f,
                        ox + sz - shift, oy + shift, 0.0f
                }
        );

        this.squares[4].setColor(this.getColor());
        //new RGBA(0.7f, 0.3f, 0.3f, 1.0f)
        // cervena medium //prostredek
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
