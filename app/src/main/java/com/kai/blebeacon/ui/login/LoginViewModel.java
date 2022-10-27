package com.kai.blebeacon.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.kai.blebeacon.listener.LoginListener;
import com.kai.blebeacon.model.LoginData;
import com.kai.blebeacon.utils.APIHelper;

public class LoginViewModel extends AndroidViewModel implements LoginListener {

    MutableLiveData<String> messageLiveData;
    MutableLiveData<LoginData> loginLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new MutableLiveData<>();
        loginLiveData = new MutableLiveData<>();
    }

    public void login(String email, String password){
        APIHelper.login(email,password,this);
    }

    @Override
    public void loginSuccessfull(String id, String jwtoken, String customerId) {
        loginLiveData.postValue(new LoginData(id,jwtoken,customerId));
    }

    @Override
    public void loginFailure(String message) {
        messageLiveData.postValue(message);
    }
    public MutableLiveData<String> getMessageLiveData(){
        return messageLiveData;
    }
    public MutableLiveData<LoginData> getLoginLiveData(){
        return loginLiveData;
    }
}
