package com.echo.datatag3.base;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;

public abstract class BaseCustomActivity<VM extends BaseViewModel<M>, M extends BaseModel, VDB extends ViewDataBinding> extends BaseActivity<VM, M ,VDB> {

    @Override
    protected final VM createViewModel() {
        return (VM) new ViewModelProvider(this).get((Class<? extends BaseViewModel<? extends BaseModel>>) (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    protected final M createModel() throws IllegalAccessException, InstantiationException {
        return (M) ((Class<? extends BaseModel>) (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1]).newInstance();
    }

}
