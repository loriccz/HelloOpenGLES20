package com.example.android.opengl;


import android.util.Log;

/**
 * Created by MAREK on 12.2.2016.
 */
public class RGBA {
    public static final String RGBADEBUG = "RGBAdebug";
    private float values[] = new float[4];

    public float R() {
        return values[0];
    }
    public float G() {
        return values[1];
    }
    public float B() {
        return values[2];
    }
    public float A() {
        return values[3];
    }

    public void setAlpha(float alpha) {
        this.values[3] = alpha;
    }
    public void setR(float R) {
        this.values[0] = R;
    }
    public void setG(float G) {
        this.values[0] = G;
    }
    public void setB(float B) {
        this.values[0] = B;
    }



    public RGBA (){
        values = new float[] {0.0f,0.0f,0.0f,0.0f};
    }
    public RGBA (float[] vals) {
        values = vals.clone();
    }
    public RGBA (float R, float G, float B, float A) {
        values = new float[] {R, G, B, A};
    }
    public RGBA (RGBA cln) { values = cln.values.clone(); }

    public RGBA (FieldStoneType type) {
        switch (type) {
            case FREE:       values = new float[] {0.0f,0.0f,0.0f,1.0f}; break; //cerny
            case FLOATING_R: values = new float[] {1.0f,0.0f,0.0f,1.0f}; break; //cerveny
            case FLOATING_G: values = new float[] {0.0f,1.0f,0.0f,1.0f}; break; //zeleny
            case FLOATING_B: values = new float[] {0.0f,0.0f,1.0f,1.0f}; break; //modry
            case GROUNDED:   values = new float[] {0.5f,0.5f,0.5f,1.0f}; break;  //sedivy
            case SPECIAL:    values = new float[] {0.7f,0.7f,0.3f,1.0f}; break; //oranzovy
        }
    }

    public float[] getValues() {
        return values;
    }

    public void setValues(float[] values) {
        this.values = values;
    }

    public void setColorByName(String col){
        if (col.equals("red")) {
            this.values = new float[]{1.0f, 0.0f, 0.0f,0.0f};
        } else if (col.equals("green")) {
            this.values = new float[]{0.0f, 1.0f, 0.0f,0.0f};
        } else if (col.equals("blue")) {
            this.values = new float[]{0.0f, 0.0f, 1.0f,0.0f};
        } else if (col.equals("yellow")) {
            this.values = new float[]{1.0f, 1.0f, 0.0f,0.0f};
        } else if (col.equals("magenta")) {
            this.values = new float[]{1.0f, 0.0f, 1.0f,0.0f};
        } else if (col.equals("cyan")) {
            this.values = new float[]{0.0f, 1.0f, 1.0f,0.0f};
        } else if (col.equals("black")) {
            this.values = new float[]{0.0f, 0.0f, 0.0f,0.0f};
        } else this.values = new float[]{0.0f,0.0f,0.0f,0.0f};


    }

    public RGBA modifyHSV(float[] factor) { //-1..1 psycho.. uvidime
        RGBA ret = new RGBA();
//        Log.d(RGBADEBUG,"factors:"+factor[0]+" "+factor[1]+" "+factor[2]);
//        Log.d(RGBADEBUG,"pre R:"+this.R()+" G:"+this.G()+" B:"+this.B());
        if (factor.length!=3) return ret;
        float[] hsv = new float[3];
        int r = (int) (this.R()*256);
        int g = (int) (this.G()*256);
        int b = (int) (this.B()*256);
//        Log.d(RGBADEBUG,"converted r:"+r+" g:"+g+" b"+b);

        android.graphics.Color.RGBToHSV(r, g, b, hsv);
        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];
//        Log.d(RGBADEBUG,"HSVpre h:"+hsv[0]+" s:"+hsv[1]+" v:"+hsv[2]);
        for (int i=1; i<=2; i++) {
            if (factor[i]>0) {
                hsv[i] += factor[i]*(1-hsv[i]);
            } else if (factor[i]<0) {
                hsv[i] += factor[i]*hsv[i];
            }
        }
        if (factor[0]>0) hsv[0]+=factor[0]*(360-hsv[0]);
        else if (factor[0]<0) hsv[0]+=factor[0]*hsv[0];

//        Log.d(RGBADEBUG,"HSVpost h:"+hsv[0]+" s:"+hsv[1]+" v:"+hsv[2]);
        int tmp = android.graphics.Color.HSVToColor(hsv);
        /*Log.d(RGBADEBUG,"tmp: "+tmp+ "red component:"+android.graphics.Color.red(tmp)+
                "green component:"+android.graphics.Color.green(tmp)+
                "blue component:"+android.graphics.Color.blue(tmp)
        );*/
        ret.setValues(new float[] {
                (float)android.graphics.Color.red(tmp)/256,
                (float)android.graphics.Color.green(tmp)/256,
                (float)android.graphics.Color.blue(tmp)/256,
                this.A()
        });
        return ret;
    }

    @Override
    public String toString() {
        return "[R:"+this.R()+
               "G:"+this.G()+
               "B:"+this.B()+
               "A:"+this.A()+"]";
    }
}
