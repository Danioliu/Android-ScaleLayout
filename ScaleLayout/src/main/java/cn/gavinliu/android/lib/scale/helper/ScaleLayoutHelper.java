package cn.gavinliu.android.lib.scale.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.gavinliu.android.lib.scale.R;
import cn.gavinliu.android.lib.scale.config.ScaleConfig;

/**
 * Created by GavinLiu on 2015-08-10
 */
public class ScaleLayoutHelper {

    private static final String TAG = "ScaleLayoutHelper";

    private final ViewGroup mHost;
    private final ScaleLayoutInfo mHostLayoutInfo;

    public interface ScaleLayoutParams {
        ScaleLayoutInfo getScaleLayoutInfo();
    }

    public ScaleLayoutHelper(ViewGroup host, ScaleLayoutInfo layoutInfo) {
        this.mHost = host;
        this.mHostLayoutInfo = layoutInfo;
    }

    public static ScaleLayoutHelper create(ViewGroup view, AttributeSet attrs) {
        return new ScaleLayoutHelper(view, getScaleLayoutInfo(view.getContext(), attrs));
    }

    public void adjustChildren(int widthMeasureSpec, int heightMeasureSpec) {
        int i = 0;
        for (int N = this.mHost.getChildCount(); i < N; ++i) {
            View view = this.mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();

            if (params instanceof ScaleLayoutParams) {
                ScaleLayoutInfo info = ((ScaleLayoutParams) params).getScaleLayoutInfo();
                if (ScaleConfig.getInstance().isDebug()) {
                    Log.d(TAG, "adjustChildren using " + info + " " + view);
                }
                if (info != null && mHostLayoutInfo != null) {
                    if (params instanceof ViewGroup.MarginLayoutParams) {
                        info.fillMarginLayoutParams(view, (ViewGroup.MarginLayoutParams) params);
                    } else {
                        info.fillLayoutParams(view, params);
                    }

                    info.fillView(view);
                }
            }
        }
    }

