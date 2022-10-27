package com.kai.blebeacon.ui.register;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kai.blebeacon.listener.RegisterationListener;
import com.kai.blebeacon.utils.APIHelper;

public class RegisterViewModel extends AndroidViewModel implements RegisterationListener {

    MutableLiveData<String> messageLiveData;
    MutableLiveData<Boolean> registerLiveData;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new MutableLiveData<>();
        registerLiveData = new MutableLiveData<>();
    }

    public void register(String email, String password, String firstName, String lastName){
        APIHelper.register(email,password,firstName,lastName,this);
    }

    @Override
    public void registerationSuccessful() {
        registerLiveData.postValue(true);
    }

    @Override
    public void registerationFailure(String message) {
        messageLiveData.postValue(message);
    }

    public MutableLiveData<Boolean> getRegisterLiveData() {
        return registerLiveData;
    }
    public MutableLiveData<String> getMessageLiveData(){
        return messageLiveData;
    }
}
