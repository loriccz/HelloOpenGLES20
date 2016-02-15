package com.example.android.opengl;

/**
 * Created by MAREK on 15.2.2016.
 */
public class PlayPiece {
    public static final int DEFAULT_SPAWNROW = 2;

    private FieldStone[] parts;

    public PlayField getContext() {
        return context;
    }

    public void setContext(PlayField context) {
        this.context = context;
    }

    private PlayField context;

    public RGBA getColor() {
        return color;
    }

    public void setColor(RGBA color) {
        this.color = color;
    }

    private RGBA color;

    public FieldStoneType getType() {
        return type;
    }

    public void setType(FieldStoneType type) {
        this.type = type;
    }

    private FieldStoneType type;

    public FieldStone[] getParts() {
        return parts;
    }

    public void setParts(FieldStone[] parts) {
        this.parts = parts;
    }

    public FieldStone getCenter() {
        return center;
    }

    public void setCenter(FieldStone center) {
        this.center = center;
    }

    private FieldStone center;

    public PlayPiece(RGBA color, PlayField context, FieldStoneType type) {
        this.setColor(color);
        this.setType(type);
        this.setContext(context);
    }

    public boolean materialize(PieceForm pieceform, int on_row){
        //defaultne to bude spawnovat v horizontalni pulce v druhym radku odshora
        float center_x, center_y;
        center_x = context.getTop_left_corner().getX()+(context.getColumnCount()/2)*context.getColumnWidth();
        center_y = context.getTop_left_corner().getY()+(DEFAULT_SPAWNROW*context.getRowHeight());

        int center_column = context.getColumnCount()/2;
        int center_row = DEFAULT_SPAWNROW;
        //!!!TODO
        FieldStone toadd;
        switch (pieceform) {
            case I_SHAPE :
                toadd = new FieldStone(new Coord(center_x,center_y), this.getColor(), this.getContext(), this.getType());



                break;

        }
        return false;
    }

    private boolean canMergeStoneToContext(int x, int y) {
        if (context==null) return false;
        if (context.typeOn(x,y)==FieldStoneType.FREE) return true;
        return false;
    }


}