    public void adjustHost(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHostLayoutInfo != null) {
            ViewGroup.LayoutParams params = mHost.getLayoutParams();
            if (params instanceof ViewGroup.MarginLayoutParams) {
                mHostLayoutInfo.fillMarginLayoutParams(mHost, (ViewGroup.MarginLayoutParams) params);
            } else {
                mHostLayoutInfo.fillLayoutParams(mHost, params);
            }
        }
    }

    public void restoreOriginalParams() {
        int i = 0;

        for (int N = this.mHost.getChildCount(); i < N; ++i) {
            View view = this.mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();

            if (params instanceof ScaleLayoutParams) {
                ScaleLayoutInfo info = ((ScaleLayoutParams) params).getScaleLayoutInfo();
                if (ScaleConfig.getInstance().isDebug()) {
                    Log.d(TAG, "restoreOriginalParams using " + info);
                }

                if (info != null) {
                    if (params instanceof ViewGroup.MarginLayoutParams) {
                        info.restoreMarginLayoutParams((ViewGroup.MarginLayoutParams) params);
                    } else {
                        info.restoreLayoutParams(params);
                    }
                }
            }
        }

    }

    public static void fetchWidthAndHeight(ViewGroup.LayoutParams params, TypedArray array, int widthAttr, int heightAttr) {
        params.width = array.getLayoutDimension(widthAttr, 0);
        params.height = array.getLayoutDimension(heightAttr, 0);
    }

    public static ScaleLayoutInfo getScaleLayoutInfo(Context context, AttributeSet attrs) {
        ScaleLayoutInfo info = new ScaleLayoutInfo(context);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleLayout);

        int value = array.getInt(R.styleable.ScaleLayout_layout_scale_by, 0);
        if (value != 0) {
            info.scaleBy = value;
        }

        try {
            value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_width, 0);
            if (value != 0) {
                info.width = value;
            }
        } catch (Exception e) {
        }

        try {
            value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_height, 0);
            if (value != 0) {
                info.height = value;
            }
        } catch (Exception e) {
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_margin, 0);
        if (value != 0) {
            info.leftMargin = value;
            info.topMargin = value;
            info.rightMargin = value;
            info.bottomMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_marginLeft, 0);
        if (value != 0) {
            info.leftMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_marginTop, 0);
        if (value != 0) {
            info.topMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_marginRight, 0);
        if (value != 0) {
            info.rightMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_marginBottom, 0);
        if (value != 0) {
            info.bottomMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_marginStart, 0);
        if (value != 0) {
            info.startMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_layout_marginEnd, 0);
        if (value != 0) {
            info.endMargin = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_padding, 0);
        if (value != 0) {
            info.leftPadding = value;
            info.topPadding = value;
            info.rightPadding = value;
            info.bottomPadding = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_paddingLeft, 0);
        if (value != 0) {
            info.leftPadding = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_paddingTop, 0);
        if (value != 0) {
            info.topPadding = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_paddingRight, 0);
        if (value != 0) {
            info.rightPadding = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_paddingBottom, 0);
        if (value != 0) {
            info.bottomPadding = value;
        }

        value = array.getDimensionPixelSize(R.styleable.ScaleLayout_android_textSize, 0);
        if (value != 0) {
            info.textSize = value;
        }

        array.recycle();

        if (ScaleConfig.getInstance().isDebug()) {
            Log.d(TAG, "constructed: " + info);
        }
        return info;
    }

    public boolean handleMeasuredStateTooSmall() {
        boolean needsSecondMeasure = false;
        int i = 0;

        for (int N = this.mHost.getChildCount(); i < N; ++i) {
            View view = this.mHost.getChildAt(i);
            ViewGroup.LayoutParams params = view.getLayoutParams();

            if (params instanceof ScaleLayoutParams) {
                ScaleLayoutInfo info = ((ScaleLayoutParams) params).getScaleLayoutInfo();
                if (info != null) {
                    if (shouldHandleMeasuredWidthTooSmall(view, info)) {
                        needsSecondMeasure = true;
                        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }

                    if (shouldHandleMeasuredHeightTooSmall(view, info)) {
                        needsSecondMeasure = true;
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                }
            }
        }

        if (ScaleConfig.getInstance().isDebug()) {
            Log.d(TAG, "should trigger second measure pass: " + needsSecondMeasure);
        }

        return needsSecondMeasure;
    }

    private static boolean shouldHandleMeasuredWidthTooSmall(View view, ScaleLayoutInfo info) {
        int state = ViewCompat.getMeasuredWidthAndState(view) & ViewCompat.MEASURED_STATE_MASK;
        return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.width >= 0.0F && info.mPreservedParams.width == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    private static boolean shouldHandleMeasuredHeightTooSmall(View view, ScaleLayoutInfo info) {
        int state = ViewCompat.getMeasuredHeightAndState(view) & ViewCompat.MEASURED_STATE_MASK;
        return state == ViewCompat.MEASURED_STATE_TOO_SMALL && info.height >= 0.0F && info.mPreservedParams.height == ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public static class ScaleLayoutInfo {
        private int designWidth;
        private int designHeight;

        private int screenW;
        private int screenH;

        public int width = 0;
        public int height = 0;

        private int scaleBy = ScaleByWidth;
        private static final int ScaleByWidth = 0;
        private static final int ScaleByHeight = 1;

        private int leftMargin;
        private int topMargin;
        private int rightMargin;
        private int bottomMargin;
        private int startMargin;
        private int endMargin;

        private int leftPadding;
        private int topPadding;
        private int rightPadding;
        private int bottomPadding;

        private int textSize;

        final ViewGroup.MarginLayoutParams mPreservedParams = new ViewGroup.MarginLayoutParams(0, 0);

        public ScaleLayoutInfo(Context ctx) {
            designWidth = ScaleConfig.getInstance().getDesignWidth();
            designHeight = ScaleConfig.getInstance().getDesignHeight();

            screenW = ctx.getResources().getDisplayMetrics().widthPixels;
            screenH = ctx.getResources().getDisplayMetrics().heightPixels;
        }

        public void fillLayoutParams(View view, ViewGroup.LayoutParams params) {
            this.mPreservedParams.width = params.width;
            this.mPreservedParams.height = params.height;

            if (this.width > 0.0) {
                params.width = getRealPixelSize(this.width);
            }

            if (this.height > 0.0) {
                params.height = getRealPixelSize(this.height);
            }

            int paddingL = 0, paddingT = 0, paddingR = 0, paddingB = 0;

            if (this.leftPadding > 0.0) {
                paddingL = getRealPixelSize(this.leftPadding);
            }

            if (this.topPadding > 0.0) {
                paddingT = getRealPixelSize(this.topPadding);
            }

            if (this.rightPadding > 0.0) {
                paddingR = getRealPixelSize(this.rightPadding);
            }

            if (this.bottomPadding > 0.0) {
                paddingB = getRealPixelSize(this.bottomPadding);
            }

            view.setPadding(paddingL, paddingT, paddingR, paddingB);
        }

        public void fillMarginLayoutParams(View view, ViewGroup.MarginLayoutParams params) {
            this.fillLayoutParams(view, params);
            this.mPreservedParams.leftMargin = params.leftMargin;
            this.mPreservedParams.topMargin = params.topMargin;
            this.mPreservedParams.rightMargin = params.rightMargin;
            this.mPreservedParams.bottomMargin = params.bottomMargin;
            MarginLayoutParamsCompat.setMarginStart(this.mPreservedParams, MarginLayoutParamsCompat.getMarginStart(params));
            MarginLayoutParamsCompat.setMarginEnd(this.mPreservedParams, MarginLayoutParamsCompat.getMarginEnd(params));

            if (this.leftMargin > 0.0F) {
                params.leftMargin = getRealPixelSize(this.leftMargin);
            }

            if (this.topMargin > 0.0F) {
                params.topMargin = getRealPixelSize(this.topMargin);
            }

            if (this.rightMargin > 0.0F) {
                params.rightMargin = getRealPixelSize(this.rightMargin);
            }

            if (this.bottomMargin > 0.0F) {
                params.bottomMargin = getRealPixelSize(this.bottomMargin);
            }

            if (this.startMargin > 0.0F) {
                MarginLayoutParamsCompat.setMarginStart(params, getRealPixelSize(this.startMargin));
            }

            if (this.endMargin > 0.0F) {
                MarginLayoutParamsCompat.setMarginEnd(params, getRealPixelSize(this.endMargin));
            }

        }

        public void fillView(View view) {
            if (view instanceof TextView) {
                if (this.textSize > 0) {
                    int newTextSize = getRealFontSize(this.textSize);
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
                }
            }
        }

        public void restoreMarginLayoutParams(ViewGroup.MarginLayoutParams params) {
            this.restoreLayoutParams(params);
            params.leftMargin = this.mPreservedParams.leftMargin;
            params.topMargin = this.mPreservedParams.topMargin;
            params.rightMargin = this.mPreservedParams.rightMargin;
            params.bottomMargin = this.mPreservedParams.bottomMargin;
            MarginLayoutParamsCompat.setMarginStart(params, MarginLayoutParamsCompat.getMarginStart(this.mPreservedParams));
            MarginLayoutParamsCompat.setMarginEnd(params, MarginLayoutParamsCompat.getMarginEnd(this.mPreservedParams));
        }

        public void restoreLayoutParams(ViewGroup.LayoutParams params) {
            params.width = this.mPreservedParams.width;
            params.height = this.mPreservedParams.height;
        }

        public void setMargins(int left, int top, int right, int bottom) {
            leftPadding = left;
            topPadding = top;
            rightPadding = right;
            bottomPadding = bottom;
        }

        private int getRealFontSize(int pix) {
            int screen, design;
            switch (scaleBy) {
                case ScaleByHeight:
                    screen = screenH;
                    design = designHeight;
                    break;
                default:
                    screen = screenW;
                    design = designWidth;
                    break;
            }

            return getRealPixelSizeBySP(pix, screen, design);
        }

        private int getRealPixelSize(int pix) {
            int screen, design;
            switch (scaleBy) {
                case ScaleByHeight:
                    screen = screenH;
                    design = designHeight;
                    break;
                default:
                    screen = screenW;
                    design = designWidth;
                    break;
            }

            if (ScaleConfig.getInstance().isDimensUnitByDp()) {
                return getRealPixelSizeByDp(pix, screen, design);
            } else {
                return getRealPixelSizeByPix(pix, screen, design);
            }
        }

        private int getRealPixelSizeByPix(int pix, int screen, int design) {
            int result;

            int res = pix * screen;

            if (res % design == 0) {
                result = res / design;
            } else {
                result = res / design + 1;
            }

            if (ScaleConfig.getInstance().isDebug())
                Log.i(TAG, "pix:" + pix + ",result:" + result);
            return result;
        }

        private int getRealPixelSizeByDp(int pix, int screen, int design) {
            float density = ScaleConfig.getInstance().getScreenDensity();
            float designDensity = ScaleConfig.getInstance().getDesignDensity();

            int designDp = convertPix2Dp(density, pix);
            int designPix = convertDp2Pix(designDensity, designDp);

            int result;

            int res = designPix * screen;

            if (res % design == 0) {
                result = res / design;
            } else {
                result = res / design + 1;
            }

            if (ScaleConfig.getInstance().isDebug())
                Log.i(TAG, "pix:" + pix + ",dp:" + designDp + ",result:" + result);
            return result;
        }

        private int getRealPixelSizeBySP(int pix, int screen, int design) {
            float density = ScaleConfig.getInstance().getScreenFontScale();
            float designDensity = ScaleConfig.getInstance().getDesignFontScale();

            int designDp = convertPix2Sp(density, pix);
            int designPix = convertSp2Pix(designDensity, designDp);

            int result;

            int res = designPix * screen;

            if (res % design == 0) {
                result = res / design;
            } else {
                result = res / design + 1;
            }

            if (ScaleConfig.getInstance().isDebug())
                Log.i(TAG, "pix:" + pix + ",sp:" + designDp + ",result:" + result);
            return result;
        }

        private static int convertPix2Dp(float density, int px) {
            return (int) (px / density + 0.5f);
        }

        private static int convertDp2Pix(float density, int dip) {
            return (int) (dip * density + 0.5f);
        }

        private static int convertPix2Sp(float fontScale, float pxValue) {
            return (int) (pxValue / fontScale + 0.5f);
        }

        private static int convertSp2Pix(float fontScale, float spValue) {
            return (int) (spValue * fontScale + 0.5f);
        }
    }

}
