package com.echo.datatag3.mvvm.viewmodel;


//public class DatasetEditPageViewModel extends BaseViewModel<DatasetEditPageModel> {
//    public DatasetEditPageViewModel(@NonNull Application application) {
//        super(application);
//    }
//
//    TopRightMenu topRightMenu;
//    public void openMenu(View view) {
//        if (topRightMenu == null) {
//            topRightMenu = new TopRightMenu((DatasetEditPageActivity) getModel().getContext());
//            List<MenuItem> menuItems = new ArrayList<>();
//            menuItems.add(new MenuItem(R.drawable.common_delete_gray,"删除数据集"));
//            menuItems.add(new MenuItem(R.drawable.common_upload_gray,"上传至服务器"));
//            topRightMenu
//                    .addMenuList(menuItems)
//                    .setOnMenuItemClickListener(position -> {
//                        switch (position) {
//                            case 0 -> deleteDataset();
//                            case 1 -> uploadDataset();
//                        }
//                    })
//                    .showAsDropDown(view, -100, 0);    //带偏移量
//        } else {
//            topRightMenu.showAsDropDown(view, -100, 0);
//        }
//    }
//
//    public void deleteDataset(){
//        if (getModel().getDataset().getDatasetIndex()==null){
//            ToastUtil.toast("您还未保存该数据集");
//        }else {
//            DialogUtil.confirmDeleteDataset(getModel().getContext(), getModel().getDataset().getName(), new Function2<MaterialDialog, CharSequence, Unit>() {
//                @Override
//                public Unit invoke(MaterialDialog materialDialog, CharSequence charSequence) {
//                    if (charSequence.toString().equals(getModel().getDataset().getName())){
//                        DatasetUtil.deleteLocalDataset(new CommonDBDeleteCallback() {
//                            @Override
//                            public void onSuccess() {
//                                ToastUtil.success("删除成功");
//                                getModel().setHasEdited(true);
//                                getModel().getHandler().postDelayed(() -> ((DatasetEditPageActivity)getModel().getContext()).finish(), 300);
//                            }
//
//                            @Override
//                            public void onSQLiteError() {
//                                ToastUtil.error("删除失败");
//                            }
//                        }, getModel().getDataset());
//                    }else {
//                        ToastUtil.toast("请输入正确名称");
//                    }
//                    return null;
//                }
//            });
//        }
//    }
//
//    public void saveDataset() {
//        LocalDataset dataset = getModel().getDataset();
//        dataset.setFilesInfo(getModel().getDataFileAdapter().getData());
//
//        DatasetUtil.saveLocalDataset(new SaveLocalDatasetCallback() {
//            @Override
//            public void onSuccess() {
//                ToastUtil.success("保存成功");
//                getModel().setHasEdited(false);
//            }
//
//            @Override
//            public void onError() {
//                ToastUtil.error("保存失败");
//            }
//        }, dataset);
//    }
//
//    public void uploadDataset() {
//        LocalDataset dataset = getModel().getDataset();
//        if (getModel().isHasEdited()){
//            ToastUtil.toast("请先保存数据集");
//        } else if (dataset.getName() == null || dataset.getName().isEmpty()) {
//            ToastUtil.error("请填写数据集名称");
//        } else if (dataset.getName().length() > 20) {
//            ToastUtil.error("数据集名称不得长于20字符");
//        } else if (dataset.getFilesInfo()==null||dataset.getFilesInfo().isEmpty()) {
//            ToastUtil.error("您还没有上传数据文件");
//        } else {
//            DatasetUtil.uploadDatasetCaching(new UploadDatasetCachingCallback() {
//                @Override
//                public void onInitError() {
//                    ToastUtil.error("缓存请求拒绝");
//                }
//
//                @Override
//                public void unknownError(String errorCode) {
//                    ToastUtil.info("未知错误"+errorCode);
//                }
//
//                @Override
//                public void onFileNotExist(int position) {
//                    ToastUtil.toast("文件失效");
//                }
//
//                @Override
//                public void cachingFileProgress(int position, int progress) {
//                    if (progress==100){
//                        getModel().getDataFileAdapter().getItem(position).setLocalCaching(true);
//                        getModel().getDataFileAdapter().getItem(position).setLocalCachingTime(TimeUtil.getCurrentTime());
//                    }
//                }
//
//                @Override
//                public void cachingFileError(int position) {
//                    ToastUtil.error("有文件上传失败");
//                }
//
//                @Override
//                public void cachingFileOvertime(int position) {
//                    ToastUtil.error("有文件上传超时");
//                }
//
//                @Override
//                public void cachingFileCheckError(int position) {
//                    ToastUtil.error("有文件上传超时");
//                }
//
//                @Override
//                public void onNoNetwork() {
//                    ToastUtil.error("网络连接失败");
//                }
//
//                @Override
//                public void onCachingOver(String uuid) {
//                    getModel().getDataset().setUuid(uuid);
//                    getModel().getDataset().setUuidCreateTime(TimeUtil.getCurrentTime());
//                    boolean flag = true;
//                    for (DataFileInfo dataFileInfo :getModel().getDataFileAdapter().getData()){
//                        if (!dataFileInfo.isLocalCaching()){
//                            flag = false;
//                            break;
//                        }
//                    }
//                    if (flag){
//                        DatasetUtil.uploadDatasetInsert(new DatasetInfoCallback() {
//                            @Override
//                            public void onSuccess(Dataset dataset) {
//                                DatasetUtil.deleteLocalDataset(new CommonDBDeleteCallback() {
//                                    @Override
//                                    public void onSuccess() {
//                                        ToastUtil.success("上传成功");
//                                        postDelayed(()->((DatasetEditPageActivity)getModel().getContext()).finish(), 300);
//                                    }
//                                    @Override
//                                    public void onSQLiteError() {
//                                        ToastUtil.toast("本地数据库错误");
//                                    }
//                                }, getModel().getDataset());
//                            }
//
//                            @Override
//                            public void unknownError(String error) {
//                                ToastUtil.info("未知错误"+error);
//                            }
//
//                            @Override
//                            public void noNetwork() {
//                                //TODO
//                                ToastUtil.toast("网络连接失败");
//                            }
//                        }, getModel().getDataset().getUuid(), getModel().getDataset().getName(), getModel().getDataset().getDataType());
//                    }else {
//                        //TODO 有文件没有成功上传，需要通知用户
//                        ToastUtil.toast("有文件没有上传完成");
//                    }
//                }
//            }, getModel().getDataset());
//        }
//    }
//
//    public void importFiles() {
////        FileOperatorUtil.chooseFiles(getModel().getContext(), getModel().getDataset().getDataType(), new FileSelectCallBack() {
////            @Override
////            public void onSuccess(@Nullable List<FileSelectResult> list) {
////                ToastUtil.success("1");
////            }
////
////            @Override
////            public void onError(@Nullable Throwable throwable) {
////                ToastUtil.error("-1");
////            }
////        });
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        intent.setType("*/*");
//        intent.putExtra(Intent.EXTRA_MIME_TYPES, FileType.getSupportedFiles(getModel().getDataset().getDataType()));
//        getModel().getLauncher().launch(intent);
//    }
//}
