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

        private SizedViewContainer sizedViewContainer; //programMap

        private boolean isSkinny = false;
        private static final int FAT_WIDTH = 300;
        private static final int SKINNY_WIDTH = 200;

        public ResizeableFrame(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            sizedViewContainer = (SizedViewContainer) findViewById(R.id.sizedViewContainer);
            sizedViewContainer.setResizeableFrame(this);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Log.d(TAG, String.format("onSizeChanged w:%s h:%s oldw:%s oldh:%s", w, h, oldw, oldh));
            if (oldw == 0) {
                sizedViewContainer.initializeSizedViews();
            }
        }

        public void toggleFatSkinny() {
            isSkinny = !isSkinny;
            getLayoutParams().width = isSkinny ? SKINNY_WIDTH : FAT_WIDTH;
            requestLayout();
        }

    }

    private static class SizedViewContainer extends RelativeLayout {

        private static final String TAG = Util.getLoggingTag(SizedViewContainer.class);

        private static final int SIZED_VIEW_MARGIN = 30;
        private static final int SIZED_VIEW_HEIGHT = 60;

        private LayoutInflater inflater;

        private ResizeableFrame resizeableFrame;

        private RelativeLayout leftSizedView;
        private RelativeLayout rightSizedView;

        public SizedViewContainer(Context context, AttributeSet attrs) {
            super(context, attrs);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        private void initializeSizedViews() {
            leftSizedView = createSizedView(SIZED_VIEW_MARGIN, "left");
            rightSizedView = createSizedView(resizeableFrame.getWidth() - getWidthForSizedView() - SIZED_VIEW_MARGIN, "right");
            addView(leftSizedView);
            addView(rightSizedView);
        }

        private RelativeLayout createSizedView(int leftMargin, String label) { //programItem
            RelativeLayout sizedView = (RelativeLayout) inflater.inflate(R.layout.sized_view, null);

            /* Set layout params */
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(getWidthForSizedView(), SIZED_VIEW_HEIGHT);
//            layoutParams.setMargins(leftMargin, getTopMarginForSizedView(), 0, 0);
//            sizedView.setLayoutParams(layoutParams);
//            Log.d(TAG, String.format("Created sized view with width %s, height %s, left margin %s, and top margin %s",
//                    layoutParams.width, layoutParams.height, layoutParams.leftMargin, layoutParams.topMargin));

            /* Set text view label */
            ((TextView) sizedView.findViewById(R.id.textView)).setText(label);

            return sizedView;
        }

        private int getTopMarginForSizedView() {
            return Math.round((resizeableFrame.getHeight() - SIZED_VIEW_HEIGHT) / 2); //Vertically center the sized view inside the resizeable frame
        }

        private int getWidthForSizedView() {
            return Math.round(resizeableFrame.getWidth() / 3); //Make the sized view 1/3 the width of the resizeable frame
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            Log.d(TAG, String.format("onLayout changed %s, left %s, top %s, right %s, bottom %s",
                    changed, left, top, right, bottom));
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            Log.d(TAG, String.format("onMeasure width:%s width mode:%s height:%s height mode:%s",
                    MeasureSpec.getSize(widthMeasureSpec), Util.measureSpecModeToString(MeasureSpec.getMode(widthMeasureSpec)),
                    MeasureSpec.getSize(heightMeasureSpec), Util.measureSpecModeToString(MeasureSpec.getMode(heightMeasureSpec))));

            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));

            /* Measure the child views */
            for (View view : new View[] { leftSizedView, rightSizedView }) {
                if (view != null) {
                    int widthSpec = MeasureSpec.makeMeasureSpec(Math.round(getMeasuredWidth() / 3), MeasureSpec.EXACTLY);
                    int heightSpec = MeasureSpec.makeMeasureSpec(SIZED_VIEW_HEIGHT, MeasureSpec.EXACTLY);
                    view.measure(widthSpec, heightSpec);
                    Log.d(TAG, String.format("view %s width %s, height %s", view, view.getMeasuredWidth(), view.getMeasuredHeight()));
                }
            }


//            measureChild();
//            getChildMeasureSpec();
//            getMeasuredWidth();
//            getMeasuredHeight();
//            leftSizedView.measure();
//            rightSizedView.measure();
            
            //TODO call measure on children
            
        }

        public void setResizeableFrame(ResizeableFrame resizeableFrame) {
            this.resizeableFrame = resizeableFrame;
        }
    }
}
