package com.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyActivity extends Activity {

    private FrameLayout resizeableFrame;
    private RelativeLayout viewContainer;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initializeViews();

        /* Set a static size of the resizeable frame */
        ViewGroup.LayoutParams params = resizeableFrame.getLayoutParams();
        params.width = 300;
        params.height = 300;

        /* Build our manually sized views */
        viewContainer.addView(getLeftView());

    }

    private RelativeLayout getLeftView() {
        RelativeLayout manuallySizedView = (RelativeLayout) inflater.inflate(R.layout.manually_sized_view, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        layoutParams.setMargins(50, 50, 0, 0);
        manuallySizedView.setLayoutParams(layoutParams);
        HolderForManuallySizedView holder = new HolderForManuallySizedView();
        holder.textView = (TextView) manuallySizedView.findViewById(R.id.textView);
        manuallySizedView.setTag(holder);
        holder.textView.setText("left");
        return manuallySizedView;
    }

    private void initializeViews() {
        resizeableFrame = (FrameLayout) findViewById(R.id.resizableFrame);
        viewContainer = (RelativeLayout) findViewById(R.id.manuallySizedViewContainer);
    }

    private static class HolderForManuallySizedView {
        TextView textView;
    }
}
