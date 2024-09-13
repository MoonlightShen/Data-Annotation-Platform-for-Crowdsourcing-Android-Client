package com.echo.dapc.mvvm.view;

import com.echo.dapc.R;
import com.echo.dapc.adapter.DataFileContentListAdapter;
import com.echo.dapc.base.activity.BaseDataBindingActivity;
import com.echo.dapc.bean.logic.DataFile;
import com.echo.dapc.databinding.ActivityDataFileTextContentBinding;
import com.echo.dapc.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.dapc.mvvm.model.DataFileTextContentModel;
import com.echo.dapc.mvvm.viewmodel.DataFileTextContentViewModel;
import com.echo.dapc.util.business.DataFileUtil;
import com.echo.dapc.util.system.ToastUtil;
import com.echo.dapc.widget.PopupMenu.MenuItem;
import com.echo.dapc.widget.PopupMenu.TopRightMenu;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import org.mozilla.universalchardet.ReaderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFileTextContentActivity extends BaseDataBindingActivity<DataFileTextContentViewModel, DataFileTextContentModel, ActivityDataFileTextContentBinding> {

    /**
     * 获取当前Activity绑定的布局文件的ID
     *
     * @return 如R.layout.activity_xxx.xml
     */
    @Override
    protected int getUILayoutId() {
        return R.layout.activity_data_file_text_content;
    }

    /**
     * 在主线程的初始化操作，如为一些特殊组件添加监听器
     */
    @Override
    protected void initOnMainThread() {
        getBinding().statusView.showLoading();
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                if (topRightMenu == null) {
                    topRightMenu = new TopRightMenu((DataFileTextContentActivity) getModel().getContext());
                    List<MenuItem> menuItems = new ArrayList<>();
                    menuItems.add(new MenuItem(R.drawable.common_left_gray, "本地缓存"));
                    menuItems.add(new MenuItem(R.drawable.common_left_gray, "删除文件"));
                    topRightMenu
                            .addMenuList(menuItems)
                            .setOnMenuItemClickListener(position -> {
                                switch (position) {
                                    case 0 -> getViewModel().localCachingDataFile();
                                    case 1 -> getViewModel().removeDataFile();
                                }
                            })
                            .showAsDropDown(getBinding().titleBar.getRightView(), -100, 0);    //带偏移量
                } else {
                    topRightMenu.showAsDropDown(getBinding().titleBar.getRightView(), -100, 0);
                }
            }
        });
//        getBinding().datafileContentSeparatorEnter.setOnClickListener(v -> {
//            if (!"\n".equals(getModel().getDatafile().getSeparator())) {
//                DialogUtil.createBasicButtonsDialog(getContext(),
//                        "您正在改变数据项分割符，这会导致数据文件重新加载，可能导致数据项分割错误，请谨慎操作！",
//                        materialDialog -> {
//                            getBinding().datafileContentSeparatorEnter.setSelected(true);
//                            return null;
//                        }, null, null);
//            }
//        });
//        getBinding().datafileContentSeparatorCustom.setOnClickListener(v -> {
//            if (!"\n".equals(getModel().getDatafile().getSeparator())) {
//                DialogUtil.createInputDialog(getContext(), null, "您正在改变数据项分割符，这会导致数据文件重新加载，可能导致数据项分割错误，请谨慎操作！请输入自定义的分割符",
//                        null, null, new Function2<MaterialDialog, CharSequence, Unit>() {
//                            @Override
//                            public Unit invoke(MaterialDialog materialDialog, CharSequence charSequence) {
//                                DataFile dataFile = getModel().getDatafile();
//                                String separator = dataFile.getSeparator();
//                                dataFile.setSeparator(charSequence.toString());
//                                DataFileUtil.refreshDataFile(new SQLiteDBRefreshCallback<>() {
//                                    @Override
//                                    public void onSuccess(DataFile dataFile) {
//                                        getModel().getDatafile().setSeparator(charSequence.toString());
//                                        getModel().setSeparator(JsonUtil.toJson(charSequence.toString()));
//                                        getBinding().datafileContentSeparatorCustom.setSelected(true);
//                                    }
//
//                                    @Override
//                                    public void onSQLiteError() {
//                                        getModel().getDatafile().setSeparator(separator);
//                                    }
//                                }, dataFile);
//                                return null;
//                            }
//                        });
//            }
//        });
        getModel().setAdapter(new DataFileContentListAdapter());
//        getBinding().datafileContentSeparatorEnter.setSelected(true);
    }

    /**
     * 在子线程的初始化操作，如从网络请求中加载数据
     */
    @Override
    protected void initInBackground() {
        long index = getLongExtra("datafile_index", 0);
        long id = getLongExtra("datafile_id", 0);
        if (id != 0) {
            //TODO 服务器的数据文件，需要缓存到本地才能查看
        } else if (index != 0) {
            DataFileUtil.queryLocalDataFile(localDataFile -> {
                DataFile dataFile = DataFile.fromLocal(localDataFile);
                getModel().setDatafile(dataFile);
                if (dataFile.getLocalCaching()!=null&&dataFile.getLocalCaching()&&dataFile.getCacheFilePath()!=null&&!dataFile.getCacheFilePath().isEmpty())getModel().setLocalCached(true);
                if ("\n".equals(getModel().getDatafile().getSeparator())) {
//                        getBinding().datafileContentSeparatorCustom.setSelected(true);
                    getModel().setSeparator(dataFile.getSeparator());
                }
                File file = new File(dataFile.getFileInfo().getPath());
                if (!file.exists()) {
                    getBinding().statusView.showError();
                    ToastUtil.error("文件不存在");
                    DataFileUtil.removeLocalDataFile(new SQLiteDBDeleteCallback() {
                        @Override
                        public void onSuccess() {
                            finish();
                        }

                        @Override
                        public void onSQLiteError() {
                        }
                    }, index);
                } else {
                    try {
                        BufferedReader reader = ReaderFactory.createBufferedReader(file);
                        getModel().setReader(reader);
                        List<String> data = getViewModel().loadMoreData(10);
                        if (data==null){
                            getBinding().statusView.showError();
                            ToastUtil.error("加载失败");
                            getBinding().smartRefreshLayout.setEnableLoadMore(false);
                        } else {
                            if (data.size() < 10) {
                                getBinding().smartRefreshLayout.setEnableLoadMore(false);
                            }
                            runOnUiThread(()->{
                                getModel().getAdapter().addItems(data);
                                getBinding().statusView.showContent();
                            });
                        }
                    } catch (IOException e) {
                        getBinding().statusView.showError();
                        ToastUtil.error("文件不存在");
                    }
                }
            }, index);
        } else {
            getBinding().statusView.showError();
        }

        getBinding().smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            List<String> data = getViewModel().loadMoreData(10);
            if (data==null){
                getBinding().statusView.showError();
                ToastUtil.error("加载失败");
                getBinding().smartRefreshLayout.setEnableLoadMore(false);
            } else {
                if (data.size() < 10) {
                    getBinding().smartRefreshLayout.setEnableLoadMore(false);
                }
                runOnUiThread(()->{
                    getModel().getAdapter().addItems(data);
                    getBinding().statusView.showContent();
                });
            }
            refreshLayout.finishLoadMore();
        });
    }

    TopRightMenu topRightMenu;

}