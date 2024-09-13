package com.echo.dapc.util.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

import com.echo.dapc.MyApp;
import com.echo.dapc.R;


@SuppressLint("InflateParams")
public final class ToastUtil {
    private static final Context context;
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static final @ColorInt int normalTintColor = Color.parseColor("#353A3E");
    private static final @ColorInt int defaultTextColor = Color.parseColor("#FFFFFFFF");
    private static final @ColorInt int warningColor = Color.parseColor("#FEC005");
    private static final @ColorInt int infoColor = Color.parseColor("#74BEEB");
    private static final @ColorInt int successColor = Color.parseColor("#388E3C");
    private static final @ColorInt int errorColor = Color.parseColor("#F15C58");
    private static Toast lastToast = null;

    static {
        context = MyApp.getMyAppContext();
    }
    
    private static Drawable getDrawable(@DrawableRes int id) {
        return AppCompatResources.getDrawable(MyApp.getMyAppContext(), id);
    }

    public static void normal(@StringRes int message) {
        ThreadUtil.runOnMain(() -> normal( context.getString(message), Toast.LENGTH_SHORT, null, false).show());
    }

    public static void normal( @NonNull CharSequence message) {
        ThreadUtil.runOnMain(() -> normal( message, Toast.LENGTH_SHORT, null, false).show());
    }

    public static void normal( @StringRes int message, Drawable icon) {
        ThreadUtil.runOnMain(() -> normal( context.getString(message), Toast.LENGTH_SHORT, icon, true).show());
    }

    public static void normal( @NonNull CharSequence message, Drawable icon) {
        ThreadUtil.runOnMain(() -> normal( message, Toast.LENGTH_SHORT, icon, true).show());
    }

    public static void normal( @StringRes int message, int duration) {
        ThreadUtil.runOnMain(() -> normal( context.getString(message), duration, null, false).show());
    }

    public static void normal( @NonNull CharSequence message, int duration) {
        ThreadUtil.runOnMain(() -> normal( message, duration, null, false).show());
    }

    public static void normal( @StringRes int message, int duration, Drawable icon) {
        ThreadUtil.runOnMain(() -> normal( context.getString(message), duration, icon, true).show());
    }

    public static void normal( @NonNull CharSequence message, int duration, Drawable icon) {
        ThreadUtil.runOnMain(() -> normal( message, duration, icon, true).show());
    }

