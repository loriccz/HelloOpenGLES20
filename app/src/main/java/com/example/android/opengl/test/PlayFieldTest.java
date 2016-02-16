package com.example.android.opengl.test;

import com.example.android.opengl.FieldStoneType;
import com.example.android.opengl.PlayField;

import junit.framework.TestCase;

/**
 * Created by MAREK on 16.2.2016.
 */
public class PlayFieldTest extends TestCase {
    private PlayField pf;

    public PlayFieldTest() {
        try {
            setUp();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testFree() throws Exception {
        for (int y=0; y<pf.getRowCount(); y++) {
            for (int x=0; x<pf.getColumnCount(); x++) {
                assertTrue(pf.typeOn(x,y)== FieldStoneType.FREE);
            }
        }

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        pf = new PlayField();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

}
