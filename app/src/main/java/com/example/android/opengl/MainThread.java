package com.example.android.opengl;

import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by MAREK on 16.2.2016.
 */
public class MainThread extends Thread {
    // flag to hold game state
    private static final String TAG = MainThread.class.getSimpleName();
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long tickCount = 0L;
        Log.d(TAG, "Starting game loop");
        while (running) {
            tickCount++;
            // update game state
            // render state to the screen
        }
        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }


    private SurfaceHolder surfaceHolder;
    private MyGLSurfaceView gamePanel;

    public MainThread(SurfaceHolder surfaceHolder, MyGLSurfaceView gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

}
