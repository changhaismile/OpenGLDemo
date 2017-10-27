package com.changhaismile.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author changhaismile
 * @name STLOpenGlSurfaceViewActivity
 * @comment //TODO
 * @date 2017/10/26
 */

public class STLOpenGlSurfaceViewActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private StlRenderer renderer;
    private float rotateDegree = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            rotate(rotateDegree);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSupported()) {
            glSurfaceView = new GLSurfaceView(this);
            renderer = new StlRenderer(this);
            glSurfaceView.setRenderer(renderer);
            setContentView(glSurfaceView);
        }
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

    private void rotate(float degree) {
        renderer.rotate(degree);
        glSurfaceView.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();

            //不断改变rotateDegree值，实现旋转
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
