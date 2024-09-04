package com.echo.datatag3.util.system;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyBoardUtil {

    public static boolean isSoftInputShow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isAcceptingText();
    }

    public static boolean touchInside(MotionEvent event, View view){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();
        return event.getX() >= left && event.getX() <= right && event.getY() >=top && event.getY() <= bottom;
    }

    public static boolean touchInRow(MotionEvent event, View view){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int top = location[1];
        int bottom = top + view.getHeight();
        return event.getY() >=top && event.getY() <= bottom;
    }

    public static void hideSoftInputFromWindow(Activity activity, View view){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        view.clearFocus();
    }
}
