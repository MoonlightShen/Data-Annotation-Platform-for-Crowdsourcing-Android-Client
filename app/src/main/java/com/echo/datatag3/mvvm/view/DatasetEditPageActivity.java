package com.echo.datatag3.mvvm.view;

//public class DatasetEditPageActivity extends BaseCustomActivity<DatasetEditPageViewModel, DatasetEditPageModel, ActivityDatasetEditPageBinding> {
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.activity_dataset_edit_page;
//    }
//
//    @Override
//    protected void init() {
//        getBinding().datasetEditTbTitle.setOnTitleBarListener(new OnTitleBarListener() {
//            @Override
//            public void onLeftClick(TitleBar titleBar) {
//                if (getViewModel().getModel().isHasEdited()) {
//                    getViewModel().saveDataset();
//                } else {
//                    finish();
//                }
//            }
//
//            @Override
//            public void onRightClick(TitleBar titleBar) {
//                getViewModel().openMenu(getBinding().datasetEditTbTitle.getRightView());
//            }
//        });
//        dispatchFromClasses(new Class[]{MaterialEditText.class});
//    }
//
//    @Override
//    protected List<Runnable> loadWithChildThread() {
//        List<Runnable> runnableList = new ArrayList<>();
//        runnableList.add(() -> {
//            getViewModel().getModel().setDataFileAdapter(new DataFileListAdapter());
//            getViewModel().getModel().getDataset().setDataType(DataType.TEXT);
//
//            long datasetIndex = getLongExtra("dataset_index", 0);
//            if (datasetIndex != 0) {
//                DatasetUtil.queryLocalDataset(new QueryLocalDatasetCallback() {
//                    @Override
//                    public void onSuccess(LocalDataset localDataset) {
//                        getViewModel().getModel().setDataset(localDataset);
//                        if (localDataset.getDataType() != null) {
//                            getBinding().datasetEditDataType.setSelection(localDataset.getDataType().getIndex() - 1);
//                        }
//                        switch (localDataset.getDataType().getIndex() - 1) {
//                            case 0 -> getBinding().fileFormatRequest.setText("每条数据需单独一行");
//                            case 1, 2 -> getBinding().fileFormatRequest.setText("无特殊要求");
//                            default -> getBinding().fileFormatRequest.setText("");
//                        }
//                        getModel().setImportFilesNum(localDataset.getFilesInfo().size());
//                        getViewModel().getModel().getDataFileAdapter().refreshItems(localDataset.getFilesInfo());
//                    }
//
//                    @Override
//                    public void onError(Object errorMessage) {
//
//                    }
//                }, datasetIndex);
//            }
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return runnableList;
//    }
//
//    @Override
//    protected void loadSuccess() {
//        getBinding().datasetEditDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (getModel().isFirstSelect()) {
//                    return;
//                }
//                getModel().setFirstSelect(true);
//                LocalDataset dataset = getViewModel().getModel().getDataset();
//                DataType oriType = dataset.getDataType();
//                if (dataset.getFilesInfo() != null && !dataset.getFilesInfo().isEmpty()) {
//                    DialogUtil.changeDataType(getViewModel().getModel().getContext(), materialDialog -> {
//                        dataset.getFilesInfo().clear();
//                        getViewModel().getModel().getDataFileAdapter().removeAllItems();
//                        dataset.setDataType(DataType.getByIndex(position + 1));
//                        switch (position) {
//                            case 0 -> getBinding().fileFormatRequest.setText("每条数据需单独一行");
//                            case 1, 2 -> getBinding().fileFormatRequest.setText("无特殊要求");
//                            default -> getBinding().fileFormatRequest.setText("");
//                        }
//                        getViewModel().getModel().setHasEdited(true);
//                        getModel().setImportFilesNum(0);
//                        postDelayed(() -> getModel().setFirstSelect(false), 1000);
//                        return null;
//                    }, materialDialog -> {
//                        dataset.setDataType(oriType);
//                        getBinding().datasetEditDataType.setSelection(oriType.getIndex() - 1);
//                        postDelayed(() -> getModel().setFirstSelect(false), 1000);
//                        return null;
//                    });
//                } else {
//                    dataset.setDataType(DataType.getByIndex(position + 1));
//                    switch (position) {
//                        case 0 -> getBinding().fileFormatRequest.setText("每条数据需单独一行");
//                        case 1, 2 -> getBinding().fileFormatRequest.setText("无特殊要求");
//                        default -> getBinding().fileFormatRequest.setText("");
//                    }
//                    getViewModel().getModel().setHasEdited(true);
//                    getModel().setImportFilesNum(0);
//                    postDelayed(() -> getModel().setFirstSelect(false), 1000);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        getBinding().datasetName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                getViewModel().getModel().setHasEdited(true);
//            }
//        });
//    }
//
//    @Override
//    protected void handleRes(int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            getModel().setHasEdited(true);
//            if (null != data.getClipData()) {
//                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
//                    Uri uri = data.getClipData().getItemAt(i).getUri();
//                    if (uri == null) {
//                        ToastUtil.toast("文件获取失败,请重试");
//                        return;
//                    }
//                    String path;
//                    try {
//                        path = FileUtil.getFileAbsolutePath(this, uri);
//                    } catch (Exception e) {
//                        ToastUtil.toast("文件路径解析失败");
//                        return;
//                    }
//                    CacheUtil.cachingDataFile(new CachingDataFileCallback() {
//                        @Override
//                        public void onSuccess(String cachePath, int dataNum) {
//                            getModel().setImportFilesNum(getModel().getImportFilesNum() + 1);
////                            runOnUiThread(() -> getModel().getDataFileAdapter().addItem(new DataFileInfo(dataNum,
////                                    true, TimeUtil.getCurrentTime(), cachePath, false, 0, 0,
////                                    new FileInfo(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")),
////                                            FileType.getBySuffix(path.substring(path.lastIndexOf(".") + 1)),
////                                            new File(cachePath).length(), null, null))));
//                        }
//
//                        @Override
//                        public void onCachingError() {
//                            ToastUtil.error("缓存失败");
//                        }
//
//                        @Override
//                        public void onFileCorruption() {
//                            ToastUtil.error("文件版本不支持或已损坏");
//                        }
//
//                        @Override
//                        public void onFileNotExist() {
//                            ToastUtil.error("文件不存在");
//                        }
//
//                    }, path);
//                }
//            } else {
//                Uri uri = data.getData();
//                if (uri == null) {
//                    ToastUtil.toast("文件获取失败,请重试");
//                    return;
//                }
//                String path;
//                try {
//                    path = FileUtil.getFileAbsolutePath(this, uri);
//                } catch (Exception e) {
//                    ToastUtil.toast("文件路径解析失败");
//                    return;
//                }
//                CacheUtil.cachingDataFile(new CachingDataFileCallback() {
//                    @Override
//                    public void onSuccess(String cachePath, int dataNum) {
//                        getModel().setImportFilesNum(getModel().getImportFilesNum() + 1);
////                        runOnUiThread(() -> getModel().getDataFileAdapter().addItem(new DataFileInfo(dataNum,
////                                true, TimeUtil.getCurrentTime(), cachePath, false, 0, 0,
////                                new FileInfo(path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".")),
////                                        FileType.getBySuffix(path.substring(path.lastIndexOf(".") + 1)),
////                                        new File(cachePath).length(), null, null))));
//                    }
//
//                    @Override
//                    public void onCachingError() {
//                        ToastUtil.error("缓存失败");
//                    }
//
//                    @Override
//                    public void onFileCorruption() {
//                        ToastUtil.error("文件版本不支持或已损坏");
//                    }
//
//                    @Override
//                    public void onFileNotExist() {
//                        ToastUtil.error("文件不存在");
//                    }
//                }, path);
//            }
//        }
//    }
//}