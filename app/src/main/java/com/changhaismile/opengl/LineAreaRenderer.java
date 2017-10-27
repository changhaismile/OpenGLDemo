package com.changhaismile.opengl;

import android.opengl.GLSurfaceView;

import com.changhaismile.opengl.utils.Utils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author changhaismile
 * @name LineAreaRenderer
 * @comment //TODO
 * @date 2017/10/25
 */

public class LineAreaRenderer implements GLSurfaceView.Renderer{
    private FloatBuffer buffer;
    //顶点数组
    private float[] mArray = {

            -0.6f , 0.6f , 0f,

            -0.2f , 0f , 0f ,

            0.2f , 0.6f , 0f ,

            0.6f , 0f , 0f

    };

    public LineAreaRenderer() {
        buffer = Utils.getFloatBuff(mArray);
    }
    /***
     * surface 创建的时候调用
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
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
        gl.glViewport(0, 0, width, height);
    }

    /***
     * 在surface view上绘制的时候调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        //清除屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        //允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //设置顶点
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, buffer);
        //设置点的颜色为绿色
        gl.glColor4f(0f, 1f, 0f, 0f);
        //设置点的大小
        gl.glPointSize(40f);
        //绘制点
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 4);
        //取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
