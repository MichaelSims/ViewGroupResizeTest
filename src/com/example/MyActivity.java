package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class MyActivity extends Activity {

    private FrameLayout resizeableFrame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        resizeableFrame = (FrameLayout) findViewById(R.id.resizableFrame);

        ViewGroup.LayoutParams params = resizeableFrame.getLayoutParams();
        params.width = 300;
        params.height = 300;
    }

}
