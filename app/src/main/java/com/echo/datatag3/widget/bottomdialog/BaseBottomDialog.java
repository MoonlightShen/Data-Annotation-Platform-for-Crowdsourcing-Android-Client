package com.echo.datatag3.widget.bottomdialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.echo.datatag3.R;

public abstract class BaseBottomDialog extends DialogFragment {

    private int height=-1;
    private float dim=0.5f; //暗度 0-1.0
    private boolean cancelOutsideToClose=false;  //点击对话框外部时关闭对话框
    private FragmentManager fragmentManager;


    @LayoutRes
    protected abstract int getLayoutRes();
    protected abstract String getFragmentTag();
    protected abstract void bindView(View v);
    public abstract void dismiss();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(cancelOutsideToClose);
        View v = inflater.inflate(getLayoutRes(), container, false);
        bindView(v);
        return v;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = dim;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (height > 0) {
            params.height = height;
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        params.gravity = Gravity.BOTTOM;

        window.setAttributes(params);
    }

    public void show() {
        show(fragmentManager, getFragmentTag());
    }

    public float getDim() {
        return dim;
    }

    public void setDim(float dim) {
        this.dim = dim;
    }

    public boolean isCancelOutsideToClose() {
        return cancelOutsideToClose;
    }

    public void setCancelOutsideToClose(boolean cancelOutsideToClose) {
        this.cancelOutsideToClose = cancelOutsideToClose;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}