package com.echo.datatag3.util.system;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.echo.datatag3.MyApp;
import com.echo.datatag3.base.BaseToast;

public final class ToastUtil {

    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    private ToastUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    @MainThread
    public static void toast(@NonNull final CharSequence message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.normal(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void toast(@StringRes final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.normal(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void toast(@NonNull final CharSequence message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.normal(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    @MainThread
    public static void toast(@StringRes final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.normal(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    //=============//

    @MainThread
    public static void error(@NonNull final CharSequence message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.error(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void error(@NonNull Throwable error) {
        final String message = error.getMessage() != null ? error.getMessage() : "";
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.error(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void error(@StringRes final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.error(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void error(@NonNull final CharSequence message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.error(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    @MainThread
    public static void error(@StringRes final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.error(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    //=============//

    @MainThread
    public static void success(@NonNull final CharSequence message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.success(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void success(@StringRes final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.success(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void success(@NonNull final CharSequence message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.success(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    @MainThread
    public static void success(@StringRes final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.success(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    //=============//

    @MainThread
    public static void info(@NonNull final CharSequence message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.info(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void info(@StringRes final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.info(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void info(@NonNull final CharSequence message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.info(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    @MainThread
    public static void info(@StringRes final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.info(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    //=============//

    @MainThread
    public static void warning(@NonNull final CharSequence message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.warning(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void warning(@StringRes final int message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.warning(MyApp.getMyAppContext(), message).show();
            }
        });
    }

    @MainThread
    public static void warning(@NonNull final CharSequence message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.warning(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    @MainThread
    public static void warning(@StringRes final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BaseToast.warning(MyApp.getMyAppContext(), message, duration).show();
            }
        });
    }

    private static void runOnUiThread(@NonNull final Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            MAIN_HANDLER.post(runnable);
        }
    }

}
