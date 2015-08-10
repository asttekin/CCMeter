package cn.cctech.ccmeter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;

public class Meter extends RelativeLayout {

    private float mStartAngle = 0;
    private float mEndAngle = 360;
    private int mMax = 360;
    private static final int MSG_UPDATE_METER = 555;
    private static final int METER_UPDATE_INTERVAL = 10;

    private ImageView mBackgroundImage;
    private ImageView mNeedleImage;
    private UpdateHandler mHandler;
    private CustomInterpolator mInterpolator;

    public Meter(Context context) {
        super(context);
        init(context);
    }

    public Meter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        removeAllViews();

        mBackgroundImage = new ImageView(context);
        mBackgroundImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ViewGroup.LayoutParams backgroundImageLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mBackgroundImage, backgroundImageLp);

        mNeedleImage = new ImageView(context);
        mNeedleImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        ViewGroup.LayoutParams needleImageLp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mNeedleImage, needleImageLp);

        mHandler = new UpdateHandler(this);
    }

    public void setMeterBackground(@DrawableRes int id) {
        mBackgroundImage.setImageResource(id);
    }

    public void setMeterNeedle(@DrawableRes int id) {
        mNeedleImage.setImageResource(id);
    }

    public void setMaxValue(int max) {
        if (max < 0) return;
        mMax = max;
    }

    public void setAngleRange(float startAngle, float endAngle) {
        if (startAngle < 0 || endAngle > 360 || startAngle >= endAngle) return;
        mStartAngle = startAngle;
        mEndAngle = endAngle;
    }

    public void setValue(int value) {
        if (value < 0) return;

        float rotation = (mEndAngle - mStartAngle) / mMax * value + mStartAngle;
        if (rotation >= mStartAngle && rotation <= mEndAngle) {
            mHandler.removeMessages(MSG_UPDATE_METER);
            float current = mNeedleImage.getRotation();
            float diff = Math.abs(rotation - current);
            float step = 1 / diff;
            step *= Math.pow(2, diff / 90) / 1.5;
            mInterpolator = new CustomInterpolator(current, rotation, step);
            mHandler.sendEmptyMessage(MSG_UPDATE_METER);
        }
    }

    private void rotateNeedle(float angle) {
        mNeedleImage.setRotation(angle);
    }

    private static class UpdateHandler extends Handler {

        private WeakReference<Meter> mContext;

        public UpdateHandler(Meter context) {
            mContext = new WeakReference<Meter>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_METER:
                    float x = mContext.get().mInterpolator.getX();
                    float f = mContext.get().mInterpolator.getInterpolation();
                    mContext.get().rotateNeedle(f);
                    if (x >= 1) return;
                    sendEmptyMessageDelayed(MSG_UPDATE_METER, METER_UPDATE_INTERVAL);
                    break;
            }
        }
    }

    public class CustomInterpolator {

        private float mCurrentAngle;
        private float mTargetAngle;
        private float mStep;
        private float x;

        public CustomInterpolator(float currentAngle, float targetAngle, float step) {
            mCurrentAngle = currentAngle;
            mTargetAngle = targetAngle;
            mStep = step;
        }

        public float getInterpolation() {
            double ratio = (Math.cos((x + 1) * Math.PI) / 2.0f) + 0.5f;
            x += mStep;
            x = Math.min(x, 1.0f);
            return (float) ((mTargetAngle - mCurrentAngle) * ratio + mCurrentAngle);
        }

        public float getX() {
            return x;
        }
    }

}
