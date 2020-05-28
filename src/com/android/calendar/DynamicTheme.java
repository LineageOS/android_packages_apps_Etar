package com.android.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import lineageos.providers.LineageSettings;

import ws.xsoh.etar.R;

/**
 * Created by Gitsaibot on 01.07.16.
 */
public class DynamicTheme {

    private static final String THEME_PREF = "pref_theme";
    private static final String COLOR_PREF = "pref_color";
    private static final String LIGHT = "light";
    private static final String DARK  = "dark";
    private static final String BLACK = "black";
    private static final String TEAL = "teal";
    private static final String BLUE = "blue";
    private static final String ORANGE  = "orange";
    private static final String GREEN  = "green";
    private static final String RED  = "red";
    private static final String PURPLE = "purple";
    private int currentTheme;


    public void onCreate(Activity activity) {
        currentTheme = getSelectedTheme(activity);
        activity.setTheme(currentTheme);
    }

    public void onResume(Activity activity) {
        if (currentTheme != getSelectedTheme(activity)) {
            Intent intent = activity.getIntent();
            activity.finish();
            OverridePendingTransition.invoke(activity);
            activity.startActivity(intent);
            OverridePendingTransition.invoke(activity);
        }
    }

    private static String getTheme(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            return LIGHT;
        } else {
            if (LineageSettings.System.getInt(context.getContentResolver(),
                    LineageSettings.System.BERRY_BLACK_THEME, 0) == 1) {
                return BLACK;
            } else {
                return DARK;
            }
        }
    }

    private static int getSelectedTheme(Activity activity) {
        String theme = getTheme(activity);
        switch (theme) {
            case LIGHT:
                return R.style.CalendarAppThemeLightSystem;
            case DARK:
                return R.style.CalendarAppThemeDarkSystem;
            case BLACK:
                return R.style.CalendarAppThemeBlackSystem;
            default:
                throw new UnsupportedOperationException("Unknown theme: " + getTheme(activity));
        }
    }

    public static String getPrimaryColor(Context context) {
        return "";
    }

    private static String getSuffix(String theme) {
        switch (theme) {
            case LIGHT:
                return "";
            case DARK:
            case BLACK:
                return "_" + theme;
            default:
                throw new IllegalArgumentException("Unknown theme: " + theme);
        }
    }
    public static int getColorId(String name) {
        return R.color.colorSystemPrimary;
    }

    public static String getColorName(int id) {
        switch (id) {
            case  R.color.colorPrimary :
                return TEAL;
            case R.color.colorBluePrimary:
                return BLUE;
            case R.color.colorOrangePrimary:
                return ORANGE;
            case R.color.colorGreenPrimary:
                return GREEN;
            case R.color.colorRedPrimary:
                return RED;
            case R.color.colorPurplePrimary:
                return PURPLE;
            default:
                throw new UnsupportedOperationException("Unknown color id : " + id);
        }
    }

    public static int getColor(Context context, String id) {
        String suffix = getSuffix(getTheme(context));
        Resources res = context.getResources();
        // When aapt is called with --rename-manifest-package, the package name is changed for the
        // application, but not for the resources. This is to find the package name of a known
        // resource to know what package to lookup the colors in.
        String packageName = res.getResourcePackageName(R.string.app_label);
        return res.getColor(res.getIdentifier(id + suffix, "color", packageName));
    }

    public static int getDrawableId(Context context, String id) {
        String suffix = getSuffix(getTheme(context));
        Resources res = context.getResources();
        // When aapt is called with --rename-manifest-package, the package name is changed for the
        // application, but not for the resources. This is to find the package name of a known
        // resource to know what package to lookup the drawables in.
        String packageName = res.getResourcePackageName(R.string.app_label);
        return res.getIdentifier(id + suffix, "drawable", packageName);
    }

    public static int getDialogStyle(Context context) {
        String theme = getTheme(context);
        switch (getTheme(context)) {
            case LIGHT:
                return android.R.style.Theme_DeviceDefault_Light_Dialog;
            case DARK:
            case BLACK:
                return android.R.style.Theme_DeviceDefault_Dialog;
            default:
                throw new UnsupportedOperationException("Unknown theme: " + theme);
        }
    }

    private static final class OverridePendingTransition {
        static void invoke(Activity activity) {
            activity.overridePendingTransition(0, 0);
        }
    }
}
