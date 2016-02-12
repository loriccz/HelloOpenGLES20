package com.example.android.opengl;



/**
 * Created by MAREK on 12.2.2016.
 */
public class RGBA {
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
        if (factor.length!=3) return ret;
        float[] hsv = new float[3];
        int r = (int) this.R()*256;
        int g = (int) this.R()*256;
        int b = (int) this.B()*256;
        android.graphics.Color.RGBToHSV(r, g, b, hsv);
        float h = hsv[0];
        float s = hsv[1];
        float v = hsv[2];
        for (int i=1; i<=2; i++) {
            if (factor[i]>0) {
                hsv[i] += factor[i]*(1-hsv[i]);
            } else if (factor[i]<0) {
                hsv[i] += factor[i]*hsv[i];
            }
        }
        if (factor[0]>0) hsv[0]+=factor[0]*(360-hsv[0]);
        else if (factor[0]<0) hsv[0]+=factor[0]*hsv[0];

        int tmp = android.graphics.Color.HSVToColor(hsv);
        ret.setValues(new float[] {
                android.graphics.Color.red(tmp)/256,
                android.graphics.Color.green(tmp)/256,
                android.graphics.Color.blue(tmp)/256,
                this.A()
        });
        return ret;
    }


}
