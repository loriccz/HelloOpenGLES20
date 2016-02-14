package com.example.android.opengl.test;

import android.util.Log;

import com.example.android.opengl.RGBA;

import junit.framework.TestCase;

/**
 * Created by loric on 14.2.2016.
 */
public class RGBAtest extends TestCase {

    private RGBA testovaci_barva = new RGBA();


    public void testHSV() {
        for (int a=0; a<4; a++) {
            assertEquals(testovaci_barva.getValues()[a],0.0f);
        }
//        assertEquals(testovaci_barva.getValues()[3], 1.0f);

//        Log.d("RGBAtest", "testovaci_barva pred: " + testovaci_barva.toString());
        this.testovaci_barva.setValues(new float[] {0.5f,0.2f,0.2f,1.0f});

        RGBA compare = new RGBA();
        Log.d("RGBAtest", "compare pred:"+compare.toString());
//        Log.d("RGBAtest", "testovaci_barva po: "+testovaci_barva.toString());
        compare = testovaci_barva.modifyHSV(new float[]{0.0f,-1.0f,0.0f});
        Log.d("RGBAtest", "compare po:"+compare.toString());
    }
}
