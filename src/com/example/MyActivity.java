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
    private static final int SIZED_VIEW_HEIGHT = 60;
    private MyFrameLayout resizeableFrame; //programMapContainer
    private RelativeLayout viewContainer; //programMap

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);

        resizeableFrame = (MyFrameLayout) findViewById(R.id.resizableFrame);
        viewContainer = (RelativeLayout) findViewById(R.id.sizedViewContainer);
        resizeableFrame.setSizedViewContainer(viewContainer);
    }

    private static class MyFrameLayout extends FrameLayout {

        private static final String TAG = Util.getLoggingTag(MyFrameLayout.class);
        private ViewGroup sizedViewContainer;
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
                sizedViewContainer.addView(getSizedView(50, "left"));
            }
        }

        private RelativeLayout getSizedView(int leftMargin, String label) { //programItem
            RelativeLayout sizedView = getSizedView();
            setLayoutParams(sizedView, leftMargin);
            buildTextView(sizedView, label);
            return sizedView;
        }

        private void buildTextView(RelativeLayout sizedView, String label) {
            HolderForSizedView holder = new HolderForSizedView();
            holder.textView = (TextView) sizedView.findViewById(R.id.textView);
            sizedView.setTag(holder);
            holder.textView.setText(label);
        }

        private void setLayoutParams(RelativeLayout sizedView, int leftMargin) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getWidthForSizedView(), SIZED_VIEW_HEIGHT);
            layoutParams.setMargins(leftMargin, getTopMarginForSizedView(), 0, 0);
            sizedView.setLayoutParams(layoutParams);
        }

        private int getTopMarginForSizedView() {
            return Math.round((getHeight() - SIZED_VIEW_HEIGHT) / 2);
        }

        private int getWidthForSizedView() {
            return Math.round(getWidth() / 3);
        }

        private RelativeLayout getSizedView() {
            return (RelativeLayout) inflater.inflate(R.layout.sized_view, null);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, "onLayout");
        }

        public void setSizedViewContainer(ViewGroup sizedViewContainer) {
            this.sizedViewContainer = sizedViewContainer;
        }

        private static class HolderForSizedView {
            TextView textView;
        }

    }
}
