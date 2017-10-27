package com.changhaismile.opengl;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author changhaismile
 * @name MyRenderer
 * @comment //TODO
 * @date 2017/10/24
 */

public class MyRenderer implements GLSurfaceView.Renderer{
    private GLSurfaceView glSurfaceView;

    /***
     * 设置SurfaceView实例
     * @param glSurfaceView
     */
    public MyRenderer(GLSurfaceView glSurfaceView) {
        this.glSurfaceView = glSurfaceView;
    }

    /***
     * surface 创建的时候调用
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(GlSurfaceViewActivity.TAG, "onSurfaceCreated");

        //设置清屏颜色
        gl.glClearColor(0f, 1f, 0f, 0f);
    }

    /***
     * surface 改变的时候调用
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(GlSurfaceViewActivity.TAG, "onSurfaceChanged,x=" + width / 4 + ",y=" + width / 2 + ",width=" + width / 2 + ",height=" + height / 2);
        //设置窗口的大小
        gl.glViewport(width / 4, width / 2, width / 2, height / 2);
    }

    /***
     * surface 绘制时候调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i(GlSurfaceViewActivity.TAG, "onDrawFrame");
        //设置渲染模式
        if (glSurfaceView != null) {
            glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
        //清除屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
