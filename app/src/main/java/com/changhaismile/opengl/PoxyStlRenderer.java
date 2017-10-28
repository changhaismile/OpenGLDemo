package com.changhaismile.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.Log;

import com.changhaismile.opengl.model.Model;
import com.changhaismile.opengl.model.Point;
import com.changhaismile.opengl.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author changhaismile
 * @name PoxyStlRenderer
 * @comment //TODO
 * @date 2017/10/27
 */

public class PoxyStlRenderer implements GLSurfaceView.Renderer{
    private Context context;
    private Point mCenterPoint;
    private Point eye = new Point(0, 0, -10);
    private Point up = new Point(0, -1, 0);
    private Point center = new Point(0, 0, 0);
    private float mScalef = 1;
    private float mDegree = 0;
    private List<Model> models = new ArrayList<>();

    public PoxyStlRenderer(Context context) {
        this.context = context;
        try {
            STLReader reader = new STLReader();
            for (int i = 1; i < 6; i ++) {
                Model model = reader.parserStlWithTextureInAssets(context, "nvshen/" + i);
                models.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rotate(float degree) {
        mDegree = degree;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST); // 启用深度缓存
        gl.glClearColor(0f, 0f, 0f, 0f);// 设置深度缓存值
        gl.glDepthFunc(GL10.GL_LEQUAL); // 设置深度缓存比较函数
        gl.glShadeModel(GL10.GL_SMOOTH);// 设置阴影模式GL_SMOOTH
        //初始化相关数据
        initConfigData(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(width, height)指定了视口的大小
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION); // 设置投影矩阵
        gl.glLoadIdentity(); // 设置矩阵为单位矩阵，相当于重置矩阵
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);// 设置透视范围

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();// 重置当前的模型观察矩阵
        //眼睛对着原点看
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);

        //为了能有立体感觉，通过改变mDegree值，让模型不断旋转
        gl.glRotatef(mDegree, 0, 1, 0);

        //将模型放缩到View刚好装下
        gl.glScalef(mScalef, mScalef, mScalef);
        //把模型移动到原点
        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y,
                -mCenterPoint.z);
        for (Model model : models) {
            //开启贴纹理功能
            gl.glEnable(GL10.GL_TEXTURE_2D);
            //根据ID绑定对应的纹理
            gl.glBindTexture(GL10.GL_TEXTURE_2D, model.getTextureIds()[0]);
            //启用相关功能
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            //开始绘制
            gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, model.getTextureBuffer());

            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFacetCount() * 3);

            //关闭当前模型贴纹理，即将纹理id设置为0
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

            //关闭对应的功能
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }
    }

    /***
     * 初始化相关数据
     * @param gl
     */
    private void initConfigData(GL10 gl) {
        float r = Utils.getR(models);
        mScalef = 0.5f / r;
        mCenterPoint = Utils.getCenter(models);

        //为每个模型绑定纹理
        for (Model model : models) {
            loadTexture(gl, model, true);
        }
    }

    private void loadTexture(GL10 gl, Model model, boolean isAssets) {
        Log.d("GLRenderer", "绑定纹理：" + model.getPictureName());
        Bitmap bitmap = null;
        try {
            //打开图片资源
            if (isAssets) {
                bitmap = BitmapFactory.decodeStream(context.getAssets().open(model.getPictureName()));
            } else {
                bitmap = BitmapFactory.decodeFile(model.getPictureName());
            }

            //生成一个纹理对象，并将其ID 保存到成员变量 texture中
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            model.setTextureIds(textures);

            //将生成的空纹理绑定到当前2d纹理通道
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

            //设置2D纹理通道当前绑定的纹理属性
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);

            //将bitmap应用到2D纹理通道当前绑定的纹理中
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    public void setScale(float scale) {
        mScalef = scale;
    }
}
