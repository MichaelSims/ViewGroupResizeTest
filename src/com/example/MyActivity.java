package com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyActivity extends Activity {

    private static final String TAG = Util.getLoggingTag(MyActivity.class);
    private static final int MANUALLY_SIZED_VIEW_HEIGHT = 60;
    private MyFrameLayout resizeableFrame; //programMapContainer
    private RelativeLayout viewContainer; //programMap

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);

        resizeableFrame = (MyFrameLayout) findViewById(R.id.resizableFrame);
        viewContainer = (RelativeLayout) findViewById(R.id.manuallySizedViewContainer);
        resizeableFrame.setManuallySizedViewContainer(viewContainer);
    }

    private static class MyFrameLayout extends FrameLayout {

        private static final String TAG = Util.getLoggingTag(MyFrameLayout.class);
        private ViewGroup manuallySizedViewContainer;
        private LayoutInflater inflater;

        public MyFrameLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Log.d(TAG, "onSizeChanged");
            if (h != 0 && w != 0) {
                manuallySizedViewContainer.addView(getLeftView());
            }
        }

        private RelativeLayout getLeftView() { //programItem
            RelativeLayout manuallySizedView = createManuallySizedView();

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getManuallySizedViewWidth(), MANUALLY_SIZED_VIEW_HEIGHT);
            layoutParams.setMargins(50, 50, 0, 0);
            manuallySizedView.setLayoutParams(layoutParams);

            HolderForManuallySizedView holder = new HolderForManuallySizedView();
            holder.textView = (TextView) manuallySizedView.findViewById(R.id.textView);
            manuallySizedView.setTag(holder);
            holder.textView.setText("left");
            return manuallySizedView;
        }

        private int getManuallySizedViewWidth() {
            return Math.round(getWidth() / 3);
        }

        private RelativeLayout createManuallySizedView() {
            return (RelativeLayout) inflater.inflate(R.layout.manually_sized_view, null);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, "onLayout");
        }

        public void setManuallySizedViewContainer(ViewGroup manuallySizedViewContainer) {
            this.manuallySizedViewContainer = manuallySizedViewContainer;
        }

        private static class HolderForManuallySizedView {
            TextView textView;
        }

    }
}
