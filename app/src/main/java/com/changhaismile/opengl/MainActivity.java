package com.changhaismile.opengl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mGLSurfaceView;
    private TextView mDotGLSurfaceView;
    private TextView mLineAreaSurfaceView;
    private TextView mStlSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGLSurfaceView = (TextView) findViewById(R.id.tv_gl_surfaceview);
        mDotGLSurfaceView = (TextView) findViewById(R.id.tv_dot_gl_surfaceview);
        mLineAreaSurfaceView = (TextView) findViewById(R.id.tv_line_area_gl_surfaceview);
        mStlSurfaceView = (TextView) findViewById(R.id.tv_stl_gl_surfaceview);
        mGLSurfaceView.setOnClickListener(this);
        mDotGLSurfaceView.setOnClickListener(this);
        mLineAreaSurfaceView.setOnClickListener(this);
        mStlSurfaceView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_gl_surfaceview:
                startActivity(new Intent(this, GlSurfaceViewActivity.class));
                break;
            case R.id.tv_dot_gl_surfaceview:
                startActivity(new Intent(this, DotGlSurfaceViewActivity.class));
                break;
            case R.id.tv_line_area_gl_surfaceview:
                startActivity(new Intent(this, LineAreaGlSurfaceViewActivity.class));
                break;
            case R.id.tv_stl_gl_surfaceview:
                startActivity(new Intent(this, STLOpenGlSurfaceViewActivity.class));
                break;
        }
    }
}
