package com.changhaismile.opengl;

import android.opengl.GLSurfaceView;
import android.util.Log;

import com.changhaismile.opengl.utils.Utils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author changhaismile
 * @name DotMyRenderer
 * @comment //TODO
 * @date 2017/10/24
 */

public class DotMyRenderer implements GLSurfaceView.Renderer{
    private float[] mArray = {0f, 0f, 0f};

    /**缓冲区*/
    private FloatBuffer mBuffer;

    public DotMyRenderer() {
        //获取浮点型缓冲数据
        mBuffer = Utils.getFloatBuff(mArray);
    }

    /***
     * surface 创建的时候调用
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(GlSurfaceViewActivity.TAG, "onSurfaceCreated");
        //设置清屏颜色为黑色
        gl.glClearColor(0f, 0f, 0f, 0f);
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
        //设置窗口大小
        gl.glViewport(width / 4, width / 2, width / 2, height / 2);
    }

    /***
     * surface 绘制的时候调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i(GlSurfaceViewActivity.TAG, "onDrawFrame");
        //清除屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        //允许设置顶点  GL10.GL_VERTEX_ARRAY 顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //设置顶点
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mBuffer);
        //设置点的颜色为绿色
        gl.glColor4f(0f, 1f, 0f, 0f);
        //设置点的大小
        gl.glPointSize(80f);
        //绘制点
        gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
        //禁止顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
