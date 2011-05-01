package com.example;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = Util.getLoggingTag(MainActivity.class);
    private ResizeableFrame resizeableFrame; //programMapContainer

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.main);

        resizeableFrame = (ResizeableFrame) findViewById(R.id.resizableFrame);
        Button resizeButton = (Button) findViewById(R.id.resizeButton);
        resizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                resizeableFrame.toggleFatSkinny();
            }
        });
    }

    private static class ResizeableFrame extends FrameLayout {

        private static final String TAG = Util.getLoggingTag(ResizeableFrame.class);

        private static final int SIZED_VIEW_MARGIN = 30;
        private static final int SIZED_VIEW_HEIGHT = 60;
        private RelativeLayout sizedViewContainer; //programMap

        private LayoutInflater inflater;
        
        private boolean isSkinny = false;
        private static final int FAT_WIDTH = 300;
        private static final int SKINNY_WIDTH = 200;
        private RelativeLayout leftSizedView;
        private RelativeLayout rightSizedView;

        public ResizeableFrame(Context context, AttributeSet attrs) {
            super(context, attrs);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            sizedViewContainer = (RelativeLayout) findViewById(R.id.sizedViewContainer);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Log.d(TAG, String.format("onSizeChanged w:%s h:%s oldw:%s oldh:%s", w, h, oldw, oldh));
            if (oldw == 0) {
                initializeSizedViews();
            }
        }

        private void initializeSizedViews() {
            leftSizedView = getSizedView(SIZED_VIEW_MARGIN, "left");
            rightSizedView = getSizedView(getWidth() - getWidthForSizedView() - SIZED_VIEW_MARGIN, "right");
            sizedViewContainer.addView(leftSizedView);
            sizedViewContainer.addView(rightSizedView);
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
            return Math.round((getHeight() - SIZED_VIEW_HEIGHT) / 2); //Vertically center the sized view inside the resizeable frame
        }

        private int getWidthForSizedView() {
            return Math.round(getWidth() / 3); //Make the sized view 1/3 the width of the resizeable frame
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, String.format("onLayout changed %s, left %s, top %s, right %s, bottom %s",
                    changed, left, top, right, bottom));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            Log.d(TAG, String.format("onMeasure width:%s width mode:%s height:%s height mode:%s",
                    MeasureSpec.getSize(widthMeasureSpec), Util.measureSpecModeToString(MeasureSpec.getMode(widthMeasureSpec)),
                    MeasureSpec.getSize(heightMeasureSpec), Util.measureSpecModeToString(MeasureSpec.getMode(heightMeasureSpec))));
        }

        public void toggleFatSkinny() {
            isSkinny = !isSkinny;
            getLayoutParams().width = isSkinny ? SKINNY_WIDTH : FAT_WIDTH;
            requestLayout();
        }

    }

    private static class SizedViewContainer extends RelativeLayout {
        public SizedViewContainer(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }
}
