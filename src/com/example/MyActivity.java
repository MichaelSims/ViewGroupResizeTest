package com.example;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyActivity extends Activity {

    private FrameLayout resizeableFrame;
    private RelativeLayout viewContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initializeViews();

        /* Set a static size of the resizeable frame */
        ViewGroup.LayoutParams params = resizeableFrame.getLayoutParams();
        params.width = 300;
        params.height = 300;

    }

    private void initializeViews() {
        resizeableFrame = (FrameLayout) findViewById(R.id.resizableFrame);
        viewContainer = (RelativeLayout) findViewById(R.id.manuallySizedViewContainer);
    }

}
