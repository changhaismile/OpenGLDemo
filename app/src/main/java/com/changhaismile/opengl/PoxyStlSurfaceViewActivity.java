package com.changhaismile.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

/**
 * @author changhaismile
 * @name PoxyStlSurfaceViewActivity
 * @comment //TODO
 * @date 2017/10/27
 */

public class PoxyStlSurfaceViewActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private PoxyStlRenderer renderer;
    private SeekBar seekBar;
    private float rotateDegree;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poxy_stl);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        if (isSupported()) {
            glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_surfaceview);
            renderer = new PoxyStlRenderer(this);
            glSurfaceView.setRenderer(renderer);
        }
        seekBar.setMax(100);
        seekBar.setProgress(80);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                renderer.setScale(1f * progress / 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * 是否支持OpenGL2
     * @return
     */
    private boolean isSupported() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo info = manager.getDeviceConfigurationInfo();
        boolean isSupportES2 = info.reqGlEsVersion >= 0x2000;
        return isSupportES2;
    }

    public void rotate(float degree) {
        renderer.rotate(degree);
        glSurfaceView.invalidate();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            rotate(rotateDegree);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        renderer.setScale(0.8f);
        if (glSurfaceView != null) {
            glSurfaceView.onResume();

            //不断改变rotateDegreen值，实现旋转
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            sleep(100);
                            rotateDegree += 5;
                            handler.sendEmptyMessage(0x001);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }
}
