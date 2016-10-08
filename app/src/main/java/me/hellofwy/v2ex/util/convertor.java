package me.hellofwy.v2ex.util;

import android.app.Activity;
import android.content.Context;

import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

import timber.log.Timber;

import static me.hellofwy.v2ex.R.raw.member;

/**
 * Created by fwy on 2016/10/3.
 */

public class Convertor {
    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static SimpleDateFormat SHORT_DATE =
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public static String utcToDate(long second) {
        return SHORT_DATE.format(second*1000);
    }

    public static void setStatusBarTranslucentIfKitKatAbove(Activity activity, View view) {
        if(Build.VERSION.SDK_INT >= 19) {
            activity.getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            Timber.v("set translucent");

            int result = 0;
            int resourceId = activity.getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result =  activity.getResources().getDimensionPixelSize(resourceId);
                view.setPadding(
                        view.getPaddingLeft(),
                        result,
                        view.getPaddingRight(),
                        view.getPaddingBottom()
                );
                Timber.v("statusbar: " + result);
            }

            // create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);
        }
    }
}
