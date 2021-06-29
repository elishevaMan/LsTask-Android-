package com.example.lsapplication.ViewModel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    protected MutableLiveData<String> toastMessage;
    protected MutableLiveData<String> saveResult;
    protected ObservableBoolean loading;

    public BaseViewModel() {
        saveResult = new MutableLiveData<>();
        toastMessage = new MutableLiveData<>();
        loading = new ObservableBoolean(false);
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }


    public MutableLiveData<String> getSaveResult() {
        return saveResult;
    }


    public ObservableBoolean getLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.set(loading);
    }
}
