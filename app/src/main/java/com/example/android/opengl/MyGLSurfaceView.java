/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */

//sem narveme hlavni loop
public class MyGLSurfaceView extends GLSurfaceView implements SurfaceHolder.Callback{
    private static final String TAG = MyGLSurfaceView.class.getSimpleName();
    private final MyGLRenderer mRenderer;
    private MainThread main_thread;





    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
        main_thread = new MainThread(getHolder(), this);
    }

    /*public MyGLSurfaceView(Context context, AttributeSet attribs) {
        super(context, attribs);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
        main_thread = new MainThread(getHolder(), this);

    }*/

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        main_thread.setRunning(true);
        main_thread.start();
        super.surfaceCreated(holder);
        Log.d(TAG,"Created surface, dimensions - x:"+this.getWidth()+ " y:"+this.getHeight());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                main_thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        super.surfaceDestroyed(holder);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        /*switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "action down");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.d(TAG, "action pointer down");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                Log.d(TAG, "action pointer up");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "action move");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "action up");
                break;

        }

        float x = event.getX();
        float y = event.getY();

        Log.d(TAG, "x:"+x+" y:"+y);


        return true;*/

        /*float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                        ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;*/

      /*  if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() > getHeight() - 50) {
                main_thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                requestRender();
            }
        }
        return super.onTouchEvent(event);*/

        int dimx = this.getHeight();
        int dimy = this.getWidth();
        float ratio = (float)dimx/(float)dimy;
        MoveDir left_pad_dir=MoveDir.INVALID,right_pad_dir = MoveDir.INVALID;

        if (event.getAction()==MotionEvent.ACTION_UP) {
            //reset all buttons
//            mRenderer.left_keypad.pressAllArrows(false);
//            mRenderer.right_keypad.pressAllArrows(false);
        }
        else {
            int count=event.getPointerCount();
            float vx1=0.0f,vy1=0.0f,vx2=0.0f, vy2=0.0f;
            float glx1 = 0.0f, glx2 = 0.0f, gly1 = 0.0f, gly2 = 0.0f;

            if (count>=1) {
                vx1 = event.getX(0)/dimx;
                vy1 = event.getY(0)/dimy;
                glx1 = (vx1-0.5f)*2*ratio;
                gly1 = (vy1-0.5f)*2;
            }
            if (count>=2) {
                vx2 = event.getX(1)/dimx;
                vy2 = event.getY(1)/dimy;
                glx2 = (vx2-0.5f)*2*ratio;
                gly2 = (vy2-0.5f)*2;
            }
            Log.d(TAG, "Vx1: "+vx1+ " Vy1:"+vy1+" GLX1:"+glx1+" GLY1:"+gly1);
            Log.d(TAG, "Vx2: "+vx2+ " Vy2:"+vy2+" GLX2:"+glx2+" GLY2:"+gly2);

            MoveDir tmp_dir1 = mRenderer.left_keypad.onPress(glx1,gly1);
            MoveDir tmp_dir2 = mRenderer.right_keypad.onPress(glx1,gly1);

            MoveDir tmp_dir3 = mRenderer.left_keypad.onPress(glx2,gly2);
            MoveDir tmp_dir4 = mRenderer.right_keypad.onPress(glx2,gly2);



            if (tmp_dir1!=MoveDir.INVALID) left_pad_dir = tmp_dir1;
            if (tmp_dir3!=MoveDir.INVALID) left_pad_dir = tmp_dir3;

            if (tmp_dir2!=MoveDir.INVALID) right_pad_dir = tmp_dir2;
            if (tmp_dir4!=MoveDir.INVALID) right_pad_dir = tmp_dir4;

//            Log.d(TAG, "Left pad:"+left_pad_dir.toString()+ "Right pad:"+right_pad_dir.toString());

            if (left_pad_dir!=MoveDir.INVALID) mRenderer.left_keypad.pressArrow(left_pad_dir,true);
            if (right_pad_dir!=MoveDir.INVALID) mRenderer.right_keypad.pressArrow(right_pad_dir,true);
        }

        return true;

    }

}
