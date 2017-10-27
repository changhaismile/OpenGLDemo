package com.changhaismile.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import com.changhaismile.opengl.model.Model;
import com.changhaismile.opengl.model.Point;
import com.changhaismile.opengl.utils.Utils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author changhaismile
 * @name StlRenderer
 * @comment //TODO
 * @date 2017/10/26
 */

public class StlRenderer implements GLSurfaceView.Renderer {
    private Model model;
    private Point mCenterPoint;
    private Point eye = new Point(0, 0, -3);
    private Point up = new Point(0, 1, 0);
    private Point center = new Point(0, 0, 0);
    private float mScalef = 1;
    private float mDegree = 0;

    public StlRenderer(Context context) {
        try {
            model = new STLReader().parserBinStlInAssets(context, "huba.stl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rotate(float degree) {
        mDegree = degree;
    }

    /***
     * surface 创建的时候调用
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //启用深度缓存
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度缓存值
        gl.glClearDepthf(1.0f);
        //设置深度缓存比较函数
        gl.glDepthFunc(GL10.GL_LEQUAL);
        //设置阴影模式GL_SMOOTH
        gl.glShadeModel(GL10.GL_SMOOTH);

        //开启光照
        openLight(gl);
        enableMaterial(gl);

        float r = model.getR();
        //r是半径，不是直径，因此用0.5/r可以酸楚放缩比例
        mScalef = 0.5f / r;
        mCenterPoint = model.getCentrePoint();
    }

    /**
     * surface 改变的时候调用
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置OpenGL 场景的大小
        gl.glViewport(0, 0, width, height);

        //设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //设置矩阵为单位矩阵，相当于重置矩阵
        gl.glLoadIdentity();
        //设置透视范围
        GLU.gluPerspective(gl, 45.0f, ((float)width) / height, 1f, 100f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    /***
     * surface 绘制的时候调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        //设置屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        //眼睛对着原点看
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z
                , center.x, center.y, center.z
                , up.x, up.y, up.z);
        //为了能有立体的感觉，通过改变mDegree值，让模型不断旋转
        gl.glRotatef(mDegree, 0, 1, 0);
        //将模型放缩到view刚好装下
        gl.glScalef(mScalef, mScalef, mScalef);
        //模型移动到原点
        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y, -mCenterPoint.z);

        //允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //允许给每个顶点设置法向量
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        //设置法向量数据圆
        gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
        //设置三角形顶点数据源
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());
        //绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFacetCount() * 3);
        //取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //取消法向量设置
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    float[] ambient = {0.9f, 0.9f, 0.9f, 1.0f};
    float[] diffuse = {0.5f, 0.5f, 0.5f, 1.0f};
    float[] specular = {1.0f, 1.0f, 1.0f, 1.0f};
    float[] lightPosition = {0.5f, 0.5f, 0.5f, 0.0f};

    public void openLight(GL10 gl) {
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(GL10.GL_LIGHT0);
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, Utils.getFloatBuff(ambient));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, Utils.getFloatBuff(diffuse));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, Utils.getFloatBuff(specular));
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, Utils.getFloatBuff(lightPosition));
    }

    float[] materialAmb = {0.4f, 0.4f, 1.0f, 1.0f,};
    float[] materialDiff = {0.0f, 0.0f, 1.0f, 1.0f,};
    float[] materialSpec = {1.0f, 0.5f, 0.0f, 1.0f,};

    public void enableMaterial(GL10 gl) {
        //材料对环境光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, Utils.getFloatBuff(materialAmb));
        //散射光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, Utils.getFloatBuff(materialDiff));
        //镜面光的反射情况
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, Utils.getFloatBuff(materialSpec));
    }
}
