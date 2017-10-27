package com.changhaismile.opengl;

import android.opengl.GLSurfaceView;

import com.changhaismile.opengl.utils.Utils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author changhaismile
 * @name GLRenderer
 * @comment //TODO
 * @date 2017/10/26
 */

public class GLRenderer implements GLSurfaceView.Renderer {
    /**三角形各顶点*/
    private float[] mTriangleArray = {
            0f, 1f, 0f,
            -1f, -1f, 0f,
            1f, -1f, 0f
    };
    /**三角形各顶点颜色值(三个顶点)*/
    private float[] mColor = {
            1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1
    };
    private FloatBuffer mTriangleBuffer;
    private FloatBuffer mColorBuffer;

    public GLRenderer() {
        mTriangleBuffer = Utils.getFloatBuff(mTriangleArray);
        mColorBuffer = Utils.getFloatBuff(mColor);
    }

    /***
     * surface 创建时候调用
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置白色为清屏颜色
        gl.glClearColor(1, 1, 1, 1);
    }

    /***
     * surface 改变的时候调用
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float ratio = (float) width / height;
        //设置OpenGL 场景的大小，（0，0）标识窗口内部视图的左下角，（w，h）指定了视图的大小
        gl.glViewport(0, 0, width, height);
        //设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //重置投影矩阵
        gl.glLoadIdentity();
        //设置视图大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        //一下两句声明，以后所有的变换都是针对模型（即我们绘制的图形）
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /***
     * surface 绘制的时候调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        //清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //重置当前的模型观察矩阵
        gl.glLoadIdentity();

        //允许设置顶点
        //GL10.GL_VERTEX_ARRAY 顶点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //允许设置颜色
        //GL10.GL_COLOR_ARRAY 颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        //将三角形在z轴上移动
        gl.glTranslatef(0f, 0.0f, -2.0f);

        //设置三角形
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);
        //设置三角形颜色
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
        //绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        //取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //取消颜色设置
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        //绘制结束
        gl.glFinish();
    }
}
