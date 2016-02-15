package com.example.android.opengl;



/**
 * Created by MAREK on 15.2.2016.
 */
public class FieldStone extends Stone {
    private PlayField context;
    private int pos_x; //pozice v ramci PlayFieldu - contextu
    private int pos_y; //pozice v ramci PlayFieldu - contextu

    public FieldStoneType getType() {
        return type;
    }

    public void setType(FieldStoneType type) {
        this.type = type;
    }

    public int getPos_y() {
        return pos_y;
    }

    public void setPos_y(int pos_y) {
        this.pos_y = pos_y;
    }

    public int getPos_x() {
        return pos_x;
    }

    public void setPos_x(int pos_x) {
        this.pos_x = pos_x;
    }

    public PlayField getContext() {
        return context;
    }

    public void setContext(PlayField context) {
        this.context = context;
    }

    private FieldStoneType type;

    public boolean move_dir(MoveDir direction) {
        switch (direction) {
            case UP: {
                if (context.typeOn(pos_x,pos_y-1)==FieldStoneType.FREE) return true;
                break;
            }
            case DOWN: {
                if (context.typeOn(pos_x,pos_y+1)==FieldStoneType.FREE) return true;
                break;
            }
            case LEFT: {
                if (context.typeOn(pos_x - 1, pos_y)==FieldStoneType.FREE) return true;
                break;
            }
            case RIGHT: {
                if (context.typeOn(pos_x + 1, pos_y)==FieldStoneType.FREE) return true;
                break;
            }
        }
        return false;
    }


    public FieldStone(Coord position, RGBA color, PlayField context, FieldStoneType type) {
        super(position, color);
        this.context = context;
        this.type = type;
        this.setPos_x(0);
        this.setPos_y(0);
    }
    public FieldStone(Coord position, RGBA color, PlayField context) {
        super(position, color);
        this.context = context;
        this.type = FieldStoneType.FREE;
    }

    public FieldStone(int pos_x, int pos_y, PlayField context, FieldStoneType type) {
        super(new Coord(),new RGBA(type));
        this.context = context;
        this.type = FieldStoneType.FREE;
        this.setPos_x(pos_x);
        this.setPos_y(pos_y);
        if (this.context!=null) {
            super.setPosition(new Coord(context.getTop_left_corner().getX()+pos_x*context.getColumnWidth(),
                    context.getTop_left_corner().getY()+pos_y*context.getRowHeight() ));
        }
    }

    protected void updateValues() { //po upraveni pos_x/pos_y
        if (this.context!=null) {
            super.setPosition(new Coord(context.getTop_left_corner().getX()+pos_x*context.getColumnWidth(),
                    context.getTop_left_corner().getY()+pos_y*context.getRowHeight() ));
        }
        super.updateValues();
    }

    @Override
    public void draw(float[] mvpMatrix) {
        super.draw(mvpMatrix);
    }
}
