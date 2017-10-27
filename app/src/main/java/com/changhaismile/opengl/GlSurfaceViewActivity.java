package com.changhaismile.opengl;

import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * @author changhaismile
 * @name GlSurfaceViewActivity
 * @comment //TODO
 * @date 2017/10/24
 */

public class GlSurfaceViewActivity extends AppCompatActivity {
    public static final String TAG = GlSurfaceViewActivity.class.getSimpleName();

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果本设备支持OpenGL ES 2.0
        if (isSupported()) {
            //新建GLSurfaceView实例
            glSurfaceView = new GLSurfaceView(this);
            //创建渲染器实例
            MyRenderer renderer = new MyRenderer(glSurfaceView);
            //设置渲染器
            glSurfaceView.setRenderer(renderer);
            //显示SurfaceView
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

    @Override
    protected void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }
}
