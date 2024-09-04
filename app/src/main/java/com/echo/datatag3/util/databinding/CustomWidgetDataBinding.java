package com.echo.datatag3.util.databinding;

import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.echo.datatag3.R;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.util.system.TimeUtil;
import com.echo.datatag3.widget.ExpandableLayout;
import com.echo.datatag3.widget.badgeview.BadgeView;
import com.fphoenixcorneae.progressbar.SmartProgressBar;
import com.hjq.bar.TitleBar;

import java.util.List;

public final class CustomWidgetDataBinding {

    @BindingAdapter("expand")
    public static void setToggle(ExpandableLayout expandableLayout, boolean value){
        if (value && !expandableLayout.isExpanded() || !value && expandableLayout.isExpanded())expandableLayout.toggle();
    }

    @BindingAdapter("tb_save_btn")
    public static void setSaveBtn(TitleBar titleBar, boolean value){
        if (value){
            titleBar.setLeftIcon(R.drawable.common_save_gray);
        }else {
            titleBar.setLeftIcon(R.drawable.common_left_gray);
        }
    }

    @BindingAdapter("title_bar")
    public static void setTitleBar(TitleBar titleBar, String title){
        titleBar.setTitle(title==null?"":title);
    }

    @BindingAdapter("badge_num")
    public static void setBadgeView(View view, Integer value){
        if (value==null)return;
        new BadgeView(view.getContext()).bindTarget(view).setBadgeNumber(value).setOnDragStateChangedListener((dragState, badge, targetView) -> {
        });
    }

    @BindingAdapter("ddHHmmSS_time")
    public static void formatTime_ddHHmmSS(TextView textView, Long endTime){
        if (endTime==null)return;
        else textView.setText(TimeUtil.format(endTime - TimeUtil.getCurrentTime(), TimeUtil.zh_ddHHmmSS));
    }

    @BindingAdapter("data_num")
    public static void setDataNum(TextView textView, List<DataFile> dataFiles){
        if (dataFiles!=null&&dataFiles.size()>0){
            int dataNum=0;
            for (DataFile dataFile:dataFiles){
                if (dataFile.getDataNum()!=null){
                    dataNum += dataFile.getDataNum();
                }
            }
            textView.setText(String.valueOf(dataNum));
        }else {
            textView.setText("暂未选择数据文件");
        }
    }

    @BindingAdapter("smart_progress")
    public static void setProgress(SmartProgressBar bar, Integer progress){
        if (progress!=null){
            bar.setProgress(progress, 0);
        }
    }

}
