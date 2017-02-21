package cn.gavinliu.android_scalelayout;

import android.app.Application;

import cn.gavinliu.android.lib.scale.config.ScaleConfig;

/**
 * Created by Gavin on 16-11-10.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        ScaleConfig.getInstance().setDebug(true);
    }
}
