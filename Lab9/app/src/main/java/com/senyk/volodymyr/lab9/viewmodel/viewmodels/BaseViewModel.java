package com.senyk.volodymyr.lab9.viewmodel.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.senyk.volodymyr.lab9.viewmodel.helpers.SingleEventLiveData;

public abstract class BaseViewModel extends ViewModel {
    protected SingleEventLiveData<String> message = new SingleEventLiveData<>();

    public LiveData<String> getMessage() {
        return this.message;
    }
}
