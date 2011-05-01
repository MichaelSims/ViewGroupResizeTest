package com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MyActivity extends Activity {

    private static final String TAG = Util.getLoggingTag(MyActivity.class);
    private static final int SIZED_VIEW_HEIGHT = 60;
    private static final int FAT_WIDTH = 300;
    private static final int SKINNY_WIDTH = 200;
    private MyFrameLayout resizeableFrame; //programMapContainer
    private RelativeLayout viewContainer; //programMap
    private boolean isSkinny = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);

        resizeableFrame = (MyFrameLayout) findViewById(R.id.resizableFrame);
        viewContainer = (RelativeLayout) findViewById(R.id.sizedViewContainer);
        resizeableFrame.setSizedViewContainer(viewContainer);
        Button resizeButton = (Button) findViewById(R.id.resizeButton);
        resizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                resizeableFrame.getLayoutParams().width = isSkinny ? FAT_WIDTH : SKINNY_WIDTH;
                isSkinny = !isSkinny;
                view.requestLayout();
            }
        });
    }

    private static class MyFrameLayout extends FrameLayout {

        private static final String TAG = Util.getLoggingTag(MyFrameLayout.class);
        private static final int SIZED_VIEW_MARGIN = 30;
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
                resetSizedViews();
            }
        }

        private void resetSizedViews() {
            sizedViewContainer.removeAllViews();
            sizedViewContainer.addView(getSizedView(SIZED_VIEW_MARGIN, "left"));
            sizedViewContainer.addView(getSizedView(getWidth() - getWidthForSizedView() - SIZED_VIEW_MARGIN, "right"));
        }

        private RelativeLayout getSizedView(int leftMargin, String label) { //programItem
            RelativeLayout sizedView = (RelativeLayout) inflater.inflate(R.layout.sized_view, null);

            /* Set layout params */
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getWidthForSizedView(), SIZED_VIEW_HEIGHT);
            layoutParams.setMargins(leftMargin, getTopMarginForSizedView(), 0, 0);
            sizedView.setLayoutParams(layoutParams);

            /* Set text view label */
            ((TextView) sizedView.findViewById(R.id.textView)).setText(label);

            return sizedView;
        }

        private int getTopMarginForSizedView() {
            return Math.round((getHeight() - SIZED_VIEW_HEIGHT) / 2);
        }

        private int getWidthForSizedView() {
            return Math.round(getWidth() / 3);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, "onLayout");
        }

        public void setSizedViewContainer(ViewGroup sizedViewContainer) {
            this.sizedViewContainer = sizedViewContainer;
        }

    }
}
