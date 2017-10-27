package com.changhaismile.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author changhaismile
 * @name LineAreaGlSurfaceViewActivity
 * @comment //TODO
 * @date 2017/10/25
 */

public class LineAreaGlSurfaceViewActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSupported()) {
            glSurfaceView = new GLSurfaceView(this);
            GLRenderer renderer = new GLRenderer();
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
}
