package com.echo.datatag3.mvvm.view;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.echo.datatag3.R;
import com.echo.datatag3.adapter.DataFileContentListAdapter;
import com.echo.datatag3.base.BaseCustomActivity;
import com.echo.datatag3.bean.logic.DataFile;
import com.echo.datatag3.databinding.PageDataFileTextContentBinding;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBDeleteCallback;
import com.echo.datatag3.interfaces.callback.common.SQLiteDBRefreshCallback;
import com.echo.datatag3.mvvm.model.DataFileTextContentModel;
import com.echo.datatag3.mvvm.view.task.TaskEditingInfoPage;
import com.echo.datatag3.mvvm.viewmodel.DataFileTextContentViewModel;
import com.echo.datatag3.util.business.DataFileUtil;
import com.echo.datatag3.util.kt.DialogUtil;
import com.echo.datatag3.util.system.JsonUtil;
import com.echo.datatag3.util.system.ToastUtil;
import com.echo.datatag3.widget.PopupMenu.MenuItem;
import com.echo.datatag3.widget.PopupMenu.TopRightMenu;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import org.mozilla.universalchardet.ReaderFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class DataFileTextContentPage extends BaseCustomActivity<DataFileTextContentViewModel, DataFileTextContentModel, PageDataFileTextContentBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.page_data_file_text_content;
    }
    TopRightMenu topRightMenu;
    @Override
    protected void init() {
        getBinding().statusView.showLoading();
        getBinding().titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(TitleBar titleBar) {
                finish();
            }

            @Override
            public void onRightClick(TitleBar titleBar) {
                if (topRightMenu == null) {
                    topRightMenu = new TopRightMenu((DataFileTextContentPage) getModel().getContext());
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
     * 创建子线程处理耗时操作，重写此方法时必须重写loadSuccess()方法
     *
     * @return
     */
    @Override
    protected List<Runnable> loadWithChildThread() {
        List<Runnable> runnableList = new ArrayList<>();
        runnableList.add(() -> {
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
        });
        return runnableList;
    }

    /**
     * 所有子线程成功处理完成后，会调用此方法
     */
    @Override
    protected void loadSuccess() {
//        getBinding().datafileContentSeparatorRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
//            if (checkedId == R.id.datafile_content_separator_enter) {
//                DataFile dataFile = getModel().getDatafile();
//                String separator = dataFile.getSeparator();
//                dataFile.setSeparator("\n");
//                DataFileUtil.refreshDataFile(new SQLiteDBRefreshCallback<>() {
//                    @Override
//                    public void onSuccess(DataFile dataFile) {
//                        getModel().getDatafile().setSeparator("\n");
//                    }
//
//                    @Override
//                    public void onSQLiteError() {
//                        getModel().getDatafile().setSeparator(separator);
//                    }
//                }, dataFile);
//            }
//        });
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
}