    public static void normal( @StringRes int message, int duration, Drawable icon, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( context.getString(message), icon, normalTintColor,
                defaultTextColor, duration, withIcon, true).show());
    }

    private static Toast normal( @NonNull CharSequence message, int duration, Drawable icon, boolean withIcon) {
        return custom( message, icon, normalTintColor,
                defaultTextColor, duration, withIcon, true);
    }

    public static void warning( @StringRes int message) {
        ThreadUtil.runOnMain(() -> warning( context.getString(message), Toast.LENGTH_SHORT, true).show());
    }

    public static void warning( @NonNull CharSequence message) {
        ThreadUtil.runOnMain(() -> warning( message, Toast.LENGTH_SHORT, true).show());
    }

    public static void warning( @StringRes int message, int duration) {
        ThreadUtil.runOnMain(() -> warning( context.getString(message), duration, true).show());
    }


    public static void warning( @NonNull CharSequence message, int duration) {
        ThreadUtil.runOnMain(() -> warning( message, duration, true).show());
    }


    public static void warning( @StringRes int message, int duration, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( context.getString(message), getDrawable(R.drawable.toast_warning_white),
                warningColor, defaultTextColor,
                duration, withIcon, true).show());
    }


    private static Toast warning( @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom( message, getDrawable(R.drawable.toast_warning_white),
                warningColor, defaultTextColor,
                duration, withIcon, true);
    }


    public static void info( @StringRes int message) {
        ThreadUtil.runOnMain(() -> info( context.getString(message), Toast.LENGTH_SHORT, true).show());
    }


    public static void info( @NonNull CharSequence message) {
        ThreadUtil.runOnMain(() -> info( message, Toast.LENGTH_SHORT, true).show());
    }


    public static void info( @StringRes int message, int duration) {
        ThreadUtil.runOnMain(() -> info( context.getString(message), duration, true).show());
    }


    public static void info( @NonNull CharSequence message, int duration) {
        ThreadUtil.runOnMain(() -> info( message, duration, true).show());
    }


    public static void info( @StringRes int message, int duration, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( context.getString(message), getDrawable(R.drawable.toast_error_white),
                infoColor, defaultTextColor,
                duration, withIcon, true).show());
    }


    private static Toast info( @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom( message, getDrawable(R.drawable.toast_error_white),
                infoColor, defaultTextColor,
                duration, withIcon, true);
    }

    public static void success( @StringRes int message) {
        ThreadUtil.runOnMain(() -> success( context.getString(message), Toast.LENGTH_SHORT, true).show());
    }

    public static void success( @NonNull CharSequence message) {
        ThreadUtil.runOnMain(() -> success( message, Toast.LENGTH_SHORT, true).show());
    }


    public static void success( @StringRes int message, int duration) {
        ThreadUtil.runOnMain(() -> success( context.getString(message), duration, true).show());
    }


    public static void success( @NonNull CharSequence message, int duration) {
        ThreadUtil.runOnMain(() -> success( message, duration, true).show());
    }


    public static void success( @StringRes int message, int duration, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( context.getString(message), getDrawable(R.drawable.toast_success_white),
                successColor, defaultTextColor,
                duration, withIcon, true).show());
    }


    public static Toast success( @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom( message, getDrawable(R.drawable.toast_success_white),
                successColor, defaultTextColor,
                duration, withIcon, true);
    }


    public static void error( @StringRes int message) {
        ThreadUtil.runOnMain(() -> error( context.getString(message), Toast.LENGTH_SHORT, true).show());
    }


    public static void error( @NonNull CharSequence message) {
        ThreadUtil.runOnMain(() -> error( message, Toast.LENGTH_SHORT, true).show());
    }


    public static void error( @StringRes int message, int duration) {
        ThreadUtil.runOnMain(() -> error( context.getString(message), duration, true).show());
    }


    public static void error( @NonNull CharSequence message, int duration) {
        ThreadUtil.runOnMain(() -> error( message, duration, true).show());
    }


    public static void error( @StringRes int message, int duration, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( context.getString(message), getDrawable(R.drawable.toast_error_white),
                errorColor, defaultTextColor,
                duration, withIcon, true).show());
    }


    public static Toast error( @NonNull CharSequence message, int duration, boolean withIcon) {
        return custom( message, getDrawable(R.drawable.toast_error_white),
                errorColor, defaultTextColor,
                duration, withIcon, true);
    }


    public static void custom( @StringRes int message, Drawable icon, int duration, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( context.getString(message), icon, -1, defaultTextColor,
                duration, withIcon, false).show());
    }


    public static void custom( @NonNull CharSequence message, Drawable icon,
                              int duration, boolean withIcon) {
        ThreadUtil.runOnMain(() -> custom( message, icon, -1, defaultTextColor,
                duration, withIcon, false).show());
    }

    private static @ColorInt int getColor(@ColorRes int tintColorRes) {
        return ContextCompat.getColor(MyApp.getMyAppContext(), tintColorRes);
    }

    public static void custom( @StringRes int message, @DrawableRes int iconRes,
                              @ColorRes int tintColorRes, int duration,
                              boolean withIcon, boolean shouldTint) {
        ThreadUtil.runOnMain(() -> custom(context.getString(message), getDrawable(iconRes),
                getColor(tintColorRes), defaultTextColor,
                duration, withIcon, shouldTint).show());
    }


    public static void custom( @NonNull CharSequence message, @DrawableRes int iconRes,
                              @ColorRes int tintColorRes, int duration,
                              boolean withIcon, boolean shouldTint) {
        ThreadUtil.runOnMain(() -> custom(message, getDrawable(iconRes),
                getColor(tintColorRes), defaultTextColor,
                duration, withIcon, shouldTint).show());
    }


    public static void custom( @StringRes int message, Drawable icon,
                              @ColorRes int tintColorRes, int duration,
                              boolean withIcon, boolean shouldTint) {
        ThreadUtil.runOnMain(() -> custom(context.getString(message), icon, getColor(tintColorRes),
                defaultTextColor, duration, withIcon, shouldTint).show());
    }


    public static Toast custom( @StringRes int message, Drawable icon,
                               @ColorRes int tintColorRes, @ColorRes int textColorRes, int duration,
                               boolean withIcon, boolean shouldTint) {
        return custom(context.getString(message), icon, getColor(tintColorRes),
                getColor(textColorRes), duration, withIcon, shouldTint);
    }

    private static Drawable tintIcon(@NonNull Drawable drawable, @ColorInt int tintColor) {
        drawable.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
        return drawable;
    }

    private static Drawable tint9PatchDrawableFrame( @ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(R.drawable.toast_frame);
        return tintIcon(toastDrawable, tintColor);
    }

    private static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    @SuppressLint("ShowToast")
    public static Toast custom(@NonNull CharSequence message, Drawable icon,
                               @ColorInt int tintColor, @ColorInt int textColor, int duration,
                               boolean withIcon, boolean shouldTint) {
        final Toast currentToast = Toast.makeText(context, "", duration);
        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.widget_toast, null);
        final ImageView toastIcon = toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint) {
            drawableFrame = tint9PatchDrawableFrame(tintColor);
        } else {
            drawableFrame = getDrawable(R.drawable.toast_frame);
        }
        setBackground(toastLayout, drawableFrame);

        if (withIcon) {
            if (icon == null) {
                throw new IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true");
            }
            if (Config.get().isRTL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                toastLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
            setBackground(toastIcon, Config.get().tintIcon ? tintIcon(icon, textColor) : icon);
        } else {
            toastIcon.setVisibility(View.GONE);
        }

        toastTextView.setText(message);
        toastTextView.setTextColor(textColor);
        toastTextView.setTypeface(Config.get().typeface, Typeface.NORMAL);
        if (Config.get().textSize != -1) {
            toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, Config.get().textSize);
        }
        if (Config.get().alpha != -1) {
            toastLayout.getBackground().setAlpha(Config.get().alpha);
        }
        currentToast.setView(toastLayout);
        if (Config.get().gravity != -1) {
            currentToast.setGravity(Config.get().gravity, Config.get().xOffset, Config.get().yOffset);
        }
        if (!Config.get().allowQueue) {
            if (lastToast != null) {
                lastToast.cancel();
            }
            lastToast = currentToast;
            // solve the problem of lastToast memory leak
            currentToast.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    v.removeOnAttachStateChangeListener(this);
                    if (currentToast == lastToast) {
                        lastToast = null;
                    }
                }
            });
        }
        return currentToast;
    }

    public static class Config {
        private static final Typeface LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL);
        private static volatile Config sInstance = null;
        private Typeface typeface = LOADED_TOAST_TYPEFACE;
        private int textSize = -1; //sp
        private boolean tintIcon = true;
        private boolean allowQueue = true;
        private int alpha = -1;
        private int gravity = -1;
        private int xOffset = 0;
        private int yOffset = 0;
        private boolean isRTL = false;

        private Config() {

        }

        /**
         * 获取单例
         *
         * @return 配置
         */
        public static Config get() {
            if (sInstance == null) {
                synchronized (Config.class) {
                    if (sInstance == null) {
                        sInstance = new Config();
                    }
                }
            }
            return sInstance;
        }

        public void reset() {
            typeface = LOADED_TOAST_TYPEFACE;
            textSize = -1;
            tintIcon = true;
            allowQueue = true;
            alpha = -1;
            gravity = -1;
            xOffset = 0;
            yOffset = 0;
            isRTL = false;
        }


        public Config setToastTypeface(Typeface typeface) {
            if (typeface != null) {
                this.typeface = typeface;
            }
            return this;
        }


        public Config setTextSize(int sizeInSp) {
            this.textSize = sizeInSp;
            return this;
        }


        public Config tintIcon(boolean tintIcon) {
            this.tintIcon = tintIcon;
            return this;
        }

        public Config setAlpha(@IntRange(from = 0, to = 255) int alpha) {
            this.alpha = alpha;
            return this;
        }


        public Config allowQueue(boolean allowQueue) {
            this.allowQueue = allowQueue;
            return this;
        }

        public Config setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Config setGravity(int gravity, int xOffset, int yOffset) {
            this.gravity = gravity;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            return this;
        }

        public Config resetGravity() {
            gravity = -1;
            xOffset = 0;
            yOffset = 0;
            return this;
        }

        public Config setXOffset(int xOffset) {
            this.xOffset = xOffset;
            return this;
        }

        public Config setYOffset(int yOffset) {
            this.yOffset = yOffset;
            return this;
        }

        public Config setRTL(boolean isRTL) {
            this.isRTL = isRTL;
            return this;
        }
    }
}
