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
                this.parts = new FieldStone[4];
                this.parts[0] = new FieldStone(center_column, center_row-1, this.getContext(), this.getType());
                this.parts[1] = new FieldStone(center_column, center_row, this.getContext(), this.getType());
                this.parts[2] = new FieldStone(center_column, center_row+1, this.getContext(), this.getType());
                this.parts[3] = new FieldStone(center_column, center_row+2, this.getContext(), this.getType());
                this.center = this.parts[1];
                break;

            case T_SHAPE:
                this.parts = new FieldStone[4];
                this.parts[0] = new FieldStone(center_column, center_row-1, this.getContext(), this.getType());
                this.parts[1] = new FieldStone(center_column-1, center_row, this.getContext(), this.getType());
                this.parts[2] = new FieldStone(center_column, center_row, this.getContext(), this.getType());
                this.parts[3] = new FieldStone(center_column+1, center_row, this.getContext(), this.getType());
                this.center = this.parts[2];
                break;

            case L_SHAPE:
                this.parts = new FieldStone[4];
                this.parts[0] = new FieldStone(center_column, center_row-1, this.getContext(), this.getType());
                this.parts[1] = new FieldStone(center_column, center_row, this.getContext(), this.getType());
                this.parts[2] = new FieldStone(center_column, center_row+1, this.getContext(), this.getType());
                this.parts[3] = new FieldStone(center_column+1, center_row+1, this.getContext(), this.getType());
                this.center = this.parts[1];
                break;
            case J_SHAPE:
                this.parts = new FieldStone[4];
                this.parts[0] = new FieldStone(center_column, center_row-1, this.getContext(), this.getType());
                this.parts[1] = new FieldStone(center_column, center_row, this.getContext(), this.getType());
                this.parts[2] = new FieldStone(center_column, center_row+1, this.getContext(), this.getType());
                this.parts[3] = new FieldStone(center_column-1, center_row+1, this.getContext(), this.getType());
                this.center = this.parts[1];
                break;

            case S_SHAPE: break;
            case Z_SHAPE: break;
            case PLUS_SHAPE: break;
            case CORNER_SHAPE: break;


        }
        return false;
    }

//    private int[] rotateOffset(boolean clockwise, int[] src) {
//        int tmpx=src[0];
//        int tmpy=src[1];
////        if (tmpx==0 && tmpy==0) return new int[]{0,0};
//
//        if (clockwise) return new int[]{-tmpy,tmpx};
//            else return new int[]{tmpy,-tmpx};
////        } else if (tmpx<0 && tmpy>0) {
////            if (clockwise) return new int[]{-tmpy,tmpx};
////            else return new int[]{tmpy, -tmpx};
////        } else if (tmpx>0 && tmpy>0) {
////            if (clockwise) return new int[]{-tmpy,tmpx}
////        }
//
//    }

    public void rotatePiece(boolean clockwise){
        int cx = this.center.getPos_x();
        int cy = this.center.getPos_y();
        int tmpx, tmpy;
        for (FieldStone rotated: this.getParts()) {
            tmpx = rotated.getPos_x()-cx;
            tmpy = rotated.getPos_y()-cy;
            if (clockwise) {
                rotated.setPos_x(cx - tmpy);
                rotated.setPos_y(cy + tmpx);
            } else {
                rotated.setPos_x(cx+tmpy);
                rotated.setPos_y(cy-tmpx);
            }
            rotated.updateValues();
        }
    }

    private boolean canMergeStoneToContextOn(int x, int y) {
        if (context==null) return false;
        if (context.typeOn(x,y)==FieldStoneType.FREE) return true;
        return false;
    }

    public boolean canMergePieceToContext(int shiftx, int shifty) {
        boolean ret;
        if (this.context==null) return false;
        for (FieldStone fs: this.getParts()) {
            if (!canMergeStoneToContextOn(fs.getPos_x()+shiftx,fs.getPos_y()+shifty)) return false;
        }
        return true;
    }

    public boolean canMovePiece(MoveDir dir) {
        switch (dir) {
            case UP: return canMergePieceToContext(0,-1);
            case DOWN: return canMergePieceToContext(0,1);
            case LEFT: return canMergePieceToContext(-1,0);
            case RIGHT: return canMergePieceToContext(1,0);
        }
        return false;
    }

    public void draw(float[] mvpMatrix) {
        for (FieldStone fs: this.getParts()) fs.draw(mvpMatrix);
    }
}
