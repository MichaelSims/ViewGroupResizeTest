package com.example;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
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

    private static final String TAG = Util.getLoggingTag(MyActivity.class);
    private FrameLayout resizeableFrame;
    private RelativeLayout viewContainer;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initializeViews();


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

    private static class MyFrameLayout extends FrameLayout {

        private static final String TAG = Util.getLoggingTag(MyFrameLayout.class);

        public MyFrameLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Log.d(TAG, "onSizeChanged");
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, "onLayout");
        }
    }
}
