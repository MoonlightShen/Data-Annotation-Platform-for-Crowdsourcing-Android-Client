package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.base.BaseSelectableListAdapter;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.entity.UntaggingData;
import com.echo.dapc.bean.entity.UntaggingDataSet;
import com.echo.dapc.bean.enumeration.MessageContentType;
import com.echo.dapc.databinding.ActivityLabelClassificationTaggingBinding;
import com.echo.dapc.mvvm.model.LabelClassificationTaggingModel;
import com.echo.dapc.mvvm.view.task.TaskEditingInfoActivity;
import com.echo.dapc.mvvm.viewmodel.LabelClassificationTaggingViewModel;
import com.echo.dapc.util.database.DBUtilShop;
import com.echo.dapc.util.system.ToastUtil;
import com.echo.dapc.widget.PopupMenu.MenuItem;
import com.echo.dapc.widget.PopupMenu.TopRightMenu;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelClassificationTaggingActivity extends BaseDataBindingActivity<LabelClassificationTaggingViewModel, LabelClassificationTaggingModel, ActivityLabelClassificationTaggingBinding> {

    TopRightMenu topRightMenu;

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_label_classification_tagging;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        FlexboxLayoutManager flexboxLayoutManager;
        flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        getModel().setFlexboxLayoutManager(flexboxLayoutManager);
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }

            @Override
            public void onTitleClick(TitleBar titleBar) {
                if (topRightMenu == null) {
                    topRightMenu = new TopRightMenu((TaskEditingInfoActivity) getModel().getContext());
                    List<MenuItem> menuItems = new ArrayList<>();
                    menuItems.add(new MenuItem(R.drawable.common_left_gray, "保存进度"));
                    menuItems.add(new MenuItem(R.drawable.common_left_gray, "举报数据"));
                    topRightMenu
                            .addMenuList(menuItems)
                            .setOnMenuItemClickListener(position -> {
                                switch (position) {
                                    case 0 -> getViewModel().saveProgress();
                                    case 1 -> getViewModel().reportData();
                                }
                            })
                            .showAsDropDown(getBinding().titleBar.getRightView(), -100, 0);    //带偏移量
                } else {
                    topRightMenu.showAsDropDown(getBinding().titleBar.getRightView(), -100, 0);
                }
            }
        });
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        long setIndex = getLongExtra("untagging_data_set_index", 0);
        if (setIndex != 0) {
            getModel().setTaggingDataSetIndex(setIndex);
            UntaggingDataSet untaggingDataSet = DBUtilShop.untaggingDatasetDBUtil.queryEntity(setIndex);

            Map<Integer, String> map = new HashMap<>();
            map.put(1, "快乐");
            map.put(2, "悲伤");
            map.put(3, "惊讶");
            map.put(4, "愤怒");
            map.put(5, "冷漠");
            map.put(6, "幸福");
            map.put(7, "激动");
            untaggingDataSet = new UntaggingDataSet(1L, 1L, map, 0);

            if (untaggingDataSet != null) {
                Map<String, Integer> optionDict = new HashMap<>();
                List<String> options = new ArrayList<>();
                for (Map.Entry<Integer, String> entry : untaggingDataSet.getTaggingOptionDict().entrySet()) {
                    optionDict.put(entry.getValue(), entry.getKey());
                    options.add(entry.getValue());
                }
                getModel().setOptionDict(optionDict);
                BaseSelectableListAdapter adapter = new BaseSelectableListAdapter(options);
                adapter.setSelectCallback(position -> {
                    if (adapter.isSelect(position)){
                        ToastUtil.normal("您选择了:" + adapter.getItem(position).content);
                    }else {
                        ToastUtil.normal("您取消了:"+adapter.getItem(position).content);
                    }
                });
                getModel().setOptionAdapter(adapter);
                List<UntaggingData> untaggingDataList = DBUtilShop.untaggingDataDBUtil.queryUntaggingDataBySetId(setIndex);
                if (untaggingDataSet.getLastTagging() != null) {
                    getModel().setCurrentDataIndex(untaggingDataSet.getLastTagging());
                }else getModel().setCurrentDataIndex(0);

                untaggingDataList.add(new UntaggingData(1L, 1L, 1L, MessageContentType.TEXT, "心花怒放", null));
                untaggingDataList.add(new UntaggingData(2L, 1L, 2L, MessageContentType.TEXT, "笑逐颜开", null));
                untaggingDataList.add(new UntaggingData(3L, 1L, 3L, MessageContentType.TEXT, "胆颤心惊", null));
                untaggingDataList.add(new UntaggingData(4L, 1L, 4L, MessageContentType.TEXT, "心旷神怡", null));
                untaggingDataList.add(new UntaggingData(5L, 1L, 5L, MessageContentType.TEXT, "喜怒哀乐", null));
                untaggingDataList.add(new UntaggingData(6L, 1L, 6L, MessageContentType.TEXT, "心潮澎湃", null));
                untaggingDataList.add(new UntaggingData(7L, 1L, 7L, MessageContentType.TEXT, "坐立不安", null));
                untaggingDataList.add(new UntaggingData(8L, 1L, 8L, MessageContentType.TEXT, "欢欣鼓舞", null));
                untaggingDataList.add(new UntaggingData(9L, 1L, 9L, MessageContentType.TEXT, "妄自尊大", null));
                untaggingDataList.add(new UntaggingData(10L, 1L, 10L, MessageContentType.TEXT, "扬眉吐气", null));

                if (untaggingDataList != null && !untaggingDataList.isEmpty()) {
                    for (int i = 0; i < untaggingDataList.size(); i++) {
                        UntaggingData untaggingData = untaggingDataList.get(i);
                        getModel().getTaggingDataList().add(untaggingData);
                        if (untaggingData.getTaggingOption() != null && !untaggingData.getTaggingOption().isEmpty()) {
                            getModel().getHasTagged().put(i, untaggingData.getTaggingOption());
                        }
                    }
                } else {
                    //TODO 加载失败
                }
            } else {
                //TODO 加载失败
            }
        }
    }
}